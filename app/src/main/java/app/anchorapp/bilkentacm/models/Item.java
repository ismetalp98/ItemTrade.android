package app.anchorapp.bilkentacm.models;

import android.media.Image;

public class Item {

    String title,content,itemId,price,viewcount;
    Image photo;

    public Item() { }

    public Item(String title, String content,String itemId,String price, String viewcount) {
        this.title = title;
        this.content = content;
        this.itemId = itemId;
        this.price = price;
        this.viewcount = viewcount;
    }

    public String getItemId() {
        return itemId;
    }

    public String getPrice() {
        return price;
    }

    public String getView() {
        return viewcount;
    }

    public void setView(String view) {
        this.viewcount = view;
    }

    public void setPrice(String price) {
        this.price = price;
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
