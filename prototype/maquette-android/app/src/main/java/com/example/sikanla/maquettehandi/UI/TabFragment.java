package com.example.sikanla.maquettehandi.UI;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.ViewPagerAdapter;

/**
 * Created by Sikanla on 20/05/2017.
 */

public class TabFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.mytabs);

        tabLayout.setVisibility(View.VISIBLE);

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ScheduledFragment(), "Demandes");
        adapter.addFragment(new InstantFragment(), "Messages");
        adapter.addFragment(new HistoricFragment(), "testTab");

        viewPager.setAdapter(adapter);
    }

}
