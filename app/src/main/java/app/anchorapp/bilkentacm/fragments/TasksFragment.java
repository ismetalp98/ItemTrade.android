package app.anchorapp.bilkentacm.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.Signin_Signup.Login;
import app.anchorapp.bilkentacm.adapters.ChecklistAdapter;
import app.anchorapp.bilkentacm.models.Catagory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    private FirebaseAuth fAuth;
    private ExtendedFloatingActionButton fab;
    private Toolbar toolbar;
    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        fAuth = FirebaseAuth.getInstance();
        fab = view.findViewById(R.id.task_fab);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_task_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Catagory> list = new ArrayList<>();

        Catagory taskGroup1 = new Catagory(R.color.color1,  "Computer Science", getActivity().getDrawable(R.drawable.ic_computer_black_24dp));
        Catagory taskGroup2 = new Catagory(R.color.color2,  "EE", getActivity().getDrawable(R.drawable.ic_ee_24dp));
        Catagory taskGroup3 = new Catagory(R.color.color3,  "Architectuure", getActivity().getDrawable(R.drawable.ic_brush_black_24dp));
        Catagory taskGroup4 = new Catagory(R.color.color4,  "Economy", getActivity().getDrawable(R.drawable.ic_euro_symbol_black_24dp));
        Catagory taskGroup5 = new Catagory(R.color.color5,  "Siyasal", getActivity().getDrawable(R.drawable.ic_person));

        list.add(taskGroup1);
        list.add(taskGroup2);
        list.add(taskGroup3);
        list.add(taskGroup4);
        list.add(taskGroup5);


        recyclerView.setAdapter(new ChecklistAdapter(list, getContext()));

        synchronized (recyclerView) {
            recyclerView.notifyAll();
        }

        //Set toolbar

        toolbar = view.findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_logout:
                        fAuth.signOut();
                        startActivity(new Intent(getContext(), Login.class));
                        getActivity().finish();
                        break;
                }
                return true;
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),AddItem.class));
            }
        });

        return view;
    }



}
