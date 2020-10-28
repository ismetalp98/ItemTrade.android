package app.anchorapp.bilkentacm.fragments;

import android.content.DialogInterface;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;

import app.anchorapp.bilkentacm.Notification.Token;
import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.Resources.DatabseManager;
import app.anchorapp.bilkentacm.activities.Message;
import app.anchorapp.bilkentacm.models.Contact;
import app.anchorapp.bilkentacm.models.Item;

public class Tab1Fragment extends Fragment {

    RecyclerView contactList;
    DatabseManager manager = new DatabseManager();
    FirestoreRecyclerAdapter<Contact, DatabseManager.ItemViewHolderConversations> noteAdapter;
    FirebaseUser user;


    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        contactList = view.findViewById(R.id.myContacts);
        user = FirebaseAuth.getInstance().getCurrentUser();

        noteAdapter = manager.getConversations("Users/" + user.getUid() + "/conversations");



        contactList.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        contactList.setAdapter(noteAdapter);
        updateToken(FirebaseInstanceId.getInstance().getToken());
        return view;
    }

    private void updateToken(String token)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Tokens");
        Token token1 = new Token(token);
        databaseReference.child(user.getUid()).setValue(token1);
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
