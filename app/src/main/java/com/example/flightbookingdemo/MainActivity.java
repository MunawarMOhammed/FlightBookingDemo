package com.example.flightbookingdemo;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    AppCompatEditText from, to, Depart, return_date, passengers;
    RadioGroup modeOfTravel;

    Button search_btn;

    private ActivityResultLauncher<String> launcher;

    LinearLayout linearLayoutRecentSearches ;
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

        linearLayoutRecentSearches = findViewById(R.id.linearLayoutRecentSearches);


        modeOfTravel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Get the selected radio button
                RadioButton selectedRadioButton = findViewById(checkedId);

                if (checkedId == R.id.radio_oneway) {
                    return_date.setEnabled(false);
                } else {
                    return_date.setEnabled(true);
                }
            }
        });



        launcher = registerForActivityResult(new Contract(),
                result -> {
                    if (result != null) {
                        String focusedEditTextId = getFocusedEditTextId();
                        if (focusedEditTextId.equals("from_txt")) {
                            from.setText(result);
                        } else if (focusedEditTextId.equals("to_txt")) {
                            to.setText(result);
                        }
                    }
                });



        View.OnClickListener editTextClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof AppCompatEditText) {

                    AppCompatEditText editText = (AppCompatEditText) view;
                    String editTextId = getResources().getResourceEntryName(editText.getId());

                    if (editTextId.equals("from_txt")) {

                        String selectedOption = editText.getText().toString();
                        launcher.launch(selectedOption);

                    } else if (editTextId.equals("to_txt")) {
                        String selectedOption = editText.getText().toString();
                        launcher.launch(selectedOption);
                    } else if (editTextId.equals("return_date") || editTextId.equals("departure_date")) {
                        hideKeyboard((View)editText);showDatePickerDialog(editText);
                    }
                }
            }
        };
        View.OnFocusChangeListener editTextFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b && view instanceof AppCompatEditText) {
                    AppCompatEditText editText = (AppCompatEditText) view;
                    String editTextId = getResources().getResourceEntryName(editText.getId());

                    if (editTextId.equals("from_txt")) {
                        String selectedOption = editText.getText().toString();
                        launcher.launch(selectedOption);

                    } else if (editTextId.equals("to_txt")) {
                        String selectedOptionInFrom = from.getText().toString();
                        launcher.launch(selectedOptionInFrom);
                    } else if (editTextId.equals("return_date") || editTextId.equals("departure_date")) {
                        hideKeyboard((View) editText);showDatePickerDialog(editText);

                    }
                }
            }
        };

        from.setOnClickListener(editTextClickListener);
        from.setOnFocusChangeListener(editTextFocusChangeListener);

        to.setOnClickListener(editTextClickListener);
        to.setOnFocusChangeListener(editTextFocusChangeListener);

        return_date.setOnClickListener(editTextClickListener);
        return_date.setOnFocusChangeListener(editTextFocusChangeListener);

        Depart.setOnClickListener(editTextClickListener);
        Depart.setOnFocusChangeListener(editTextFocusChangeListener);

        // Search Button Feature

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allFieldsFilled = !TextUtils.isEmpty(from.getText())
                        && !TextUtils.isEmpty(to.getText())
                        && !TextUtils.isEmpty(Depart.getText());

                if(allFieldsFilled){
                    Intent intent = new Intent(MainActivity.this, AvailableFlightsActivity.class);
                    intent.putExtra("from", from.getText().toString());
                    intent.putExtra("to", to.getText().toString());
                    intent.putExtra("departure_date", Depart.getText().toString());
                    intent.putExtra("passengers",passengers.getText().toString());
                    startActivity(intent);
                    addtoSearchTab(from.getText().toString(),to.getText().toString(),Depart.getText().toString());
                }
                else{
                    Toast.makeText(MainActivity.this,"please Provide all Data",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void addtoSearchTab(String fromcity,String toCity,String date){
        TextView textView = new TextView(this);
        String toDispaly = fromcity+" To "+toCity+"\n   "+date;
        textView.setText(toDispaly);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Set margins to TextView
        layoutParams.setMargins(10, 0, 28   , 0);
        textView.setLayoutParams(layoutParams);


        linearLayoutRecentSearches.addView(textView);
    }


    private String getFocusedEditTextId() {
        View focusedView = getCurrentFocus();
        if (focusedView instanceof AppCompatEditText) {
            return getResources().getResourceEntryName(focusedView.getId());
        }
        return "";
    }
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showDatePickerDialog(AppCompatEditText editText) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String str = String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
                editText.setText(str);
            }
        }, year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();
    }


}
