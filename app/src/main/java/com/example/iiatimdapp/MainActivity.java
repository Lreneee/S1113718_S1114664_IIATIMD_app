package com.example.iiatimdapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;

import com.example.iiatimdapp.Room.Moestuin;
import com.example.iiatimdapp.Room.HandleTokenTask;

import com.example.iiatimdapp.Room.Zaadjes;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.iiatimdapp.Room.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "APIcall";
    public static ArrayList<Zaadjes> zaadjes;
    public static ArrayList<Moestuin> moestuinen;


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
        moestuinen = VolleySingleton.getInstance(this).getPersonalMoestuinen();
        Log.d("ballba", moestuinen.toString());
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
                String url = "http://192.168.1.112:8000/oauth/token";

                final EditText usernameField = (EditText)findViewById(R.id.userName);
                final String username = usernameField.getText().toString();
                final EditText passwordField = (EditText)findViewById(R.id.password);
                final String password = passwordField.getText().toString();

                if (username.length() > 0 && password.length() > 0) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject json = new JSONObject(response);

                                        String accessToken = json.getString("access_token");
                                        String refreshToken = json.getString("refresh_token");
                                        Token token = new Token(accessToken, refreshToken);
                                        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                                        new Thread(new HandleTokenTask(db, token)).start();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                                    MainActivity.this.startActivity(myIntent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if (error.getMessage() != null) {
                                        Log.e(TAG, error.getMessage());
                                        error.printStackTrace();
                                    }
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();

                            params.put("username",username);
                            params.put( "password",password);
                            params.put("grant_type","password");
                            params.put("client_id","3");
                            params.put("client_secret","yXOCotIGpTvgLc7vIHCZuC2mWJ8nIKkEa1Aosmg8");
                            params.put("scope","");

                            return params;
                        }
                    };

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

            }
        });


    }
}