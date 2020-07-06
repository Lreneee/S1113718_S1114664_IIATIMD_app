package com.example.iiatimdapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiatimdapp.Room.Zaadjes;

public class SearchSeedsActivity extends AppCompatActivity {

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

        VolleySingleton.getInstance(this).getZaadjes();

        recyclerViewSeed = findViewById(R.id.searchSeeds_recyclerview);


        Button btngotochoose = (Button) findViewById(R.id.btngotochoose);
        btngotochoose.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v){
                Intent myIntent = new Intent(SearchSeedsActivity.this, ChooseSeedsActivity.class);
                SearchSeedsActivity.this.startActivity(myIntent);
            }
        });
    }
}
