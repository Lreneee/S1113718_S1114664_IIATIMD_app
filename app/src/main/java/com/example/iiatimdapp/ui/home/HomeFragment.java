package com.example.iiatimdapp.ui.home;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.iiatimdapp.CardAdapter;
import com.example.iiatimdapp.MainActivity;
import com.example.iiatimdapp.MakeMoestuinActivity;
import com.example.iiatimdapp.Room.Moestuin;
import com.example.iiatimdapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ViewPager viewPager;
    CardAdapter adapter;
    ArrayList<Moestuin> moestuinen = new ArrayList<>(); 
    List<Moestuin> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        models = new ArrayList<>();


        adapter = new CardAdapter(models, getActivity());
        viewPager = getView().findViewById(R.id.cardViewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0, 0, 410, 0);
        viewPager.setBackgroundColor(getResources().getColor(R.color.lightGrey));


        Button button= getView().findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeMoestuinActivity.class);
                startActivity(i);
            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
