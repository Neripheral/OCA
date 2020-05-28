package com.nerpage.oca.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.ItemDatabase;
import com.nerpage.oca.misc.LocaleHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ItemDatabase.init(this);

        Intent intent = new Intent(this, CharacterManagerActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(LocaleHelper.onAttach(base, "pl"));
    }
}
