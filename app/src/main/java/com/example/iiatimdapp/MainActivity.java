package com.example.iiatimdapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimdapp.Room.HandleTokenTask;
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
import com.example.iiatimdapp.Room.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "APIcall";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final Response.Listener<String> loginResponse = new Response.Listener<String>() {
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