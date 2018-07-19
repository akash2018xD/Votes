package gdgsearch.com.gdgfinals;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by skmishra on 9/9/2017.
 */
public class volleyAccess  {
    VolleySingleton VS;
    com.android.volley.toolbox.ImageLoader mImageLoader;
    public Boolean isNots = false;
    private Pattern p;
    private StringRequest sRq;
    public volleyAccess() {

        //  mPlatesUser=new platesUser();
    }

    public void makeRequest(Context mc,String url, final VolleyResponse callback) {


        if (!isNots) {
            try {
                URL mUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return;//If Url Not Good , What request are we going to make :P
            }
        }

        VS = VolleySingleton.getInstance(mc);
        RequestQueue rq = VS.getRequestQueue();
        sRq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                try {
                    callback.onSuccess(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Dd",volleyError.getMessage());
            }
        });



        sRq.setShouldCache(false);
        rq.add(sRq);
    }



    public void multipartRequest(final Map<String, String> params, final String url, final String path_video, final VolleyResponse resp) {



        final  okhttp3.RequestBody requestBody;
        OkHttpClient client = new OkHttpClient();

        File file=new File(path_video);
        final long totalSize = file.length();

        Log.e("Path Vide",path_video);
        if(getMimeType(path_video).equals("image/jpeg")) {
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);
        }

        Log.e("File Size Sen t",file.length()+"");
        MultipartBody.Builder formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse(getMimeType(path_video)),file));

        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String,String> pairs = iterator.next();
            String value =  pairs.getValue();
            String key = pairs.getKey();
            formBody.addFormDataPart(key, value);
            Log.e("Key","Value"+value);
        }
        requestBody=formBody.build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(requestBody).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.e("f",responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                Log.e("dd",response.body().string());
                try {
                    resp.onSuccess(response.body().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static String getMimeType(String url) {

        String mime;
        int dot = url.lastIndexOf(".");
        String ext = url.substring(dot + 1);
        if(ext.equals("mp4") || ext.equals("MP4"))
        {
            mime="video/mp4";

        }else
        {
            mime="image/jpeg";
        }
        return mime;
    }

}
