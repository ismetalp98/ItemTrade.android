package app.anchorapp.bilkentacm.models;

import android.media.Image;

public class Item {

    String title,content,price,owner,ownername;
    Image photo;
    long viewcount;

    public Item() { }

    public Item(String title, String content, String price, String owner, String ownername, Image photo, long viewcount) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.owner = owner;
        this.ownername = ownername;
        this.photo = photo;
        this.viewcount = viewcount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public long getViewcount() {
        return viewcount;
    }

    public void setViewcount(long viewcount) {
        this.viewcount = viewcount;
    }
}
