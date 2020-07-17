package app.anchorapp.bilkentacm.fragments;

import app.anchorapp.bilkentacm.Notification.MyResponse;
import app.anchorapp.bilkentacm.Notification.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key = AAAACsuWmbw:APA91bGfRq4rqZ0XivmKkD93YhBeOeRKTk8QYbQi3RyME-g8JIowgx3kLHX6Sg6uH7QsFqJcT4DM2mLzRy3yjknJNRIwsBslXp_DS0IYu8TIq22bdxyAA3IGvCVIyEyMReFZuU9hmGjm"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
