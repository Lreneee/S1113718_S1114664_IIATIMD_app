package com.example.iiatimdapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiatimdapp.ChooseSeedsActivity;
import com.example.iiatimdapp.MainActivity;
import com.example.iiatimdapp.R;
import com.example.iiatimdapp.Room.Zaadjes;
import com.example.iiatimdapp.SearchSeedsActivity;
import com.example.iiatimdapp.SearchSeedsAdapter;
import com.example.iiatimdapp.VolleySingleton;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Zaadjes> zaadjes;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.searchSeeds_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        ArrayList<Zaadjes> zaadjes = MainActivity.zaadjes;

       Log.d("zaad", zaadjes.toString());
        recyclerViewAdapter = new SearchSeedsAdapter(zaadjes, images);
        recyclerView.setAdapter(recyclerViewAdapter);

        return root;
    }


}
