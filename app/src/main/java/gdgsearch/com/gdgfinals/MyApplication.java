package gdgsearch.com.gdgfinals;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

/**
 * Created by skmishra on 9/9/2017.
 */
public class MyApplication extends Application {

    private static MyApplication ins;

    public MyApplication() {
        ins=this;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        ins=this;
        FontsOverride.setDefaultFont(this, "DEFAULT", "Lato-Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "Lato-Bold.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "Lato-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "Lato-Regular.ttf");
    }

    public static MyApplication getInstance()
    {
        return ins;
    }

    public static Context getAppContext()
    {
        return ins.getApplicationContext();
    }
    public void putData(Context context,String key,String value)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("votes", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(key, value);
        edit.apply();
    }
    public String getData(Context mConts,String key)
    {
        Context context;
        context= mConts;//  i
        SharedPreferences sharedPref = context.getSharedPreferences("votes", Context.MODE_PRIVATE);
        String contents =sharedPref.getString(key, "alias");
        return  contents;


    }
}
