package com.nerpage.oca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nerpage.oca.classes.ItemDatabase;
import com.nerpage.oca.misc.LocaleHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ItemDatabase.init(this);

        Intent intent = new Intent(this, CharacterEditorActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(LocaleHelper.onAttach(base, "pl"));
    }
}
