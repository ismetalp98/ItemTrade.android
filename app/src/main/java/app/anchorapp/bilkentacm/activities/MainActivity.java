package app.anchorapp.bilkentacm.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.fragments.ProfileFragment;
import app.anchorapp.bilkentacm.fragments.TasksFragment;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView bottomNav;
    private Fragment mCurrentFragment;
    private ProfileFragment profileFragment;
    private TasksFragment tasksFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.asd);
        profileFragment = new ProfileFragment();
        tasksFragment = new TasksFragment();
        mCurrentFragment = tasksFragment;

        setFragment(tasksFragment, "tasks");
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_nav_profile:
                        switchFragment(profileFragment, "profile");
                        break;
                    case R.id.menu_nav_tasks:
                        switchFragment(tasksFragment, "tasks");
                        break;
                }
                return true;
            }
        });

    }


    //fragment setter
    private void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, tag);
        fragmentTransaction.commit();
    }

    private void switchFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            fragmentTransaction.add(R.id.main_frame, fragment, tag);
        }

        fragmentTransaction.hide(mCurrentFragment);
        fragmentTransaction.show(fragment);
        mCurrentFragment = fragment;
        fragmentTransaction.commit();
    }




    /*private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LOW_PROFILE
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_IMMERSIVE
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
    }*/



}
    //Drawer
   /* @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        navigationView.bringToFront();
                drawerLayout.addDrawerListener(toogle);
                toogle.syncState();
                navigationView.setNavigationItemSelectedListener(this);
 drawerLayout=findViewById(R.id.drawer_layout);
         navigationView=findViewById(R.id.nav_view);*/