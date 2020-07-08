package com.example.iiatimdapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.iiatimdapp.Room.Zaadjes;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchSeedsActivity extends AppCompatActivity {

    RecyclerView recyclerViewSeed;
    public static ArrayList<Zaadjes> zaadjes = new ArrayList<>();
    private Gson gson = new Gson();

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

        APIManager.getInstance(this).getZaadjes( new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String zaadjesResponse = response.get(Integer.toString(i)).toString();

                        Zaadjes zaadje = gson.fromJson(zaadjesResponse, Zaadjes.class);

                        zaadjes.add(zaadje);

                        Log.d("zaadjes", zaadje.toString());
                        Log.d("zaadjesarray", zaadjes.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaald", error.toString());

            }
        });

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
