package com.nerpage.oca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nerpage.oca.misc.LanguageHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // add multiple language support
        LanguageHelper.changeLocale(this.getResources(), "pl");

        Intent intent = new Intent(this, CharacterManagerActivity.class);
        startActivity(intent);
        finish();
    }
}
