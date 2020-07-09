package com.example.iiatimdapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.iiatimdapp.Room.MoestuinMaten;
import com.example.iiatimdapp.Room.Token;
import com.example.iiatimdapp.Room.Zaadjes;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    private String accessToken;
    public static ArrayList<Zaadjes> zaadjes = new ArrayList<>();


    private APIManager(Context context) {
        this.baseUrl = "http://192.168.2.1:8000";
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
        APIManager api = new APIManager(context);
        return api;
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
                            String body = "";
                            if (error.networkResponse.data != null) {
                                try {
                                    body = new String(error.networkResponse.data, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
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
    public ArrayList<Zaadjes> getZaadjes2() {
        JsonObjectRequest jsonObjectRequestZaadjes = new JsonObjectRequest(Request.Method.GET, baseUrl+ "/api/zaadjes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String zaadjesResponse = response.get(Integer.toString(i)).toString();

                        Zaadjes zaadje = gson.fromJson(zaadjesResponse, Zaadjes.class);

                        zaadjes.add(zaadje);

                        Log.d("zaadjes", zaadje.toString());
                        Log.d("zaadjesarray", zaadjes.toString());
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
       queue.add(jsonObjectRequestZaadjes);
        return zaadjes;
    }

    public void getAllMoestuinen(Response.Listener<JSONObject> zaadjes, Response.ErrorListener errorListener) {
        String url = baseUrl + "/api/moestuinen";

        JsonObjectRequest jsonObjectRequestMoestuinen = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                zaadjes,
                errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        queue.add(jsonObjectRequestMoestuinen);
    }
    public void getTips(Response.Listener<JSONObject> zaadjes, Response.ErrorListener errorListener) {
        String url = baseUrl + "/api/tips";

        JsonObjectRequest jsonObjectRequestMoestuinen = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                zaadjes,
                errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        queue.add(jsonObjectRequestMoestuinen);
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
                params.put("Authorization", "Bearer " + accessToken);
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        queue.add(jsonObjectRequest);

    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void deleteAccessToken() { this.accessToken = null; }

    public boolean checkConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) this.context.getSystemService (Context.CONNECTIVITY_SERVICE);

        return conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();
    }

}
