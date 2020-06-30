package app.anchorapp.bilkentacm.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import app.anchorapp.bilkentacm.R;
import app.anchorapp.bilkentacm.adapters.TabLayoutAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View thisFragment;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private static final String TAG = "ProfileFragment";
    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisFragment = inflater.inflate(R.layout.fragment_profile, container, false);
        tablayout = thisFragment.findViewById(R.id.profile_tabs);
        viewPager = thisFragment.findViewById(R.id.profile_view);
        return thisFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tablayout.setupWithViewPager(viewPager);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setUpViewPager(ViewPager viewpager)
    {
        TabLayoutAdapter adapter = new TabLayoutAdapter(getChildFragmentManager());
        adapter.addFragment(new Tab1Fragment(),"Tab1");
        adapter.addFragment(new Tab2Fragment(),"Tab2");
        adapter.addFragment(new Tab3Fragment(),"Tab3");

        viewpager.setAdapter(adapter);
    }
}
