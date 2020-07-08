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
import androidx.appcompat.app.AppCompatActivity;

import com.example.iiatimdapp.Room.MoestuinMaten;

import java.util.ArrayList;
import java.util.List;


public class MakeMoestuinActivity  extends AppCompatActivity {

    String name;
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
                name = nameInput.getText().toString();

                APIManager.getInstance(getApplicationContext()).addMoestuinToDatabase(name, 1);
                Intent myIntent = new Intent(MakeMoestuinActivity.this, SearchSeedsActivity.class);
                MakeMoestuinActivity.this.startActivity(myIntent);
            }
        });
    }


}
