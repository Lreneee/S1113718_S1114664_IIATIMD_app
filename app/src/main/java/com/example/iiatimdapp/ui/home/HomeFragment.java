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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iiatimdapp.CardAdapter;
import com.example.iiatimdapp.DetailsMoestuinActivity;
import com.example.iiatimdapp.MainActivity;
import com.example.iiatimdapp.MakeMoestuinActivity;
import com.example.iiatimdapp.Room.Moestuin;
import com.example.iiatimdapp.R;
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
    public static ArrayList<Moestuin> moestuinen = new ArrayList<>();
    Gson gson = new Gson();

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

        RequestQueue queue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObjectRequestMoestuinen = new JsonObjectRequest(Request.Method.GET, "http://192.168.2.1:8000/api/moestuinen", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String moestuinResponse = response.get(Integer.toString(i)).toString();

                        Moestuin moestuin = gson.fromJson(moestuinResponse, Moestuin.class);

                        moestuinen.add(moestuin);

                        Log.d("zaadjes", moestuin.toString());
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaald", error.toString());
                String body = "";
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                if (error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("FAILURE22", body);
            }
        });
        queue.add(jsonObjectRequestMoestuinen);

        adapter = new CardAdapter(moestuinen, getActivity());
        viewPager = getView().findViewById(R.id.cardViewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0, 0, 410, 0);
        viewPager.setBackgroundColor(getResources().getColor(R.color.lightGrey));


        Button button = getView().findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DetailsMoestuinActivity.class);
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
