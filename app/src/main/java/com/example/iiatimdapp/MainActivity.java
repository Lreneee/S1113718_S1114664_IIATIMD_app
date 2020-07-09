package com.example.iiatimdapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.example.iiatimdapp.Room.Moestuin;
import com.example.iiatimdapp.Room.HandleTokenTask;

import com.example.iiatimdapp.Room.Zaadjes;

import com.google.android.gms.common.api.Api;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.iiatimdapp.Room.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "APIcall";
    public static ArrayList<Zaadjes> zaadjes;
    public static ArrayList<Moestuin> moestuinen;
    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        setContentView(R.layout.activity_main);
//        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        if (!APIManager.getInstance(this).checkConnection()) {
            Toast toast = Toast.makeText(this, "Ofline modus", Toast.LENGTH_SHORT);
            toast.show();

            Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
            MainActivity.this.startActivity(myIntent);
        }

        AppDatabase.getInstance(getApplicationContext()).tokenDAO().getToken().observe(this, new Observer<Token>() {
            @Override
            public void onChanged(Token token) {

                if (token != null) {
                    APIManager.getInstance(getApplicationContext()).setAccessToken(token.getAccessToken());

                    Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                    MainActivity.this.startActivity(myIntent);

                }

            }
        });


        final Response.Listener<String> loginResponse = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    String accessToken = json.getString("access_token");
                    APIManager.getInstance(getApplicationContext()).setAccessToken(accessToken);
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
        };

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final EditText usernameField = (EditText) findViewById(R.id.userName);
                final String username = usernameField.getText().toString();
                final EditText passwordField = (EditText) findViewById(R.id.password);
                final String password = passwordField.getText().toString();

                if (username.length() > 0 && password.length() > 0) {
                    APIManager.getInstance(getApplicationContext()).loginRequest(username, password,loginResponse);
                }

            }
        });

        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("MainActivity", "Button clicked");
                final EditText usernameField = (EditText) findViewById(R.id.userName);
                final String username = usernameField.getText().toString();
                final EditText passwordField = (EditText) findViewById(R.id.password);
                final String password = passwordField.getText().toString();

                if (username.length() > 0 && password.length() > 0) {

                    APIManager.getInstance(getApplicationContext()).registerRequest(username, password,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("MainActivity", "response");
                                    APIManager.getInstance(getApplicationContext()).loginRequest(username, password, loginResponse);
                                }
                            });
                }
            }
        });


    }
}