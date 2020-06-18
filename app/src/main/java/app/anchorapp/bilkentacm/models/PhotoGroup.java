package app.anchorapp.bilkentacm.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class PhotoGroup {

    private Bitmap bitmap;
    private Drawable icon;

    public PhotoGroup(Drawable thisicon)
    {
        icon = thisicon;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Drawable getIcon()
    {
        return icon;
    }
}
