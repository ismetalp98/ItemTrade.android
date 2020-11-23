package app.anchorapp.bilkentacm.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.Resources.DatabseManager;
import app.anchorapp.bilkentacm.Signin_Signup.Login;
import app.anchorapp.bilkentacm.activities.AddItem;
import app.anchorapp.bilkentacm.models.Item;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    private FirebaseAuth fauth;
    private ExtendedFloatingActionButton fab;
    private Toolbar toolbar;
    RecyclerView itemList;
    DatabseManager manager = new DatabseManager();
    FirestoreRecyclerAdapter<Item, DatabseManager.ItemViewHolder> noteAdapter;



    final static int RECOGNIZER_RESULT = 1;


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
        //swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        noteAdapter = manager.getItems("Items",false,"");
        //mItems = new ArrayList<>();


        //Set toolbar

        toolbar = view.findViewById(R.id.main_toolbar);
        toolbar.setTitle("Home page");
        toolbar.inflateMenu(R.menu.app_bar_menu);
        /*Menu menu = toolbar.getMenu();
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                noteAdapter.stopListening();
                searchItems(s);
                return false;
            }
        });*/


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.speech:
                        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech Text");
                        startActivityForResult(speechIntent, RECOGNIZER_RESULT);
                        break;
                    case R.id.toolbar_logout:
                        fauth.signOut();
                        getActivity().finish();
                        startActivity(new Intent(getContext(), Login.class));
                        break;
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


        /*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                searchItems();
            }
        });*/


        itemList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        itemList.setAdapter(noteAdapter);

        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        }
    }




    /*public void searchItems(final String key) {
        mItems.clear();
        fStore.collection("Items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (final DocumentSnapshot snapshot : task.getResult()) {
                    Item asd = snapshot.toObject(Item.class);
                    Item item = new Item(asd, snapshot.getId());
                    if (asd.getTitle().contains(key)) {
                        mItems.add(item);
                    }
                }
                ItemAdapter itemAdapter = new ItemAdapter(mItems, getContext());
                itemList.setAdapter(itemAdapter);
            }
        });*/

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
