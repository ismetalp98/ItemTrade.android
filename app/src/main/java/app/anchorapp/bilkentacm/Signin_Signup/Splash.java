package app.anchorapp.bilkentacm.Signin_Signup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import app.anchorapp.bilkentacm.MainActivity;
import app.anchorapp.bilkentacm.R;

public class Splash extends AppCompatActivity {

    Handler handler;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        view = findViewById(R.id.logoTitle);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                                  |View.SYSTEM_UI_FLAG_FULLSCREEN
                                  |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                  |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                  |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                  |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        },1000);
    }
}
