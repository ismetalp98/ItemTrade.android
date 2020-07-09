package app.anchorapp.bilkentacm.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.activities.ItemDetail;
import app.anchorapp.bilkentacm.models.Chat;
import app.anchorapp.bilkentacm.models.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<Item> mChat;
    Context mContext;

    public ItemAdapter(List<Item> mChat,Context mContext) {
        this.mChat = mChat;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View checkListView = LayoutInflater.from(mContext).inflate(R.layout.item_item_cardview, parent, false);
        return new ItemAdapter.ViewHolder(checkListView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolder holder, int position) {
        final Item itemParent = mChat.get(position);

        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("Items/" + itemParent.getItemId() + "/image0");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.imageView);
            }
        });

        final Item item = itemParent.getItem();
        holder.noteTitle.setText(item.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), ItemDetail.class);
                i.putExtra("viewcount",String.valueOf(item.getViewcount()));
                i.putExtra("owner",item.getOwner());
                i.putExtra("ownername",item.getOwnername());
                i.putExtra("title", item.getTitle());
                i.putExtra("price", item.getPrice());
                i.putExtra("content", item.getContent());
                i.putExtra("itemId", itemParent.getItemId());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView noteTitle;
        View view;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.cardview_title);
            imageView = itemView.findViewById(R.id.cardview_photo);
            view = itemView;
        }
    }
}

