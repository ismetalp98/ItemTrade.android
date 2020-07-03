package app.anchorapp.bilkentacm.adapters;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.models.Catagory;
import app.anchorapp.bilkentacm.models.Item;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private List<Item> item;

    public Adapter(List<Item> item){

            this.item = item;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View checkListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_cardview,parent,false);
            ViewHolder holder = new ViewHolder(checkListView);
            return holder;


        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ViewHolder holderCheck = (ViewHolder) holder;
            holderCheck.itemTitle.setText(item.get(position).getTitle());
            //holderCheck.imgView.setImageDrawable(tasks.get(position).getIcon());
        }



        @Override
        public int getItemCount() {
            return item.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemTitle;
            View view;
            //ImageView itemImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemTitle = itemView.findViewById(R.id.cardview_title);
                //itemImage = itemView.findViewById(R.id.cardview_photo);
                view = itemView;
            }
        }
}