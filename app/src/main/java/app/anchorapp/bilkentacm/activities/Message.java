package app.anchorapp.bilkentacm.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

import java.text.SimpleDateFormat;
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
import app.anchorapp.bilkentacm.Resources.DatabseManager;
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
    List<Contact> mConversations;
    String owner;
    String chatId;
    DatabaseReference reference;
    ChatAdapter chatAdapter;
    APIService apiService;
    boolean notify = false;
    DatabseManager conversation = new DatabseManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        data = getIntent();


        recyclerView = findViewById(R.id.message_recyclerview);
        toolbar = findViewById(R.id.message_toolbar);
        message = findViewById(R.id.message_message);
        floatingActionButton = findViewById(R.id.message_send);
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        chatId = data.getStringExtra("chatId");
        owner = data.getStringExtra("owner");


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        linearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayout);


        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);


        mConversations = conversation.conversationExist(fUser.getUid(),chatId);
        readMessages();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                SendMessage(fUser.getUid(), owner, message.getText().toString());
                message.setText("");
            }
        });



        message.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    notify = true;
                    SendMessage(fUser.getUid(), owner, message.getText().toString());
                    message.setText("");
                    return true;
                }
                return false;
            }
        });


        setSupportActionBar(toolbar);
        toolbar.setTitle(data.getStringExtra("ownerName"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void SendMessage(String sender, final String reciever, String messagetosend) {

        String ownername = data.getStringExtra("ownername");
        String itemId = data.getStringExtra("itemid");

        Date datea = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String date = formatter.format(datea);

        HashMap<String, Object> last_message = new HashMap<>();
        last_message.put("lastupdate", date);
        last_message.put("is_read", false);
        last_message.put("message", messagetosend);


        DatabaseReference databaseReference_message = FirebaseDatabase.getInstance().getReference().child("Coversations").child(chatId).child("messages");
        DatabaseReference databaseReference_sender = FirebaseDatabase.getInstance().getReference().child("Users").child(sender).child("conversations");
        DatabaseReference documentReference_reciever = FirebaseDatabase.getInstance().getReference().child("Users").child(owner).child("conversations");

        if (mConversations.isEmpty()) {

            HashMap<String, Object> chat_sender = new HashMap<>();
            HashMap<String, Object> chat_reciever = new HashMap<>();


            chat_sender.put("id", chatId);
            chat_sender.put("other_user_id", owner);
            chat_sender.put("name", ownername);
            chat_sender.put("itemid", itemId);
            chat_sender.put("latest_message", last_message);


            chat_reciever.put("id", chatId);
            chat_reciever.put("other_user_id", sender);
            chat_reciever.put("name", ownername);
            chat_reciever.put("itemid", itemId);
            chat_reciever.put("latest_message", last_message);


            databaseReference_sender.push().setValue(chat_sender);
            documentReference_reciever.push().setValue(chat_reciever);

        }
        else
        {

        }

        HashMap<String, Object> message = new HashMap<>();
        message.put("sender_id", sender);
        message.put("id", chatId);
        message.put("is_read", false);
        message.put("name", ownername);
        message.put("content", messagetosend);
        message.put("type", "text");
        message.put("date", date);

        Chat chat = new Chat(messagetosend, chatId, ownername, sender, "text", date, false);
        mChat.add(chat);

        databaseReference_message.setValue(mChat);

        /*final String msg = messagetosend;
        final DatabaseReference documentReference = databaseReference.child("Users").child(fUser.getUid());
        documentReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = (String) snapshot.child("name").getValue();
                if (notify)
                    sendNotification(reciever, name, msg);
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }


    /*public void sendNotification(final String reciever, final String user, final String msg) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(reciever);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Token token = snapshot1.getValue(Token.class);
                    Data data = new Data(fUser.getUid(), user + ": " + msg, "New Message",
                            owner, R.mipmap.ic_launcher_round);

                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200) {
                                        if (response.body().success != 1) {
                                            Toast.makeText(Message.this, "Failed", Toast.LENGTH_SHORT).show();
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

    }*/

    public void readMessages() {
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Coversations").child(chatId).child("messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    mChat.add(chat);
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
