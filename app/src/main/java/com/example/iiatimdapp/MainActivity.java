package com.example.iiatimdapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimdapp.Room.MoestuinMaten;
import com.example.iiatimdapp.Room.Zaadjes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "APIcall";
    public static ArrayList<Zaadjes> zaadjes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        zaadjes = VolleySingleton.getInstance(this).getZaadjes();

//        setContentView(R.layout.activity_login);
//
//        Button btnLogin = (Button) findViewById(R.id.btnLogin);
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                String url = "http://192.168.1.112:8000/oauth/token";
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject json = new JSONObject(response);
//
//                                    String accessToken = json.getString("access_token");
//                                    String refreshToken = json.getString("refresh_token");
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                                Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
//                                MainActivity.this.startActivity(myIntent);
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                if (error.getMessage() != null) {
//                                    Log.e(TAG, error.getMessage());
//                                }
//                            }
//                        }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("username","test@test.nl");
//                        params.put( "password","test1234");
//                        params.put("grant_type","password");
//                        params.put("client_id","3");
//                        params.put("client_secret","yXOCotIGpTvgLc7vIHCZuC2mWJ8nIKkEa1Aosmg8");
//                        params.put("scope","");
//
//                        return params;
//                    }
//                };
//
//                // Add the request to the RequestQueue.
//                queue.add(stringRequest);
//            }
//        });


    }
}