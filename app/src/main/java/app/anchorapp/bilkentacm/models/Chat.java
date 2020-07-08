package app.anchorapp.bilkentacm.models;

import com.google.type.Date;

public class Chat {

    private String reciever ;
    private String sender;
    private String message;
    private long sendtime;

    public Chat() {
    }

    public Chat(String reciever, String sender, String message, long sendtime) {
        this.reciever = reciever;
        this.sender = sender;
        this.message = message;
        this.sendtime = sendtime;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSendtime() {
        return sendtime;
    }

    public void setSendtime(long sendtime) {
        this.sendtime = sendtime;
    }
}
