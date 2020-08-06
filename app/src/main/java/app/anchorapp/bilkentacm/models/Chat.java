package app.anchorapp.bilkentacm.models;

import com.google.type.Date;

public class Chat {

    private String content, id, name,sender_id,type ;
    private String date;
    private boolean is_read;

    public Chat() {
    }

    public Chat(String content, String id, String name, String sender_id, String type, String date, boolean is_read) {
        this.content = content;
        this.id = id;
        this.name = name;
        this.sender_id = sender_id;
        this.type = type;
        this.date = date;
        this.is_read = is_read;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }
}
