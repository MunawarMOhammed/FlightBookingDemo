package com.example.flightbookingdemo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;

public class Search_Acitivity extends AppCompatActivity {

    SearchView searchView_find_country;
    ListView lstView;

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    private ActivityResultLauncher<String> launcher;

    ImageView imgback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_acitivity);

        searchView_find_country= findViewById(R.id.searchbar);

        imgback = findViewById(R.id.search_toolbar_backbutton);

        lstView = findViewById(R.id.listview);

        HashMap<String, String> cityCodeMap = new HashMap<>();
        cityCodeMap.put("BSA", "Bosaso");
        cityCodeMap.put("DXB", "Dubai");
        cityCodeMap.put("JIB", "Djibouti");
        cityCodeMap.put("NBO", "Nairobi");
        cityCodeMap.put("MGQ","Mogadishu");
        cityCodeMap.put("JED","Jeddah");
        cityCodeMap.put("HGA","Hargeisa");

        arrayList = new ArrayList<>(cityCodeMap.values());

        launcher = registerForActivityResult(new Contract(),
                result -> {
                    if (result != null) {
                        setResultAndFinish(result);
                    }
                });

        arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        lstView.setAdapter(arrayAdapter);

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedOption = (String) adapterView.getItemAtPosition(position);
                setResultAndFinish(selectedOption);
            }
        });

        searchView_find_country.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });

        imgback.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Contract.KEY_SELECTED_OPTION,""); // Replace 'selectedOption' with the value you want to set

        // Set the result and finish the activity
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void setResultAndFinish(String selectedOption) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Contract.KEY_SELECTED_OPTION, selectedOption);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}