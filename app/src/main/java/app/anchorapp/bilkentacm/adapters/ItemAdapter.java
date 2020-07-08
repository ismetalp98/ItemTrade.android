package app.anchorapp.bilkentacm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.models.Chat;
import app.anchorapp.bilkentacm.models.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<Item> mChat;
    Context mContext;

    public ItemAdapter(List<Item> mChat,Context mContext) {
        this.mChat = mChat;
        System.out.println( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View checkListView = LayoutInflater.from(mContext).inflate(R.layout.item_item_cardview, parent, false);
        return new ItemAdapter.ViewHolder(checkListView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item chat = mChat.get(position);
        System.out.println(chat.getTitle() + "bbbbbbbbb");
        holder.noteTitle.setText(chat.getTitle());
        //holder.imageView.setText(format.format(date));

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

