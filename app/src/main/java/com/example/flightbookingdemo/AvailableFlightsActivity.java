package com.example.flightbookingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AvailableFlightsActivity extends AppCompatActivity {

    String from,to,departureDate,passengers;
    ImageView imgbackbtn;
    LinearLayout linearLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_flights);



        Intent intent = getIntent();
        if (intent != null) {
             from= intent.getStringExtra("from");
             to = intent.getStringExtra("to");
            departureDate = intent.getStringExtra("departure_date");
            passengers = intent.getStringExtra("passengers");
        }

        String str = from + " "+ to + " "+departureDate;

        Toast.makeText(this,str,Toast.LENGTH_LONG).show();


        imgbackbtn = findViewById(R.id.search_toolbar_backbutton);

        imgbackbtn.setOnClickListener(v->onBackPressed());



        linearLayout = findViewById(R.id.linearLaout);

        ArrayList<String> flights = new ArrayList<String>();
        flights.add("Air Arabia");
        flights.add("Qatar Airways");
        flights.add("Emirates");

        flights.add("IndiGo");
        flights.add("Air India");
        flights.add("Air Asia");

        flights.add("Etihad Airways");
        flights.add("Saudi Airways");




        for (String flight : flights) {
            CardView flightCardView = new CardView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 20);
            flightCardView.setLayoutParams(layoutParams);
            flightCardView.setCardElevation(15);
            flightCardView.setRadius(8);

            View flightCardContent = getLayoutInflater().inflate(R.layout.filght_card_view, flightCardView, false);

            TextView textViewFrom = flightCardContent.findViewById(R.id.txtFrom);
            TextView textViewTo = flightCardContent.findViewById(R.id.txtTo);
            TextView textViewFlightName = flightCardContent.findViewById(R.id.txtFlightName);
            TextView textViewPrice = flightCardContent.findViewById(R.id.txtFlightPrice);
            textViewFrom.setText(from);
            textViewTo.setText(to);
            textViewFlightName.setText(flight);
            textViewPrice.setText("$ " + 200);





            flightCardView.addView(flightCardContent);

            linearLayout.addView(flightCardView);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}