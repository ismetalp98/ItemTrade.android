package app.anchorapp.bilkentacm.Signin_Signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.HashMap;

import app.anchorapp.bilkentacm.MainActivity;
import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.models.User;

public class Register extends AppCompatActivity {

    TextView email,pss,name,lastname;
    TextInputLayout emaillay,psslay,namelay,lastnamelay;
    TextView login;
    Button register;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.tv_reg_email);
        pss = findViewById(R.id.tv_reg_pss);
        name = findViewById(R.id.tv_reg_name);
        lastname = findViewById(R.id.tv_reg_lastname);
        emaillay = findViewById(R.id.tv_reg_email_layout);
        psslay = findViewById(R.id.tv_reg_pss_layout);
        namelay = findViewById(R.id.tv_reg_name_layout);
        lastnamelay = findViewById(R.id.tv_reg_lastname_layout);
        register = findViewById(R.id.reg_btn);
        login = findViewById(R.id.reg_lgn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String loginEmail = email.getText().toString().trim();
                final String loginPss = pss.getText().toString().trim();
                final String loginlastname = lastname.getText().toString().trim();
                final String loginname = name.getText().toString().trim();
                emaillay.setError(null);
                psslay.setError(null);
                lastnamelay.setError(null);
                namelay.setError(null);

                if (loginEmail.length() == 0 || loginPss.length() == 0 || loginlastname.length() == 0 || loginname.length() == 0) {
                    if (loginEmail.length() == 0 || !loginEmail.contains("@"))
                        emaillay.setError("error");
                    if (loginPss.length() < 6)
                        psslay.setError("eror");
                    if (loginlastname.length() == 0 || loginlastname.matches(".*\\d.*"))
                        lastnamelay.setError("error");
                    if (loginname.length() == 0 || loginname.matches(".*\\d.*"))
                        namelay.setError("error");

                    return;
                } else {
                    fAuth.createUserWithEmailAndPassword(loginEmail, loginPss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification();
                                HashMap<String, Object> user = new HashMap<>();
                                user.put("name", loginname);
                                user.put("lastname", loginlastname);
                                user.put("email", loginEmail);
                                user.put("phonenumber", null);
                                DocumentReference documentReference = fStore.collection("Users").document(fuser.getUid());
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                        finish();
                                    }
                                });

                            } else
                                Toast.makeText(Register.this, "asd", Toast.LENGTH_SHORT).show();
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
