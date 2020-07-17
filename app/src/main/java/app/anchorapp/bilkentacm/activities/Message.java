package app.anchorapp.bilkentacm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.anchorapp.bilkentacm.Notification.Client;
import app.anchorapp.bilkentacm.Notification.Data;
import app.anchorapp.bilkentacm.Notification.MyResponse;
import app.anchorapp.bilkentacm.Notification.Sender;
import app.anchorapp.bilkentacm.Notification.Token;
import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.adapters.ChatAdapter;
import app.anchorapp.bilkentacm.fragments.APIService;
import app.anchorapp.bilkentacm.models.Chat;
import app.anchorapp.bilkentacm.models.Contact;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    APIService apiService;
    boolean notify = false;

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


        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
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
                notify = true;
                SendMessage(fUser.getUid(),owner,message.getText().toString());
                message.setText("");
            }
        });


        toolbar.setTitle(data.getStringExtra("recievername"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void SendMessage(String sender, final String reciever, String messagetosend)
    {
        Date date = new Date();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String ,Object> message = new HashMap<>();
        message.put("sender",sender);
        message.put("reciever",reciever);
        message.put("message",messagetosend);
        message.put("sendtime",date.getTime());

        databaseReference.child("Chats").child(chatId).push().setValue(message);

        final String msg = messagetosend;
        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(fUser.getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String name = documentSnapshot.getString("name");
                if (notify)
                    sendNotification(reciever,name,msg);
                notify =false;
            }
        });
    }

    public void sendNotification(final String reciever, final String user, final String msg)
    {
        DatabaseReference tokens  = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(reciever);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    Token token = snapshot1.getValue(Token.class);
                    Data data = new Data(fUser.getUid(),user + ": " + msg,"New Message",
                            owner,R.mipmap.ic_launcher_round);

                    Sender sender = new Sender(data,token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200)
                                    {
                                        if (response.body().success != 1 )
                                        {
                                            Toast.makeText(Message.this,"Failed",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
