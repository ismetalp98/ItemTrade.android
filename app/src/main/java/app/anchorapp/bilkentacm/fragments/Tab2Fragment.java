package app.anchorapp.bilkentacm.fragments;

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
import app.anchorapp.bilkentacm.models.Item;

public class Tab2Fragment extends Fragment {

    FirestoreRecyclerAdapter<Item, ItemViewHolder> noteAdapter;
    RecyclerView itemList;
    FirebaseFirestore fStore;
    FirebaseUser user;
    private FirebaseAuth fauth;

    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        fauth = FirebaseAuth.getInstance();
        itemList = view.findViewById(R.id.myitemList);
        fStore = FirebaseFirestore.getInstance();
        user = fauth.getCurrentUser();


        Query query = fStore.collection("Users").document(user.getUid()).collection("myItems").orderBy("title");
        FirestoreRecyclerOptions<Item> allNotes = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query,Item.class)
                .build();

        noteAdapter = new FirestoreRecyclerAdapter<Item, ItemViewHolder>(allNotes) {

            @Override
            public void onBindViewHolder(@NonNull ItemViewHolder noteViewHolder, final int i, @NonNull final Item note) {
                noteViewHolder.noteTitle.setText(note.getTitle());
                //noteViewHolder.imageView.setImageResource(note.getPhoto().getFormat());
                //final String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();

                /*noteViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), NoteDetails.class);
                        i.putExtra("title", note.getTitle());
                        i.putExtra("content", note.getContent());
                        i.putExtra("code", code);
                        i.putExtra("noteId", docId);
                        v.getContext().startActivity(i);
                    }
                });*/
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_cardview,parent,false);
                return new ItemViewHolder(view);
            }
        };


        itemList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        itemList.setAdapter(noteAdapter);
        return view;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView noteTitle;
        View view;
        //ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.cardview_title);
            //imageView = itemView.findViewById(R.id.cardview_photo);
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