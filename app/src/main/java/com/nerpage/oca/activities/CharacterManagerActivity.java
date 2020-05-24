package com.nerpage.oca.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.classes.PlayerCharacter;

public class CharacterManagerActivity extends AppCompatActivity {

    public AppBarConfiguration appBarConfiguration;
    public PlayerCharacter pc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_manager);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        this.appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout((DrawerLayout)this.findViewById(R.id.drawer_layout))
                        .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        // initialize PlayerCharacter data
        PlayerCharacter newPc = new PlayerCharacter();
        newPc.initFromFile(this);
        this.pc = newPc;
    }

    @Override
    public boolean onSupportNavigateUp(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public void saveChanges(){
        this.pc.saveToFile(this);
    }

    protected void onStop(){
        super.onStop();
        saveChanges();
    }
}
