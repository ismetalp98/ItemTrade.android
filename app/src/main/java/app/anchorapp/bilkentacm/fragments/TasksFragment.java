package app.anchorapp.bilkentacm.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.Signin_Signup.Login;
import app.anchorapp.bilkentacm.activities.AddItem;
import app.anchorapp.bilkentacm.adapters.ItemAdapter;
import app.anchorapp.bilkentacm.models.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    private FirebaseAuth fauth;
    private ExtendedFloatingActionButton fab;
    private Toolbar toolbar;
    //FirestoreRecyclerAdapter<Item, ItemViewHolder> noteAdapter;
    RecyclerView itemList;
    FirebaseFirestore fStore;
    List<Item> mItems;


    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        fab = view.findViewById(R.id.task_fab);
        fauth = FirebaseAuth.getInstance();
        itemList = view.findViewById(R.id.itemList);
        fStore = FirebaseFirestore.getInstance();
        itemList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mItems = new ArrayList<>();
        searchItems("");


        //Set toolbar

        toolbar = view.findViewById(R.id.main_toolbar);
        toolbar.setTitle("Home page");
        toolbar.inflateMenu(R.menu.app_bar_menu);
        Menu menu = toolbar.getMenu();
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchItems(s);
                return false;
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case  R.id.toolbar_logout:
                        fauth.signOut();;
                        getActivity().finish();
                        startActivity(new Intent(getContext(), Login.class));
                }
                return true;
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), AddItem.class));
            }
        });

        return view;
    }



    public void searchItems(final String key) {
        fStore.collection("Items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mItems.clear();
                for (final DocumentSnapshot snapshot : task.getResult()) {
                    Item asd = snapshot.toObject(Item.class);
                    Item item = new Item(asd, snapshot.getId());
                    if (asd.getTitle().contains(key)) {
                        mItems.add(item);
                    }
                    ItemAdapter itemAdapter = new ItemAdapter(mItems, getContext());

                    itemList.setAdapter(itemAdapter);
                }
            }
        });
        /*Query query = fStore.collection("Items").orderBy("title");
        FirestoreRecyclerOptions<Item> allNotes = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query,Item.class)
                .build();

        noteAdapter = new FirestoreRecyclerAdapter<Item, ItemViewHolder>(allNotes) {


            @Override
            public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i, @NonNull final Item item) {
                final String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();
                itemViewHolder.noteTitle.setText(item.getTitle());


                StorageReference profileRef = storageReference.child("Items/" + docId + "/image0");
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(itemViewHolder.imageView);
                    }
                });



                itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(v.getContext(), ItemDetail.class);
                        i.putExtra("viewcount",String.valueOf(item.getViewcount()));
                        i.putExtra("owner",item.getOwner());
                        i.putExtra("ownername",item.getOwnername());
                        i.putExtra("title", item.getTitle());
                        i.putExtra("price", item.getPrice());
                        i.putExtra("content", item.getContent());
                        i.putExtra("itemId", docId);
                        v.getContext().startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_cardview, parent, false);
                return new ItemViewHolder(view);
            }
        };


        itemList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        itemList.setAdapter(noteAdapter);*/
    }

    /*public class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView noteTitle;
        View view;
        ImageView imageView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.cardview_title);
            imageView = itemView.findViewById(R.id.cardview_photo);
            view = itemView;
        }
    }*/

    /*@Override
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
    }*/
}
