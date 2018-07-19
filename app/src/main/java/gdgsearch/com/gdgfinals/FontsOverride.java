package gdgsearch.com.gdgfinals;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by skmishra on 9/9/2017.
 */

public final class FontsOverride {

    private Typeface gets;


    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }


    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
   public void setLatoBold(Context context,TextView view)
    {
        gets=Typeface.createFromAsset(context.getAssets(),"Lato-Bold.ttf");
        view.setTypeface(gets);
    }

    public void setLatoLight(Context context,TextView view)
    {
        gets=Typeface.createFromAsset(context.getAssets(),"Lato-Light.ttf");
        view.setTypeface(gets);
    }

    public void setLatoSemiBold(Context context,TextView view)
    {
        gets=Typeface.createFromAsset(context.getAssets(), "Lato-Semibold.ttf");
        view.setTypeface(gets);


    }

}