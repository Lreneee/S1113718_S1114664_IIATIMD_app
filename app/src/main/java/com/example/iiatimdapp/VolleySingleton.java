package com.example.iiatimdapp;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimdapp.Room.MoestuinMaten;
import com.example.iiatimdapp.Room.Zaadjes;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;
    private AppDatabase appDatabase;
    final ArrayList<MoestuinMaten> moestuinMaten = new ArrayList<>();
    final ArrayList<Zaadjes> zaadjes = new ArrayList<>();
    Gson gson = new Gson();

    public VolleySingleton(Context context){
        ctx = context;
        requestQueue = getRequestQueue();

        appDatabase = Room.databaseBuilder(ctx, AppDatabase.class, "alldata").build();
    }
    public static synchronized VolleySingleton getInstance(Context context){
        if(instance == null){
            instance = new VolleySingleton(context);
        }
        return instance;
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

    public void  getMoestuinMaten() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.2.1:8000/api/moestuin_maten", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String moestuinResponse = response.get(Integer.toString(i)).toString();

                        MoestuinMaten moestuinMaat = gson.fromJson(moestuinResponse, MoestuinMaten.class);

                        moestuinMaten.add(moestuinMaat);

                        Log.d("maten", moestuinMaat.toString());
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
        addToRequestQueue(jsonObjectRequest);
    }
    public ArrayList<MoestuinMaten> getMoestuinMatenArraylist() {
        return moestuinMaten;
    }

    public void getZaadjes() {
        JsonObjectRequest jsonObjectRequestZaadjes = new JsonObjectRequest(Request.Method.GET, "http://192.168.2.1:8000/api/zaadjes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String zaadjesResponse = response.get(Integer.toString(i)).toString();

                        Zaadjes zaadje = gson.fromJson(zaadjesResponse, Zaadjes.class);

                        zaadjes.add(zaadje);

                        Log.d("zaadjes", zaadje.toString());
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
        addToRequestQueue(jsonObjectRequestZaadjes);
    }
}
