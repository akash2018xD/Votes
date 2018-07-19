package gdgsearch.com.gdgfinals;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by skmishra on 9/10/2017.
 */
public class dialogKnowMore extends Dialog {



    TextView about;
    ImageView imageViewDR;
    public dialogKnowMore(final Context context, String oid) {
        super(context);
        setContentView(R.layout.dialog);

        imageViewDR=(ImageView)findViewById(R.id.imageOpt);
        about=(TextView)findViewById(R.id.about);
        FontsOverride mf=new FontsOverride();
        mf.setLatoSemiBold(context,about);
        volleyAccess mv=new volleyAccess();
        mv.makeRequest(context, "http://192.168.219.1/GDGFinals/index.php?request=getKnowMore&oid=" + oid, new VolleyResponse() {
            @Override
            public void onSuccess(String s) throws JSONException {
                JSONArray mJSON=new JSONArray(s);
                JSONObject mjsobs=mJSON.getJSONObject(0);
                Glide.with(context).load(mjsobs.getString("kimage")).into(imageViewDR);
                about.setText(mjsobs.getString("kabout"));
            }

            @Override
            public void onError() {

            }
        });
    }
}
