package gdgsearch.com.gdgfinals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by skmishra on 9/9/2017.
 */
public class poll extends Activity {

    TextView title;
    TextView tabout;
    RecyclerView optrec;
    ArrayList<dataDers> mArray=new ArrayList<>();
    Button options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.setStatusBarColor(Color.parseColor("#292426"));
        final MyApplication max=new MyApplication();
        final volleyAccess mvolley=new volleyAccess();

        Intent i=getIntent();
        String name=i.getExtras().getString("name");
        String about=i.getExtras().getString("about");
        final String pid=i.getExtras().getString("id");

        title=(TextView)findViewById(R.id.title);
        tabout=(TextView)findViewById(R.id.about);

       mvolley.makeRequest(this, "http://192.168.219.1/GDGFinals/index.php?request=verify&pid=" + pid + "&uid=" + max.getData(this, "userID"), new VolleyResponse() {
            @Override
            public void onSuccess(String s) throws JSONException {
                    JSONObject resp=new JSONObject(s);
                if(resp.getBoolean("response"))
                {
                    Toast.makeText(poll.this,"Already Voted",Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onError() {

            }
        });

        title.setText(name);
        tabout.setText(about);
        optrec=(RecyclerView)findViewById(R.id.options);
        optrec.setLayoutManager(new LinearLayoutManager(this));
         final adapterSelections madap=new adapterSelections(this);

        optrec.setAdapter(madap);

        mArray=new ArrayList<>();

        mvolley.makeRequest(this, "http://192.168.219.1/GDGFinals/index.php?request=getOptions&pid=" + pid, new VolleyResponse() {
            @Override
            public void onSuccess(String s) throws JSONException {
                JSONArray mJSON = new JSONArray(s);
                if(mJSON.length()>0)
                {
                    for (int i = 0  ;i<mJSON.length();i++) {
                        JSONObject MJSON = mJSON.getJSONObject(i);
                        dataDers mdata=new dataDers();
                        mdata.setName(MJSON.getString("oname"));
                        mdata.setImage(MJSON.getString("oimage"));
                        mdata.setId(MJSON.getString("oid"));
                        mArray.add(mdata);
                    }
                }

                madap.setData(mArray);

            }

            @Override
            public void onError() {

            }
        });
      options=(Button)findViewById(R.id.optionBs);


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvolley.makeRequest(poll.this, "http://192.168.219.1/GDGFinals/index.php?request=vote&pid=" + pid + "&oid=" + madap.option + "&uid=" + max.getData(poll.this, "userID"), new VolleyResponse() {
                    @Override
                    public void onSuccess(String s) throws JSONException {


                        Intent i=new Intent(poll.this,splash.class);
                        startActivity(i);
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });



    }
}
