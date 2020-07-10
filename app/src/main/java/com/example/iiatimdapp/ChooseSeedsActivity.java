package com.example.iiatimdapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiatimdapp.Room.Zaadjes;
import com.example.iiatimdapp.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class ChooseSeedsActivity extends AppCompatActivity {

    RecyclerView recyclerViewSeed;
    public String moestuin_id;
    public String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchseeds);

        moestuin_id = getIntent().getStringExtra("moestuin_id");
        x = getIntent().getStringExtra("x");

        ArrayList<Zaadjes> zaadjes = HomeActivity.zaadjes;
        ChooseSeedsAdapter seedsAdapter = new ChooseSeedsAdapter(zaadjes, moestuin_id, x);
        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.setSeedsAdapter(seedsAdapter);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_seeds_fragment_container, dashboardFragment)
                .commit();
//
//        recyclerViewSeed = findViewById(R.id.searchSeeds_recyclerview);
//
//        moestuin_id = getIntent().getStringExtra("moestuin_id");
//        x = getIntent().getStringExtra("x");
//        Log.d("moestuin", moestuin_id + " " +  x);
//
//        recyclerViewSeed.setAdapter(seedsAdapter);
//        recyclerViewSeed.setLayoutManager(new LinearLayoutManager(this));

    }
}

