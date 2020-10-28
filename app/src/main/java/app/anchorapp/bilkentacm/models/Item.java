package app.anchorapp.bilkentacm.models;

import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

public class Item {

    String title,content,owner,ownername,itemId,price;
    long viewcount;
    HashMap<String,String> urls = new HashMap<>();

    public Item() { }


    public Item(String title, String content, String owner, String ownername, String itemId, String price, long viewcount, HashMap<String,String> urls) {
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.ownername = ownername;
        this.itemId = itemId;
        this.price = price;
        this.viewcount = viewcount;
        this.urls = urls;
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

    public long getViewcount() {
        return viewcount;
    }

    public void setViewcount(long viewcount) {
        this.viewcount = viewcount;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public HashMap<String,String> getUrls() {
        return urls;
    }

    public void setUrls(HashMap<String,String> urls) {
        this.urls = urls;
    }
}
