package app.anchorapp.bilkentacm.Signin_Signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.anchorapp.bilkentacm.activities.MainActivity;
import app.anchorapp.bilkentacm.R;

public class Login extends AppCompatActivity {

    TextView email;
    TextInputLayout pss_layout,mail_layout;
    TextView pss,register;
    Button login;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ;

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        email = findViewById(R.id.tv_email);
        pss = findViewById(R.id.tv_pss);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.tv_register);
        pss_layout = findViewById(R.id.loginpsslayout);
        mail_layout = findViewById(R.id.loginmaillayout);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginEmail = email.getText().toString().trim();
                String loginPss = pss.getText().toString().trim();
                mail_layout.setError(null);
                pss_layout.setError(null);
                if(loginEmail.length() == 0 || loginPss.length() == 0 || !loginEmail.contains("@"))
                {
                    if(loginEmail.length() == 0 || !loginEmail.contains("@"))
                        mail_layout.setError("eror");
                    if(loginPss.length() == 0)
                        pss_layout.setError("eror");
                    return;
                }

                else if(loginPss.length() < 6)
                {
                    pss_layout.setError("eror");
                    return;
                }
                else {
                    fAuth.signInWithEmailAndPassword(loginEmail, loginPss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else
                                Toast.makeText(Login.this, "asd", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    /*private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
    }*/
}
