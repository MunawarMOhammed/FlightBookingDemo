package com.example.flightbookingdemo;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Contract extends ActivityResultContract<String,String>{
    static final String KEY_SELECTED_OPTION = "selected_option";

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, String s) {
        Intent intent = new Intent(context,Search_Acitivity.class);
        intent.putExtra(KEY_SELECTED_OPTION,s);
        return intent;
    }

    @Override
    public String parseResult(int i, @Nullable Intent intent) {
        return intent.getStringExtra(KEY_SELECTED_OPTION);
    }
}
