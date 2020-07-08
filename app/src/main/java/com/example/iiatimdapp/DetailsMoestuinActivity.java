package com.example.iiatimdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iiatimdapp.Room.MoestuinDetails;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsMoestuinActivity extends AppCompatActivity {

    private String clickedMoestuin;
    Gson gson = new Gson();
    private ArrayList<MoestuinDetails> moestuinDetails = new ArrayList<>();
    TextView naam, bedekking;
    ImageView image;
    GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_moestuin);
        image = findViewById(R.id.image_moestuin);
        naam = findViewById(R.id.moestuinDetail_title);
        bedekking = findViewById(R.id.moestuinDetail_bedekking);
        gridLayout = (GridLayout) findViewById(R.id.moestuinAddLayout);

        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.moestuin_add);

        clickedMoestuin = getIntent().getStringExtra("clickedMoestuin");

        final JSONObject object = new JSONObject();
        try {
            object.put("moestuin_id", clickedMoestuin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObjectRequestMoestuin = new JsonObjectRequest(Request.Method.POST, "http://192.168.2.1:8000/api/moestuin/details", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String zaadjesResponse = response.get(Integer.toString(i)).toString();

                                MoestuinDetails moestuin = gson.fromJson(zaadjesResponse, MoestuinDetails.class);
                                moestuinDetails.add(moestuin);
                                Log.d("zaadjes", moestuin.toString());
                                naam.setText(moestuin.getNaam());
                                bedekking.setText("Bedekking: " + moestuin.getLengte_in_vakjes() + "/" + moestuin.getBreedte_in_vakjes());

                                GridLayoutManager.LayoutParams params = new GridLayoutManager.LayoutParams(moestuin.getLengte_in_vakjes(), moestuin.getBreedte_in_vakjes());
                                imageView.setLayoutParams(params);
                                gridLayout.addView(imageView);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

        Button btnCreateMoestuin = (Button) findViewById(R.id.btnAddSeeds);
        btnCreateMoestuin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v){
                Intent myIntent = new Intent(DetailsMoestuinActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
