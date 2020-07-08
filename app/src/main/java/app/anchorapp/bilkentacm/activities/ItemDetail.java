package app.anchorapp.bilkentacm.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.anchorapp.bilkentacm.R;

public class ItemDetail extends AppCompatActivity {

    Toolbar toolbar;
    TextView title,content,price,viewcount;
    ImageView photos;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseUser fUser;
    Button btnAddFav,sendMessage;
    Intent data;
    List<Uri> photosUri;
    String itemId;
    String owner;
    String ownername;
    String fUserid;
    boolean control = false;
    int listsize = 0;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails);
        data = getIntent();


        title = findViewById(R.id.itemdetail_title);
        content = findViewById(R.id.itemdetail_content);
        price = findViewById(R.id.itemdetail_price);
        owner = data.getStringExtra("owner");
        viewcount = findViewById(R.id.itemdetail_viewcount);
        photos = findViewById(R.id.itemdetail_photos);
        sendMessage = findViewById(R.id.itemdetail_sendMessage);
        btnAddFav = findViewById(R.id.itemdetail_fav);
        ownername = data.getStringExtra("ownername");
        firebaseFirestore = FirebaseFirestore.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        photosUri = new ArrayList<>();
        fUserid = fUser.getUid();

        toolbar = findViewById(R.id.itemdetail_toolbar);
        toolbar.setTitle("Item Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ////Set TextViews
        viewcount.setText(data.getStringExtra("viewcount"));
        itemId = data.getStringExtra("itemId");
        title.setText(data.getStringExtra("title"));
        content.setText(data.getStringExtra("content"));
        price.setText(data.getStringExtra("price"));


        final DocumentReference documentReference = firebaseFirestore.collection("Items").document(itemId);
        final DocumentReference documentReference_user = firebaseFirestore.collection("Users").document(fUserid).collection("myItems").document(itemId);
        final DocumentReference documentReference_fav = firebaseFirestore.collection("Users").document(fUserid).collection("myFavorites").document(itemId);

        if (!owner.equals(fUserid)) {
            firebaseFirestore.collection("Users").document(fUser.getUid()).collection("myFavorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (QueryDocumentSnapshot asd : task.getResult())
                    {
                        if (itemId.equals(asd.getId()))
                        {
                            control = true;
                            btnAddFav.setVisibility(View.VISIBLE);
                            btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_black_24dp));
                        }
                    }
                    if (!control) {
                        btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_border_black_24dp));
                        btnAddFav.setVisibility(View.VISIBLE);
                    }
                }
            });
            documentReference.update("viewcount", FieldValue.increment(1));
            documentReference_user.update("viewcount", FieldValue.increment(1));
            documentReference_fav.update("viewcount", FieldValue.increment(1));
        }
        else
        {
            sendMessage.setVisibility(View.INVISIBLE);
        }


        storageReference = FirebaseStorage.getInstance().getReference().child("Items/"+ itemId);
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (final StorageReference itemphoto : listResult.getItems())
                {
                    itemphoto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            photosUri.add(uri);
                            listsize++;
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(photosUri.get(0)).into(photos);
                        }
                    });
                }
            }
        });


        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i < listsize -1 )
                {
                    i++;
                    Picasso.get().load(photosUri.get(i)).into(photos);
                }
                else
                    {
                    i = 0;
                    Picasso.get().load(photosUri.get(i)).into(photos);
                }

            }
        });
    }

    public void setAddFav(View view)
    {
        DocumentReference documentReference_user = firebaseFirestore.collection("Users").document(fUser.getUid()).collection("myFavorites").document(itemId);

        if (!control)
        {
            HashMap<String, Object> item = new HashMap<>();
            item.put("title",title.getText().toString());
            item.put("content",content.getText().toString());
            item.put("price",price.getText().toString());
            item.put("viewcount",Integer.valueOf(viewcount.getText().toString()));
            item.put("owner",owner);
            documentReference_user.set(item);
            btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_black_24dp));
            control = true;
        }
        else
        {
            control = false;
            btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_border_black_24dp));
            documentReference_user.delete();
        }
    }

    public void sendMessage(View view)
    {
        Date date = new Date();
        DocumentReference documentReference_sender = firebaseFirestore.collection("Users").document(fUser.getUid()).collection("myChats").document(itemId + fUserid);
        DocumentReference documentReference_reciever = firebaseFirestore.collection("Users").document(owner).collection("myChats").document(itemId + fUserid);

        HashMap<String,Object> chat_sender = new HashMap<>();
        HashMap<String,Object> chat_reciever = new HashMap<>();
        chat_sender.put("chatId",itemId + fUserid);
        chat_sender.put("reciever",owner);
        chat_sender.put("sender",fUserid);
        chat_sender.put("lastupdate",date.getTime());
        chat_sender.put("recievername",ownername);

        chat_reciever.put("chatId",itemId+ fUserid);
        chat_reciever.put("reciever",fUserid);
        chat_reciever.put("recievername",fUser.getDisplayName());
        chat_reciever.put("sender",owner);
        chat_reciever.put("lastupdate",date.getTime());
        documentReference_sender.set(chat_sender);
        documentReference_reciever.set(chat_reciever);

        Intent i = new Intent(view.getContext(), Message.class);
        i.putExtra("owner",owner);
        i.putExtra("chatId",itemId + fUserid);
        view.getContext().startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
