package app.anchorapp.bilkentacm.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.Resources.DatabseManager;

import app.anchorapp.bilkentacm.models.Item;

public class Tab2Fragment extends Fragment {

    FirebaseRecyclerAdapter<Item, DatabseManager.ItemViewHolder> noteAdapter;
    RecyclerView itemList;
    DatabseManager databseManager = new DatabseManager();
    FirebaseUser firebaseUser;


    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        itemList = view.findViewById(R.id.myitemList);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        noteAdapter = databseManager.getItems("Itemsa",true, firebaseUser.getUid());


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