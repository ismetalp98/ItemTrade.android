package app.anchorapp.bilkentacm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import app.anchorapp.bilkentacm.R;

public class Message extends AppCompatActivity {

    FirebaseFirestore fStore;
    Intent data;
    Toolbar toolbar;
    TextView message;
    FirebaseUser fUser;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    String fUserid;
    String owner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        recyclerView = findViewById(R.id.message_recyclerview);
        floatingActionButton = findViewById(R.id.message_send);
        fStore = FirebaseFirestore.getInstance();
        data = getIntent();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        fUserid = fUser.getUid();
        owner = data.getStringExtra("owner");
        toolbar = findViewById(R.id.message_toolbar);
        message = findViewById(R.id.message_message);
        setSupportActionBar(toolbar);

        if (!message.getText().toString().isEmpty())
        {
            String messagetosend = message.getText().toString();
            SendMessage(fUserid,owner,messagetosend);
        }

    }

    public void SendMessage(String sender,String reciever,String messagetosend)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DocumentReference documentReference = fStore.collection("Users").document(fUserid).collection("myChats").document();

        HashMap<String,Object> message_user = new HashMap<>();
        message_user.put("reciever",owner);
        documentReference.set(message_user);

        HashMap<String ,Object> message = new HashMap<>();
        message.put("sender",sender);
        message.put("reciever",reciever);
        message.put("message",messagetosend);
        message.put("senttime",formatter.format(date) );

        databaseReference.child("Chats").child(documentReference.getId()).setValue(message);
    }
}
