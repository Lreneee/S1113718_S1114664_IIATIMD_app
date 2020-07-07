package com.example.iiatimdapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseSeedsActivity extends AppCompatActivity {

    RecyclerView recyclerViewSeed;

    String title[], desc[];
    int images[]= {
            R.drawable.broccoli,
            R.drawable.broccoli,
            R.drawable.broccoli,
            R.drawable.broccoli,
            R.drawable.broccoli,
            R.drawable.broccoli,
            R.drawable.broccoli,
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchseeds);

        recyclerViewSeed = findViewById(R.id.searchSeeds_recyclerview);

        ChooseSeedsAdapter seedsAdapter = new ChooseSeedsAdapter(this, title, desc, images);
        recyclerViewSeed.setAdapter(seedsAdapter);
        recyclerViewSeed.setLayoutManager(new LinearLayoutManager(this));

    }
}

