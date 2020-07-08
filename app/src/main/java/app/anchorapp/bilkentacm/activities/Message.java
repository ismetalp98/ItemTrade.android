package app.anchorapp.bilkentacm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.adapters.ChatAdapter;
import app.anchorapp.bilkentacm.models.Chat;

public class Message extends AppCompatActivity {

    Intent data;
    Toolbar toolbar;
    TextView message;
    FirebaseUser fUser;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    List<Chat> mChat;
    String owner;
    String chatId;
    DatabaseReference reference;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        data = getIntent();


        recyclerView = findViewById(R.id.message_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        linearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayout);


        chatId = data.getStringExtra("chatId");
        floatingActionButton = findViewById(R.id.message_send);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        owner = data.getStringExtra("owner");
        toolbar = findViewById(R.id.message_toolbar);
        message = findViewById(R.id.message_message);
        setSupportActionBar(toolbar);
        readMessages("");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage(fUser.getUid(),owner,message.getText().toString());
                message.setText("");
            }
        });


        toolbar.setTitle(data.getStringExtra("recievername"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void SendMessage(String sender,String reciever,String messagetosend)
    {
        Date date = new Date();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String ,Object> message = new HashMap<>();
        message.put("sender",sender);
        message.put("reciever",reciever);
        message.put("message",messagetosend);
        message.put("sendtime",date.getTime());

        databaseReference.child("Chats").child(chatId).push().setValue(message);

    }

    public void readMessages(final String search_key)
    {
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats").child(chatId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                        Chat chat = snapshot.getValue(Chat.class);
                        if (chat.getMessage().contains(search_key)) {
                            mChat.add(chat);
                        }
                        chatAdapter = new ChatAdapter(Message.this, mChat);
                        recyclerView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
