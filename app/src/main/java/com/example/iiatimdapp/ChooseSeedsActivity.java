package com.example.iiatimdapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiatimdapp.Room.Zaadjes;

import java.util.ArrayList;

public class ChooseSeedsActivity extends AppCompatActivity {

    RecyclerView recyclerViewSeed;
    public String moestuin_id;
    public String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchseeds);

        recyclerViewSeed = findViewById(R.id.searchSeeds_recyclerview);

        ArrayList<Zaadjes> zaadjes = HomeActivity.zaadjes;
        moestuin_id = getIntent().getStringExtra("moestuin_id");
        x = getIntent().getStringExtra("x");
        Log.d("moestuin", moestuin_id + " " +  x);

        ChooseSeedsAdapter seedsAdapter = new ChooseSeedsAdapter(zaadjes, moestuin_id, x);
        recyclerViewSeed.setAdapter(seedsAdapter);
        recyclerViewSeed.setLayoutManager(new LinearLayoutManager(this));

    }
}

