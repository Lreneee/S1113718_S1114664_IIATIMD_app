package com.example.iiatimdapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiatimdapp.APIManager;
import com.example.iiatimdapp.ChooseSeedsActivity;
import com.example.iiatimdapp.HomeActivity;
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
    private RecyclerView.Adapter adapter;

    public void setSeedsAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

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

        ArrayList<Zaadjes> zaadjes = HomeActivity.zaadjes;

        Log.d("zaad", zaadjes.toString());
        if (this.adapter == null) {
            this.adapter = new SearchSeedsAdapter(zaadjes);
        }
        recyclerView.setAdapter(this.adapter);

        if(!APIManager.getInstance(getContext()).checkConnection()){
            Toast toast = Toast.makeText(getContext(), R.string.offline_mode, Toast.LENGTH_LONG);
            toast.show();
        }

        return root;
    }


}
