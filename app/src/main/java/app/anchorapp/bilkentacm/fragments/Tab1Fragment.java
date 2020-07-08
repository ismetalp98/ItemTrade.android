package app.anchorapp.bilkentacm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.activities.Message;
import app.anchorapp.bilkentacm.models.Contact;

public class Tab1Fragment extends Fragment {

    RecyclerView contactList;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Contact, ItemViewHolder> noteAdapter;
    FirebaseUser user;
    private FirebaseAuth fauth;

    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        fauth = FirebaseAuth.getInstance();
        contactList = view.findViewById(R.id.myContacts);
        fStore = FirebaseFirestore.getInstance();
        user = fauth.getCurrentUser();


        Query query = fStore.collection("Users").document(user.getUid()).collection("myChats").orderBy("lastupdate");
        FirestoreRecyclerOptions<Contact> allNotes = new FirestoreRecyclerOptions.Builder<Contact>()
                .setQuery(query,Contact.class)
                .build();

        noteAdapter = new FirestoreRecyclerAdapter<Contact, ItemViewHolder>(allNotes) {

            @Override
            public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i, @NonNull final Contact contact) {
                itemViewHolder.reciever.setText(contact.getRecievername());
                itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), Message.class);
                        i.putExtra("owner",contact.getReciever());
                        i.putExtra("recievername",contact.getRecievername());
                        i.putExtra("chatId",contact.getChatId());
                        v.getContext().startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_cardview,parent,false);
                return new ItemViewHolder(view);
            }
        };


        contactList.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        contactList.setAdapter(noteAdapter);
        return view;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView reciever;
        View view;
        //ImageView imageView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            reciever = itemView.findViewById(R.id.contact_reciever);
            view = itemView;
        }
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
