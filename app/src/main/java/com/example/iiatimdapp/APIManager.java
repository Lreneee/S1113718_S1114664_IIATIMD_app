package com.example.iiatimdapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.iiatimdapp.Room.InsertZaadjesTask;
import com.example.iiatimdapp.Room.Moestuin;
import com.example.iiatimdapp.Room.MoestuinMaten;
import com.example.iiatimdapp.Room.Tips;
import com.example.iiatimdapp.Room.Zaadjes;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        this.clientSecret = "PVPWDE1JckEk15zFSzOseBm0CcsXbhbo2pjn7mM3";
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
    public ArrayList<Zaadjes> getZaadjes2(LifecycleOwner viewLifecycleOwner) {

        if (checkConnection()) {

            String url = baseUrl + "/api/zaadjes";

            JsonObjectRequest jsonObjectRequestZaadjes = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            String zaadjesResponse = response.get(Integer.toString(i)).toString();

                            Zaadjes zaadje = gson.fromJson(zaadjesResponse, Zaadjes.class);

                            new Thread(new InsertZaadjesTask(AppDatabase.getInstance(context), zaadje)).start();

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
        } else {
            AppDatabase.getInstance(this.context).zaadjesDAO().getAll().observe(viewLifecycleOwner, new Observer<List<Zaadjes>>() {

                @Override
                public void onChanged(List<Zaadjes> z) {
                    zaadjes.addAll(z);
                }
            });
        }
        return zaadjes;
    }

    public void getAllMoestuinen(Response.Listener<JSONObject> zaadjes, Response.ErrorListener errorListener, LifecycleOwner viewLifecycleOwner, Observer<List<Moestuin>> observer) {
        if(checkConnection()){
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
        } else {

            AppDatabase.getInstance(this.context).moestuinDAO().getAll().observe(viewLifecycleOwner, observer);

        }
    }
    public void getTips(Response.Listener<JSONObject> zaadjes, Response.ErrorListener errorListener, LifecycleOwner viewLifecycleOwner, Observer<List<Tips>> observer) {

        if (checkConnection()) {

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

        } else {

            AppDatabase.getInstance(this.context).tipsDAO().getAll().observe(viewLifecycleOwner, observer);

        }
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
    public void postZaadjeMoestuin(int moestuin_id, int zaadjes_id, int x) {
        String url = baseUrl + "/api/zaadje_moestuin";

        final JSONObject object = new JSONObject();
        try {
            object.put("moestuin_id", moestuin_id);
            object.put("zaadjes_id", zaadjes_id);
            object.put("x", x);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequestMoestuinZaadje = new JsonObjectRequest(
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
        queue.add(jsonObjectRequestMoestuinZaadje);
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


    public void postMessagingToken(String token) {
        String url = baseUrl + "/api/fcm-token";

        final JSONObject object = new JSONObject();
        try {
            object.put("token", token);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequestMessagingToken = new JsonObjectRequest(
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
        queue.add(jsonObjectRequestMessagingToken);
    }
}
