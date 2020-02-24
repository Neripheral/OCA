package com.example.oca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.oca.classes.PlayerCharacter;
import com.example.oca.ui.pager.PagerAdapter;

public class CharacterViewerActivity extends AppCompatActivity {

    public PagerAdapter adapter;
    public PlayerCharacter pc;

    public static class Layout{
        public static ViewPager getViewPager(View rootView){
            return (ViewPager) rootView.findViewById(R.id.character_viewer_pager);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_viewer);

        // add multiple language support
        LanguageHelper.changeLocale(this.getResources(), "pl");

        // initialize PlayerCharacter data
        PlayerCharacter newPc = new PlayerCharacter();
        newPc.initFromFile(this);
        this.pc = newPc;

        // initialize adapter
        FragmentManager fm = getSupportFragmentManager();
        this.adapter = new PagerAdapter(this.getBaseContext(), fm);

        // initialize pager
        ViewPager pager = Layout.getViewPager(this.findViewById(R.id.content).getRootView());
        pager.setAdapter(this.adapter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.saveChanges();
    }

    public void saveChanges(){
        this.pc.saveToFile(this);
    }
}
