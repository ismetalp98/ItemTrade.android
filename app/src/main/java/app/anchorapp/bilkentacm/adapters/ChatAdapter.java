package app.anchorapp.bilkentacm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.models.Chat;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    List<Chat> mChat;
    Context mContext;
    FirebaseUser fUser;

    public static final int MSG_TYPE_RIGHT = 1;
    public static final int MSG_TYPE_LEFT = 0;

    public ChatAdapter(Context mContext ,List<Chat> chat) {

        this.mContext = mContext;
        this.mChat = chat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_LEFT)
        {
            View checkListView = LayoutInflater.from(mContext).inflate(R.layout.item_message_left, parent, false);
            return new ChatAdapter.ViewHolder(checkListView);
        }
        else
        {
            View checkListView = LayoutInflater.from(mContext).inflate(R.layout.item_message_right, parent, false);
            return new ChatAdapter.ViewHolder(checkListView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        Date date = new Date(chat.getSendtime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        holder.message_time.setText(format.format(date));
    }


    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView show_message;
        TextView message_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            message_time = itemView.findViewById(R.id.message_time);
        }
    }

    public int getItemViewType(int i)
    {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        String fUseruid = fUser.getUid();
        if (mChat.get(i).getSender().equals(fUseruid))
        {
            return MSG_TYPE_RIGHT;
        }
        else
            return MSG_TYPE_LEFT;
    }
}

