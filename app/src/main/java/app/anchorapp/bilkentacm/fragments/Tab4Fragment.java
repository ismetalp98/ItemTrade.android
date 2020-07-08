package app.anchorapp.bilkentacm.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.activities.ItemDetail;
import app.anchorapp.bilkentacm.models.Item;

public class Tab4Fragment extends Fragment {

    FirestoreRecyclerAdapter<Item, ItemViewHolder> noteAdapter;
    RecyclerView itemList;
    FirebaseFirestore fStore;
    FirebaseUser user;
    private FirebaseAuth fauth;
    StorageReference storageReference;

    public Tab4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);

        fauth = FirebaseAuth.getInstance();
        itemList = view.findViewById(R.id.myitemList);
        fStore = FirebaseFirestore.getInstance();
        user = fauth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();


        Query query = fStore.collection("Users").document(user.getUid()).collection("myFavorites").orderBy("title");
        FirestoreRecyclerOptions<Item> allNotes = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query,Item.class)
                .build();

        noteAdapter = new FirestoreRecyclerAdapter<Item, ItemViewHolder>(allNotes) {

            @Override
            public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i, @NonNull final Item item) {
                itemViewHolder.noteTitle.setText(item.getTitle());
                final String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();


                StorageReference profileRef = storageReference.child("Items/" + docId  + "/image0");
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(itemViewHolder.imageView);
                    }
                });


                itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), ItemDetail.class);
                        i.putExtra("viewcount",String.valueOf(item.getViewcount()));
                        i.putExtra("owner",item.getOwner());
                        i.putExtra("title", item.getTitle());
                        i.putExtra("price", item.getPrice());
                        i.putExtra("content", item.getContent());
                        i.putExtra("itemId", docId);
                        v.getContext().startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_cardview,parent,false);
                return new ItemViewHolder(view);
            }
        };


        itemList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        itemList.setAdapter(noteAdapter);
        return view;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView noteTitle;
        View view;
        ImageView imageView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.cardview_title);
            imageView = itemView.findViewById(R.id.cardview_photo);
            view = itemView;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (noteAdapter != null) {
            noteAdapter.stopListening();
        }
    }
}