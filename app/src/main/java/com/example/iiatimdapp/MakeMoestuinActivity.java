package com.example.iiatimdapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iiatimdapp.Room.MoestuinMaten;

import java.util.ArrayList;
import java.util.List;


public class MakeMoestuinActivity  extends AppCompatActivity {

    String name;
    String moestuin_maat;
    int chosen_moestuin_maat;
    EditText nameInput;
    Spinner spinnerMoestuinMaten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makemoestuin);
        Spinner staticSpinner = (Spinner) findViewById(R.id.static_spinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.makeMoestuin_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);


        //Make your own 'Moestuin
        spinnerMoestuinMaten = (Spinner) findViewById(R.id.static_spinner);
        nameInput= (EditText) findViewById(R.id.nameInput);
        Button btnCreateMoestuin = (Button) findViewById(R.id.btnCreateMoestuin);
        final VolleySingleton volleySingleton = new VolleySingleton(this);
        btnCreateMoestuin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v){

                if(APIManager.getInstance(getApplicationContext()).checkConnection()){
                    name = nameInput.getText().toString();
                    moestuin_maat = spinnerMoestuinMaten.getSelectedItem().toString();
                    Log.d("sdfsdfsdfsdf", moestuin_maat);
                    getMaat();

                    APIManager.getInstance(getApplicationContext()).addMoestuinToDatabase(name, chosen_moestuin_maat);
                    Intent myIntent = new Intent(MakeMoestuinActivity.this, HomeActivity.class);
                    MakeMoestuinActivity.this.startActivity(myIntent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.offline_mode, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
    public void getMaat(){
        if(moestuin_maat.equals("30 x 30 cm")){
            chosen_moestuin_maat = 7;
        }else if(moestuin_maat.equals("120 x 120 cm")){
            chosen_moestuin_maat = 1;
        } else if(moestuin_maat.equals("120 x 90 cm")){
            chosen_moestuin_maat = 4;
        } else if(moestuin_maat.equals("120 x 60 cm")){
            chosen_moestuin_maat = 2;
        } else if(moestuin_maat.equals("90 x 90 cm")){
            chosen_moestuin_maat = 3;
        } else if(moestuin_maat.equals("60 x 60 cm")){
            chosen_moestuin_maat = 6;
        } else if(moestuin_maat.equals("60 x 30 cm")){
            chosen_moestuin_maat = 5;
        } else {
            chosen_moestuin_maat = 1;
        }
    }


}
