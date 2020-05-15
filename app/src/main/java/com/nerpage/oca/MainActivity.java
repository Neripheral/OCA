package com.nerpage.oca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
