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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.iiatimdapp.Room.HandleTokenTask;
import com.example.iiatimdapp.Room.MoestuinMaten;
import com.example.iiatimdapp.Room.Token;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIManager {

    private static APIManager instance;
    private RequestQueue queue;
    private Context context;
    private String baseUrl;
    private String clientID;
    private String clientSecret;
    private Gson gson = new Gson();


    private APIManager(Context context) {
        this.baseUrl = "http://192.168.1.112:8000";
        this.clientID = "3";
        this.clientSecret = "oQz9P4s4IYcduzGgjMrRdO7X77QHlDj4tJCcZuYE";
        this.context = context;
        this.queue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public static synchronized APIManager getInstance(Context context) {
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

    public void getAllMoestuinen(Response.Listener<JSONObject> zaadjes, Response.ErrorListener errorListener) {
        String url = baseUrl + "/api/moestuinen";

        JsonObjectRequest jsonObjectRequestMoestuinen = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                zaadjes,
                errorListener);

        queue.add(jsonObjectRequestMoestuinen);
    }

    public void getZaadjes(Response.Listener<JSONObject> jsonObjectListener, Response.ErrorListener gefaald) {
        String url = baseUrl + "/api/zaadjes";

        JsonObjectRequest jsonObjectRequestZaadjes = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                jsonObjectListener,
                gefaald);

        queue.add(jsonObjectRequestZaadjes);
    }

    public void addMoestuinToDatabase(String naam_moestuin, int moestuin_maten) {
        String url = baseUrl + "/api/moestuinen/add";

        final JSONObject object = new JSONObject();
        try {
            object.put("naam", naam_moestuin);
            object.put("moestuin_maten", moestuin_maten);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequestMoestuin = new JsonObjectRequest(
                Request.Method.POST,
                url,
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        Log.d("object_response", object.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", error.toString());
                Log.d("object_response", object.toString());

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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };
        queue.add(jsonObjectRequestMoestuin);
    }

    void getMoestuinMaten() {
        String url = baseUrl + "/api/moestuin_maten/get";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String moestuinResponse = response.get(Integer.toString(i)).toString();

                                MoestuinMaten moestuinMaat = gson.fromJson(moestuinResponse, MoestuinMaten.class);

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

        queue.add(jsonObjectRequest);

    }
}
