package app.anchorapp.bilkentacm.models;

import android.graphics.drawable.Drawable;

public class Catagory {

    private int color;
    private String name;
    private Drawable icon;

    public Catagory(int color, String name, Drawable icon) {
        this.color = color;
        this.name = name;
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
