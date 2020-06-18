package app.anchorapp.bilkentacm.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import app.anchorapp.bilkentacm.models.PhotoGroup;

import java.util.ArrayList;

import app.anchorapp.bilkentacm.R;

import static app.anchorapp.bilkentacm.fragments.AddItem.CAMERA_PERM_CODE;
import static app.anchorapp.bilkentacm.fragments.AddItem.CAMERA_REQUEST_CODE;

/*public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "PhotoAdapter";
    private ArrayList<PhotoGroup> tasks;
    private Context context;

    public PhotoAdapter(ArrayList<PhotoGroup> tasks, Context context) {
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
        View checkListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.additem_photos, parent, false);
        ChecklistHolder holder = new ChecklistHolder(checkListView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChecklistHolder holderCheck = (ChecklistHolder) holder;
        holderCheck.imgView.setImageDrawable(tasks.get(position).getIcon());
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


        public ImageView imgView;

        public ChecklistHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.additem_image);

        }

        public ImageView getImgView(){
            return imgView;
        }


    }
}*/

