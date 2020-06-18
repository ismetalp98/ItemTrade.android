package app.anchorapp.bilkentacm.models;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class TaskGroup {

    private int color;
    private int size;
    private String name;
    private Drawable icon;

    public TaskGroup(int color, int size, String name, Drawable icon) {
        this.color = color;
        this.size = size;
        this.name = name;
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
