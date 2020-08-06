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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.Resources.DatabseManager;
import app.anchorapp.bilkentacm.activities.ItemDetail;
import app.anchorapp.bilkentacm.models.Item;

public class Tab4Fragment extends Fragment {

    FirebaseRecyclerAdapter<Item, DatabseManager.ItemViewHolder> noteAdapter;
    RecyclerView itemList;
    DatabseManager databseManager = new DatabseManager();
    FirebaseUser user;
    private FirebaseAuth fauth;


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
        user = fauth.getCurrentUser();

        noteAdapter = databseManager.getItems("Users/"+ user.getUid()+"/myFavorites",false,"");

        itemList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        itemList.setAdapter(noteAdapter);
        return view;
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