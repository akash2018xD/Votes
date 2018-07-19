package gdgsearch.com.gdgfinals;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by skmishra on 9/9/2017.
 */

public class Home extends Activity {

    RecyclerView mrecMain;
    RecyclerView mrecPoll;
    recyclerMainElecsAdapter madap;

    recyclerMainPollsAdapter mdac;
    volleyAccess mvolley;
    private String userID;
    TextView tname;
    ImageView img;
    TextView state;
    ArrayList<pollPOJO> mainPolls=new ArrayList<>();
    ArrayList<pollPOJO> otherPolls=new ArrayList<>();
    Boolean cantShow=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home);


        MyApplication mapp=new MyApplication();

        userID=mapp.getData(this,"userID");

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.setStatusBarColor(Color.parseColor("#292426"));

        Intent i=getIntent();
        if(i.hasExtra("not"))
        {
            cantShow=true;
        }
        tname=(TextView)findViewById(R.id.textName);
        img=(ImageView)findViewById(R.id.imagePro);
        state=(TextView)findViewById(R.id.state);

        mrecMain=(RecyclerView)findViewById(R.id.recyclerMain);
        mrecPoll=(RecyclerView)findViewById(R.id.recyclerPolls);

        madap=new recyclerMainElecsAdapter(this);
        mdac=new recyclerMainPollsAdapter(this);


        mrecMain.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mrecPoll.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        mrecMain.setAdapter(madap);
        mrecPoll.setAdapter(mdac);

        if(!cantShow) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                    Log.e("Should", "A Notification");
                    NotificationCompat.Builder mBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(Home.this)
                                    .setSmallIcon(R.drawable.ic_launcher)
                                    .setContentTitle("New poll in your locality")
                                    .setPriority(Notification.PRIORITY_MAX)
                                    .setContentText("Delhi Legislative Assembly election, 2020 has started. Cast your vote now!")
                                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                    ;


// Gets an instance of the NotificationManager service//

                    NotificationManager mNotificationManager =

                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//When you issue multiple notifications about the same type of event, it’s best practice for your app to try to update an existing notification with this new information, rather than immediately creating a new notification. If you want to update this notification at a later date, you need to assign it an ID. You can then use this ID whenever you issue a subsequent notification. If the previous notification is still visible, the system will update this existing notification, rather than create a new one. In this example, the notification’s ID is 001//

                    mNotificationManager.notify(001, mBuilder.build());
                }
            }, 3000);
        }
        mvolley=new volleyAccess();

        makeRequest();

    }
    public ArrayList<pollPOJO> parseReponse(JSONArray jsonArray) throws JSONException {
        ArrayList<pollPOJO> returnArray=new ArrayList<>();

        if(jsonArray.length()>0)
        {
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject mJSONObs=jsonArray.getJSONObject(i);
                pollPOJO ns=new pollPOJO();
                ns.setName(mJSONObs.getString("pname"));
                ns.setAbout(mJSONObs.getString("pabout"));
                ns.setImage(mJSONObs.getString("pphoto"));
                ns.setId(mJSONObs.getString("pid"));

                returnArray.add(ns);
            }
        }
        return returnArray;

    }

    private void makeRequest() {
            mvolley.makeRequest(this, "http://192.168.219.1/GDGFinals/index.php?request=getHome&pid=" + userID, new VolleyResponse() {
                @Override
                public void onSuccess(String s) throws JSONException {

                    JSONArray mJson=new JSONArray(s);
                    handleUiChange(mJson.getJSONArray(0));

                    mainPolls=parseReponse(mJson.getJSONArray(1));
                    otherPolls=parseReponse(mJson.getJSONArray(2));
                    Log.e("Sxxze",mainPolls.size()+"");

                    onUpdateData(mainPolls,otherPolls);
                }

                @Override
                public void onError() {

                }
            });
    }

    private void handleUiChange(JSONArray profile) throws JSONException {


        JSONObject obs=profile.getJSONObject(0);
        tname.setText(obs.getString("name"));
        state.setText(obs.getString("state"));
        Glide.with(this).load(obs.getString("photo")).into(img);

    }

    private void onUpdateData(ArrayList<pollPOJO> mainPolls, ArrayList<pollPOJO> otherPolls) {
        madap.setData(mainPolls);
        mdac.setData(otherPolls);
    }
}
