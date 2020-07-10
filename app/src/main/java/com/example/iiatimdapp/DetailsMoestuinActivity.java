package com.example.iiatimdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iiatimdapp.Room.MoestuinDetails;
import com.example.iiatimdapp.Room.ZaadjesToegevoegd;
import com.example.iiatimdapp.ui.dashboard.DashboardFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsMoestuinActivity extends AppCompatActivity {

    private String clickedMoestuin;
    private String clickedMoestuinAfterSeeds;
    Gson gson = new Gson();
    private ArrayList<MoestuinDetails> moestuinDetails = new ArrayList<>();
    private ArrayList<ZaadjesToegevoegd> zaadjesToegevoegdArray = new ArrayList<>();
    TextView naam, bedekking;
    ImageView image;
    ImageView positionImage;
    GridLayout gridLayout;
    String base_URL  = "http://192.168.2.1:8000";

    private int[] moestuin_maat_1 = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView16, R.id.imageView15, R.id.imageView14, R.id.imageView13};
    private int[] moestuin_maat_2 = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4};
    private int[] moestuin_maat_3 = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView8, R.id.imageView12, R.id.imageView16};
    private int[] moestuin_maat_4 = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8, R.id.imageView12, R.id.imageView16};
    private int[] moestuin_maat_5 = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8, R.id.imageView11, R.id.imageView12, R.id.imageView15, R.id.imageView16};
    private int[] moestuin_maat_6 = {R.id.imageView1, R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView11, R.id.imageView12,R.id.imageView13, R.id.imageView14, R.id.imageView15, R.id.imageView16};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_moestuin);
        image = findViewById(R.id.image_moestuin);
        naam = findViewById(R.id.moestuinDetail_title);
        bedekking = findViewById(R.id.moestuinDetail_bedekking);
        gridLayout = (GridLayout) findViewById(R.id.moestuinAddLayout);
        int childCount = gridLayout.getChildCount();

        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.moestuin_add);

        clickedMoestuin = getIntent().getStringExtra("clickedMoestuin");
        clickedMoestuinAfterSeeds = getIntent().getStringExtra("moestuin_id");

        final JSONObject object = new JSONObject();
        try {
            if (clickedMoestuin != null) {
                object.put("moestuin_id", clickedMoestuin);
            } else {
                object.put("moestuin_id", clickedMoestuinAfterSeeds);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObjectRequestMoestuin = new JsonObjectRequest(Request.Method.POST, base_URL + "/api/moestuin/details", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Log.d("response", response.toString());
                                String zaadjesResponse = response.get(Integer.toString(i)).toString();

                                MoestuinDetails moestuin = gson.fromJson(zaadjesResponse, MoestuinDetails.class);
                                moestuinDetails.add(moestuin);
                                Log.d("zaadjes", moestuin.toString());
                                naam.setText(moestuin.getNaam());
                                bedekking.setText("Bedekking: " + moestuin.getLengte_in_vakjes() + "/" + moestuin.getBreedte_in_vakjes());
                                if (moestuin.getBreedte_in_vakjes() == 2 && moestuin.getLengte_in_vakjes() == 4) {
                                    for (int image : moestuin_maat_1) {
                                        ImageView myImg = (ImageView) findViewById(image);
                                        myImg.setImageResource(0);
                                    }
                                } else if (moestuin.getBreedte_in_vakjes() == 3 && moestuin.getLengte_in_vakjes() == 4) {
                                    for (int image : moestuin_maat_2) {
                                        ImageView myImg = (ImageView) findViewById(image);
                                        myImg.setImageResource(0);
                                    }
                                } else if (moestuin.getBreedte_in_vakjes() == 3 && moestuin.getLengte_in_vakjes() == 3) {
                                    for (int image : moestuin_maat_3) {
                                        ImageView myImg = (ImageView) findViewById(image);
                                        myImg.setImageResource(0);
                                    }
                                } else if (moestuin.getBreedte_in_vakjes() == 2 && moestuin.getLengte_in_vakjes() == 3) {
                                    for (int image : moestuin_maat_4) {
                                        ImageView myImg = (ImageView) findViewById(image);
                                        myImg.setImageResource(0);
                                    }
                                } else if (moestuin.getBreedte_in_vakjes() == 2 && moestuin.getLengte_in_vakjes() == 2) {
                                    for (int image : moestuin_maat_5) {
                                        ImageView myImg = (ImageView) findViewById(image);
                                        myImg.setImageResource(0);
                                    }
                                }else if(moestuin.getBreedte_in_vakjes() == 1 && moestuin.getLengte_in_vakjes() ==1 ){
                                    for (int image : moestuin_maat_6) {
                                        ImageView myImg = (ImageView) findViewById(image);
                                        myImg.setImageResource(0);
                                    }
                                } else if(moestuin.getBreedte_in_vakjes() == 4 && moestuin.getLengte_in_vakjes()==4){
                                   return;
                                }else {
                                    return;
                                }
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

        final JSONObject objectToegevoegd = new JSONObject();
        try {
            if (clickedMoestuin != null) {
                object.put("moestuin_id", clickedMoestuin);
            } else {
                object.put("moestuin_id", clickedMoestuinAfterSeeds);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequestToegevoegd = new JsonObjectRequest(Request.Method.POST, base_URL + "/api/toegevoegde_zaadjes", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Log.d("response", response.toString());
                                String zaadjesToegevoegdResponse = response.get(Integer.toString(i)).toString();

                                ZaadjesToegevoegd zaadjesToegevoegd = gson.fromJson(zaadjesToegevoegdResponse, ZaadjesToegevoegd.class);
                                zaadjesToegevoegdArray.add(zaadjesToegevoegd);
                                Log.d("zaadjes_toegevoegd", zaadjesToegevoegd.toString());
                                for (ZaadjesToegevoegd zaadje : zaadjesToegevoegdArray) {
                                    int position = zaadje.getX() + 1;
                                    int zaadjes_id = zaadje.getZaadjes_id();
                                    int resID = getResources().getIdentifier("imageView" + position,
                                            "id", getPackageName());
                                    positionImage = findViewById(resID);
                                    positionImage.setImageResource(getResources().getIdentifier("plane" + zaadjes_id, "drawable", getPackageName()));
                                }
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
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };
        queue.add(jsonObjectRequestToegevoegd);

        try {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                final ImageView layout = (ImageView) gridLayout.getChildAt(i);
                final int finalI = i;
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (layout.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.moestuin_add).getConstantState()) {
                            Log.d("ballba", Integer.toString(finalI));
                            Intent seedIntent = new Intent(DetailsMoestuinActivity.this, ChooseSeedsActivity.class);
                            seedIntent.putExtra("x", Integer.toString(finalI));
                            for (MoestuinDetails moestuinDetails : moestuinDetails) {
                                seedIntent.putExtra("moestuin_id", Integer.toString(moestuinDetails.getMoestuin_id()));
                            }
                            startActivity(seedIntent);
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.d("dlswdlf", e.toString());
        }


        Button btnCreateMoestuin = (Button) findViewById(R.id.btnAddSeeds);
        btnCreateMoestuin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v){
                Intent myIntent = new Intent(DetailsMoestuinActivity.this, SearchSeedsActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
