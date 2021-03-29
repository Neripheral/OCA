package com.nerpage.oca.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.misc.LocaleHelper;

public class CharacterEditorActivity extends AppCompatActivity {

    public AppBarConfiguration appBarConfiguration;
    private PlayerCharacter pc;

    public PlayerCharacter getPc() {
        return pc;
    }

    public CharacterEditorActivity setPc(PlayerCharacter pc) {
        this.pc = pc;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_manager);

        Toolbar toolbar = findViewById(R.id.character_manager_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        this.appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(this.findViewById(R.id.drawer_layout))
                        .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navView.getMenu().clear();
        navView.inflateMenu(R.menu.menu_character_manager_drawer);

        // initialize PlayerCharacter data
        PlayerCharacter newPc = new PlayerCharacter();
        newPc.initFromFile(this);
        this.setPc(newPc);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(LocaleHelper.onAttach(base, "pl"));
    }

    public void saveChanges(){
        this.getPc().saveToFile(this);
    }

    protected void onStop(){
        super.onStop();
        saveChanges();
    }
}
