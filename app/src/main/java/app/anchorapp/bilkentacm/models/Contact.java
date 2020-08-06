package app.anchorapp.bilkentacm.models;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Contact {

    String id,itemid,name,other_user_id;
    HashMap<String,Object> latest_message = new HashMap<>();

    public Contact() {
    }

    public Contact(String id, String itemid, String name, String other_user_id, HashMap<String, Object> latest_message) {
        this.id = id;
        this.itemid = itemid;
        this.name = name;
        this.other_user_id = other_user_id;
        this.latest_message = latest_message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther_user_id() {
        return other_user_id;
    }

    public void setOther_user_id(String other_user_id) {
        this.other_user_id = other_user_id;
    }

    public HashMap<String, Object> getLatest_message() {
        return latest_message;
    }

    public void setLatest_message(HashMap<String, Object> latest_message) {
        this.latest_message = latest_message;
    }
}
