package app.anchorapp.bilkentacm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.models.Catagory;

public class ChecklistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "Catagory";
    private ArrayList<Catagory> tasks;
    private Context context;

    public ChecklistAdapter(ArrayList<Catagory> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View checkListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catagory, parent, false);
        ChecklistHolder holder = new ChecklistHolder(checkListView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChecklistHolder holderCheck = (ChecklistHolder) holder;
        holderCheck.txtTitle.setText(tasks.get(position).getName());
        holderCheck.imgView.setImageDrawable(tasks.get(position).getIcon());
        holderCheck.rltImg.setBackgroundTintList( ContextCompat.getColorStateList(context, tasks.get(position).getColor()));
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ChecklistHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public ImageView imgView;
        public RelativeLayout rltImg;

        public ChecklistHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.catagory_text);
            rltImg = itemView.findViewById(R.id.item_task_rlt_img);
            imgView = itemView.findViewById(R.id.catagory_image);

        }
    }

}
