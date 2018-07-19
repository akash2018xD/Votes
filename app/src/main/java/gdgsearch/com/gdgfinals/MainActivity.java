package gdgsearch.com.gdgfinals;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //If election comission blocks account that!
    EditText mEds;
    Button bgrid1;
    Button bgrid2;
    Button bgrid3;
    Button bgrid4;
    Button bgrid5;
    Button bgrid6;
    Button bgrid7;
    Button bgrid8;
    Button bgrid9;
    Button bgrid0;
    volleyAccess mvs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.setStatusBarColor(Color.parseColor("#292426"));

        MyApplication gapp=new MyApplication();
        String useID=gapp.getData(this,"userID");
        if(!useID.equals("alias"))
        {
            Intent i=new Intent(this,Home.class);
            startActivity(i);

        }

        mEds=(EditText)findViewById(R.id.edNum);
        mEds.getBackground().setColorFilter(Color.parseColor("#5e5458"), PorterDuff.Mode.SRC_ATOP);
        bgrid1=(Button)findViewById(R.id.butGrid1);
        bgrid2=(Button)findViewById(R.id.butGrid2);
        bgrid3=(Button)findViewById(R.id.butGrid3);
        bgrid4=(Button)findViewById(R.id.butGrid4);
        bgrid5=(Button)findViewById(R.id.butGrid5);
        bgrid6=(Button)findViewById(R.id.butGrid6);
        bgrid7=(Button)findViewById(R.id.butGrid7);
        bgrid8=(Button)findViewById(R.id.butGrid8);
        bgrid9=(Button)findViewById(R.id.butGrid9);
        bgrid0=(Button)findViewById(R.id.butGrid0);


        bgrid1.setOnClickListener(this);
        bgrid2.setOnClickListener(this);
        bgrid3.setOnClickListener(this);
        bgrid4.setOnClickListener(this);

        bgrid5.setOnClickListener(this);
        bgrid6.setOnClickListener(this);
        bgrid7.setOnClickListener(this);
        bgrid8.setOnClickListener(this);


        bgrid9.setOnClickListener(this);
        bgrid0.setOnClickListener(this);

        mvs=new volleyAccess();
    }


    @Override
    public void onClick(View v) {


        Button b = (Button)v;
        String buttonText = b.getText().toString();
        mEds.setText(mEds.getText().toString()+buttonText);
        mEds.setSelection(mEds.getText().toString().length());

    }

    public void confirm(View view) {

        String adhaarNo =mEds.getText().toString();
        if(adhaarNo.length()==12) {

            mvs.makeRequest(MainActivity.this,"http://192.168.219.1/GDGFinals/index.php?request=register&adhaarNo=" + adhaarNo, new VolleyResponse() {
                @Override
                public void onSuccess(String s) throws JSONException {

                    JSONObject mJSON=new JSONObject(s);
                    Boolean success=mJSON.getBoolean("success");
                    if(success) {
                        //Redirect
                        MyApplication mapp=new MyApplication();
                        mapp.putData(MainActivity.this,"userID",mJSON.getString("id"));

                        Intent i=new Intent(MainActivity.this,Home.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(MainActivity.this,"Error Accessing Adhaar Server",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onError() {

                }
            });
        }else {

            Toast.makeText(this,"Please enter a valid 12 digit adhaar no.",Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View view) {
        String str = mEds.getText().toString();
        str = str.substring ( 0, str.length() - 1 );
// Now set this Text to your edit text
        mEds.setText ( str );

        mEds.setSelection(mEds.getText().toString().length());
    }

}
