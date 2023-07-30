package com.example.flightbookingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    AppCompatEditText from ,to, Depart, return_date ,passengers;
    RadioGroup modeOfTravel;

    Button search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modeOfTravel = findViewById(R.id.radio);
        from = findViewById(R.id.from_txt);
        to = findViewById(R.id.to_txt);
        Depart = findViewById(R.id.departure_date);
        return_date = findViewById(R.id.return_date);
        passengers = findViewById(R.id.passengers);
        search_btn = findViewById(R.id.button);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iif = new Intent(MainActivity.this,Search_Acitivity.class);
                startActivity(iif);
            }
        });








    }
}