package app.anchorapp.bilkentacm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import app.anchorapp.bilkentacm.R;

public class ItemDetail extends AppCompatActivity {

    Toolbar toolbar;
    TextView title,content,price,viewcount;
    ImageView photos;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser fUser;
    Button btnAddFav,sendMessage;
    Intent data;
    String itemId,owner,ownername,fUserid,concoid;
    ArrayList<String> uris;
    boolean control;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails);
        data = getIntent();

        title = findViewById(R.id.itemdetail_title);
        content = findViewById(R.id.itemdetail_content);
        price = findViewById(R.id.itemdetail_price);
        viewcount = findViewById(R.id.itemdetail_viewcount);
        photos = findViewById(R.id.itemdetail_photos);
        sendMessage = findViewById(R.id.itemdetail_sendMessage);
        btnAddFav = findViewById(R.id.itemdetail_fav);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        uris = data.getStringArrayListExtra("uris");
        ownername = data.getStringExtra("ownername");
        owner = data.getStringExtra("owner");

        fUserid = fUser.getUid();

        toolbar = findViewById(R.id.itemdetail_toolbar);
        toolbar.setTitle("Item Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTexts();



        if (!owner.equals(fUserid)) {
            firebaseDatabase.getReference().child("Users").child(fUser.getUid()).child("myFavorites").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(itemId)){
                        control = false;
                        btnAddFav.setVisibility(View.VISIBLE);
                        btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_black_24dp));
                    }
                    else {
                        control = true;
                        btnAddFav.setVisibility(View.VISIBLE);
                        btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            /*firebaseDatabase.getReference().setValue("viewcount", FieldValue.increment(1));
            documentReference_user.update("viewcount", FieldValue.increment(1));
            documentReference_fav.update("viewcount", FieldValue.increment(1));*/
        }
        else
        {
            sendMessage.setVisibility(View.INVISIBLE);
        }


        Picasso.get().load(uris.get(0)).into(photos);

    }

    public void setAddFav(View view)
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(fUserid).child("myFavorites").child(itemId);
        if (!control)
        {
            HashMap<String, Object> item = new HashMap<>();
            item.put("title",title.getText().toString());
            item.put("content",content.getText().toString());
            item.put("price",Long.valueOf(price.getText().toString()));
            item.put("viewcount",Long.valueOf(viewcount.getText().toString()));
            item.put("owner",owner);
            databaseReference.setValue(item);
            btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_black_24dp));
            control = true;
        }
        else
        {
            control = false;
            btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_border_black_24dp));
            databaseReference.removeValue();
        }
    }

    public void sendMessage(View view)
    {
        Intent i = new Intent(view.getContext(), Message.class);
        i.putExtra("owner",owner);
        i.putExtra("chatId","conversations_" + itemId + "_" + fUserid);
        i.putExtra("ownername",data.getStringExtra("ownername"));
        i.putExtra("itemid",itemId);
        view.getContext().startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setTexts()
    {
        viewcount.setText(data.getStringExtra("viewcount"));
        itemId = data.getStringExtra("itemId");
        title.setText(data.getStringExtra("title"));
        content.setText(data.getStringExtra("content"));
        price.setText(data.getStringExtra("price"));
    }
}
