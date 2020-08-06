package app.anchorapp.bilkentacm.Resources;


import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.activities.ItemDetail;
import app.anchorapp.bilkentacm.activities.Message;
import app.anchorapp.bilkentacm.models.Contact;
import app.anchorapp.bilkentacm.models.Item;

public class DatabseManager {

    static FirebaseDatabase fDatabase;
    FirebaseRecyclerAdapter<Item, ItemViewHolder> noteAdapter;
    Query query;


    //------------------------------------------- Get Items --------------------------------------------------

    public FirebaseRecyclerAdapter getItems(String path, boolean search, String searchkey) {
        fDatabase = FirebaseDatabase.getInstance();


        if (search) {
            query = fDatabase.getReference().child(path).orderByChild("owner").startAt(searchkey);
        } else
            query = fDatabase.getReference().child(path);

        FirebaseRecyclerOptions<Item> allNotes = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        noteAdapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(allNotes) {


            @Override
            protected void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i, @NonNull final Item item) {
                final String docId = noteAdapter.getSnapshots().getSnapshot(i).getKey();
                itemViewHolder.noteTitle.setText(String.valueOf(item.getPrice()));
                final Uri uri = Uri.parse(item.getUrls().get("url1"));
                Picasso.get().load(uri).into(itemViewHolder.imageView);
                final ArrayList<String> uris = new ArrayList<>();
                for (String tempuri : item.getUrls().values()) {
                    uris.add(tempuri);
                }

                itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), ItemDetail.class);
                        i.putExtra("viewcount", String.valueOf(item.getViewcount()));
                        i.putExtra("owner", item.getOwner());
                        i.putExtra("uris", uris);
                        i.putExtra("ownername", item.getOwnername());
                        i.putExtra("title", item.getTitle());
                        i.putExtra("price", String.valueOf(item.getPrice()));
                        i.putExtra("content", item.getContent());
                        i.putExtra("itemId", docId);
                        v.getContext().startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_cardview, parent, false);
                return new ItemViewHolder(view);
            }
        };
        return noteAdapter;
    }


    //------------------------------ Get Conversations ----------------------------------------

    public FirebaseRecyclerAdapter getConversations(String path) {

        FirebaseRecyclerAdapter<Contact, ItemViewHolderConversations> contactAdapter;
        fDatabase = FirebaseDatabase.getInstance();

        query = fDatabase.getReference().child(path);
        FirebaseRecyclerOptions<Contact> allNotes = new FirebaseRecyclerOptions.Builder<Contact>()
                .setQuery(query, Contact.class)
                .build();

        contactAdapter = new FirebaseRecyclerAdapter<Contact, ItemViewHolderConversations>(allNotes) {

            @Override
            protected void onBindViewHolder(@NonNull final ItemViewHolderConversations itemViewHolderConversations, final int i, @NonNull final Contact contact) {
                itemViewHolderConversations.reciever.setText(contact.getName());
                //itemViewHolderConversations.latest_message.setText(contact.getLatest_message().get("message").toString());
                final String chatId = contact.getId();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                storageReference.child("Images/" + contact.getOther_user_id() + "_profile_picture.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(itemViewHolderConversations.imageView);
                    }
                });

                itemViewHolderConversations.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), Message.class);
                        i.putExtra("owner", contact.getOther_user_id());
                        i.putExtra("ownername",contact.getName());
                        i.putExtra("recievername", contact.getName());
                        i.putExtra("isNew",false);
                        i.putExtra("chatId", chatId);
                        v.getContext().startActivity(i);
                    }
                });
                /*itemViewHolderConversations.view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        final DatabaseReference ref =  noteAdapter.getSnapshots().getSnapshot(i).getRef();
                        final DatabaseReference refrec =  fDatabase.getReference().child("Users").child(contact.getReciever()).child("conversations").child(chatId);
                        final DatabaseReference chat  = FirebaseDatabase.getInstance().getReference("Conversations").child(chatId);
                        String[] list = {"Delete"};
                        new MaterialAlertDialogBuilder()
                                .setItems(list, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0)
                                        {
                                            ref.removeValue();
                                            refrec.removeValue();
                                            chat.removeValue();
                                        }
                                    }
                                })
                                .show();
                        return false;
                    }
                });*/
            }


            @NonNull
            @Override
            public ItemViewHolderConversations onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_cardview, parent, false);
                return new ItemViewHolderConversations(view);
            }
        };
        return contactAdapter;
    }

    //--------------------------- View Holders --------------------------------------------------------
    public class ItemViewHolderConversations extends RecyclerView.ViewHolder {
        TextView reciever;
        TextView latest_message;
        View view;
        ImageView imageView;


        public ItemViewHolderConversations(@NonNull View itemView) {
            super(itemView);
            reciever = itemView.findViewById(R.id.contact_reciever);
            imageView = itemView.findViewById(R.id.contact_photo);
            latest_message = itemView.findViewById(R.id.contact_latest_mesage);
            view = itemView;
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
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


    //----------------- Coversation Exist? ------------------------


    int count = 0;
        public int conversationExist(String uid, final String chatId)
        {
            fDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = fDatabase.getReference().child("Users").child(uid).child("conversations");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dsnapshot : snapshot.getChildren()) {
                        Contact convo = dsnapshot.getValue(Contact.class);
                        if (dsnapshot.child("id").getValue() == chatId)
                        {
                            break;
                        }
                        else
                            count++;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return count;
        }
}
