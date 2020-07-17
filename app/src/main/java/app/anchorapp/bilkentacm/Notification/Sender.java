package app.anchorapp.bilkentacm.Notification;


public class Sender {

    Data notification;
    String to;

    public Sender(Data notification, String to) {
        this.notification = notification;
        this.to = to;
    }

    public Sender() {
    }

    public Data getData() {
        return notification;
    }

    public void setData(Data notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
