package com.example.iiatimdapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.room.Room;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.iiatimdapp.Room.HandleTokenTask;
import com.example.iiatimdapp.Room.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIManager {

    private static APIManager instance;
    private RequestQueue queue;
    private Context context;
    private String baseUrl;
    private String clientID;
    private String clientSecret;

    private APIManager(Context context) {
        this.baseUrl = "http://192.168.1.112:8000";
        this.clientID = "3";
        this.clientSecret = "yXOCotIGpTvgLc7vIHCZuC2mWJ8nIKkEa1Aosmg8";
        this.context = context;
        this.queue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    static synchronized APIManager getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static APIManager create(final Context context) {
        return new APIManager(context);
    }

    void loginRequest(final String username, final String password, Response.Listener<String> onResponse) {
        String url = baseUrl + "/oauth/token";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                onResponse,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage() != null) {
                            Log.e("APIManager", error.getMessage());
                            error.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("username", username);
                params.put("password", password);
                params.put("grant_type", "password");
                params.put("client_id", clientID);
                params.put("client_secret", clientSecret);
                params.put("scope", "");

                return params;
            }
        };

        queue.add(stringRequest);
    }

    void registerRequest(final String username, final String password, Response.Listener<String> onResponse) {
        String url = baseUrl + "/api/register";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                onResponse,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage() != null) {
                            Log.e("APIManager", error.getMessage());
                            error.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("email", username);
                params.put("password", password);

                return params;
            }
        };

        queue.add(stringRequest);
    }
}
