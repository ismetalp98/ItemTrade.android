package app.anchorapp.bilkentacm.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    Button btnAddFav;
    Button btnSendMessage;
    Intent data;
    List<Uri> photosUri;
    String itemId;
    boolean control = false;
    int listsize = 0;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails);
        toolbar = findViewById(R.id.itemdetail_toolbar);
        title = findViewById(R.id.itemdetail_title);
        content = findViewById(R.id.itemdetail_content);
        price = findViewById(R.id.itemdetail_price);
        viewcount = findViewById(R.id.itemdetail_viewcount);
        photos = findViewById(R.id.itemdetail_photos);
        btnAddFav = findViewById(R.id.itemdetail_fav);
        btnSendMessage = findViewById(R.id.itemdetail_sendMessage);
        firebaseFirestore = FirebaseFirestore.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        data = getIntent();
        photosUri = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ////Set TextViews
        viewcount.setText(data.getStringExtra("viewcount"));
        itemId = data.getStringExtra("itemId");
        title.setText(data.getStringExtra("title"));
        content.setText(data.getStringExtra("content"));
        price.setText(data.getStringExtra("price"));


        final DocumentReference documentReference = firebaseFirestore.collection("Items").document(itemId);
        firebaseFirestore.collection("Users").document(fUser.getUid()).collection("myFavorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot asd : task.getResult())
                {
                    if (itemId.equals(asd.getId()))
                    {
                        control = true;
                        btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_black_24dp));
                    }
                }
                if (!control)
                    btnAddFav.setBackground(getDrawable(R.drawable.ic_favorite_border_black_24dp));
            }
        });
        documentReference.update("viewcount", FieldValue.increment(1));


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
            HashMap<String,Object> fav = new HashMap<>();
            fav.put("itemId",itemId);
            documentReference_user.set(fav);
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
        Intent i = new Intent(view.getContext(),Message.class);
        i.putExtra("owner",data.getStringExtra("owner"));
        view.getContext().startActivity(i);
    }
}
