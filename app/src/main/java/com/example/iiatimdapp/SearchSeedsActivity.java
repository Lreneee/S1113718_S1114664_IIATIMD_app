package com.example.iiatimdapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.iiatimdapp.Room.Zaadjes;
import com.example.iiatimdapp.ui.dashboard.DashboardFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchSeedsActivity extends AppCompatActivity {

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

        APIManager.getInstance(this).getZaadjes2(this);

        zaadjes = HomeActivity.zaadjes;

        SearchSeedsAdapter adapter = new SearchSeedsAdapter(zaadjes);

        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.setSeedsAdapter(adapter);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_seeds_fragment_container, dashboardFragment)
                .commit();


    }
}
