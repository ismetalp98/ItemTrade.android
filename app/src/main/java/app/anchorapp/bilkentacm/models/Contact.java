package app.anchorapp.bilkentacm.models;

import de.hdodenhof.circleimageview.CircleImageView;

public class Contact {

    String reciever;
    String sender;
    String message;
    String chatId;
    String recievername;


    public Contact() {
    }

    public Contact(String reciever, String sender, String message, String chatId, String recievername) {
        this.reciever = reciever;
        this.sender = sender;
        this.message = message;
        this.chatId = chatId;
        this.recievername = recievername;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getRecievername() {
        return recievername;
    }

    public void setRecievername(String recievername) {
        this.recievername = recievername;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
