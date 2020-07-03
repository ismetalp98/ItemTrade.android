package app.anchorapp.bilkentacm.models;

import android.media.Image;

public class Item {

    String title,content,itemId;
    Image photo;

    public Item() { }

    public Item(String title, String content,String itemId) {
        this.title = title;
        this.content = content;
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }
}
