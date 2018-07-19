package gdgsearch.com.gdgfinals;

import org.json.JSONException;

/**
 * Created by skmishra on 9/9/2017.
 */
public interface VolleyResponse {

    public void onSuccess(String s) throws JSONException;
    public void onError();
}
