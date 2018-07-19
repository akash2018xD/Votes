package gdgsearch.com.gdgfinals;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by skmishra on 9/9/2017.
 */
public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;
    private VolleySingleton(Context mcontext){
        mRequestQueue= Volley.newRequestQueue(mcontext);

    }
    public static VolleySingleton getInstance(Context mc){
        if(sInstance==null)
        {
            sInstance=new VolleySingleton(mc);
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
