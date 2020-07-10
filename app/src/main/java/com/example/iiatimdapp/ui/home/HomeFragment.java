package com.example.iiatimdapp.ui.home;

import android.animation.ArgbEvaluator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iiatimdapp.APIManager;
import com.example.iiatimdapp.AppDatabase;
import com.example.iiatimdapp.CardAdapter;
import com.example.iiatimdapp.DetailsMoestuinActivity;
import com.example.iiatimdapp.MainActivity;
import com.example.iiatimdapp.MakeMoestuinActivity;
import com.example.iiatimdapp.Room.InsertMoestuinTask;
import com.example.iiatimdapp.Room.InsertTipTask;
import com.example.iiatimdapp.Room.Moestuin;
import com.example.iiatimdapp.R;
import com.example.iiatimdapp.Room.Tips;
import com.example.iiatimdapp.TipsAdapter;
import com.example.iiatimdapp.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ViewPager viewPager;
    CardAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ArrayList<Moestuin> moestuinen = new ArrayList<>();
    public static ArrayList<Tips> tipsArray = new ArrayList<>();
    Gson gson = new Gson();
    TextView title, desc;
    ImageView image;
    boolean firstTime = true;

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

        recyclerView = getActivity().findViewById(R.id.tips_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        recyclerViewAdapter = new TipsAdapter(tipsArray);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setNestedScrollingEnabled(false);
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        APIManager.getInstance(getActivity().getApplicationContext()).getAllMoestuinen(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String moestuinResponse = response.get(Integer.toString(i)).toString();

                        Moestuin moestuin = gson.fromJson(moestuinResponse, Moestuin.class);
                        new Thread(new InsertMoestuinTask(AppDatabase.getInstance(getContext()), moestuin)).start();
                        moestuinen.add(moestuin);

                        Log.d("zaadjes", moestuin.toString());
                    }
                    adapter = new CardAdapter(moestuinen, getActivity());
                    viewPager = getView().findViewById(R.id.cardViewPager);
                    viewPager.setAdapter(adapter);
                    viewPager.setPadding(0, 0, 410, 0);
                    viewPager.setBackgroundColor(getResources().getColor(R.color.lightGrey));
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaald", error.toString());
            }
        }, getViewLifecycleOwner(), new Observer<List<Moestuin>>() {
            @Override
            public void onChanged(List<Moestuin> moestuins) {
                ArrayList<Moestuin> moestuinen = new ArrayList<>(moestuins);
                adapter = new CardAdapter(moestuinen, getActivity());
                viewPager = getView().findViewById(R.id.cardViewPager);
                viewPager.setAdapter(adapter);
                viewPager.setPadding(0, 0, 410, 0);
                viewPager.setBackgroundColor(getResources().getColor(R.color.lightGrey));
                adapter.notifyDataSetChanged();
            }
        });

        APIManager.getInstance(getActivity().getApplicationContext()).getTips(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String tipsResponse = response.get(Integer.toString(i)).toString();


                        Tips tips = gson.fromJson(tipsResponse, Tips.class);
                        new Thread(new InsertTipTask(AppDatabase.getInstance(getContext()), tips)).start();

                        if(tipsArray.size() <2) {
                            tipsArray.add(tips);
                        } else{
                            return;
                        }
                        Log.d("tips", tips.toString());
                       recyclerViewAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaaaaaald", error.toString());
            }
        }, getViewLifecycleOwner(), new Observer<List<Tips>>() {

            @Override
            public void onChanged(List<Tips> tips) {
                for (int i = 0; i < tips.size(); i++) {

                    tipsArray.add(tips.get(i));

                    Log.d("tips", tips.toString());
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });



        Button button = getView().findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeMoestuinActivity.class);
                startActivity(i);
            }
        });


    }
}
