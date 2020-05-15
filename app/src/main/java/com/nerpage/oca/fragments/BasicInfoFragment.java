package com.nerpage.oca.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nerpage.oca.CharacterManagerActivity;
import com.nerpage.oca.CharacterViewerFragment;
import com.nerpage.oca.R;
import com.nerpage.oca.classes.PlayerCharacter;

public class BasicInfoFragment extends Fragment {
    private View rootView = null;

    static class Layout{
        private static TextView getFormNameView(View rootView){
            return (TextView) rootView.findViewById(R.id.character_name_view);
        }

        public static String getFormName(View rootView){
            return getFormNameView(rootView).getText().toString();
        }

        public static void setFormName(View rootView, String toSet){
            getFormNameView(rootView).setText(toSet);
        }

        private static ToggleButton getFormGenderView(View rootView){
            return (ToggleButton) rootView.findViewById(R.id.character_gender_view);
        }

        public static boolean getFormGender(View rootView){
            return getFormGenderView(rootView).isChecked();
        }

        public static void setFormGender(View rootView, boolean toSet){
            getFormGenderView(rootView).setChecked(toSet);
        }

        private static TextView getFormAgeView(View rootView){
            return (TextView) rootView.findViewById(R.id.character_age_view);
        }

        public static int getFormAge(View rootView){
            String ageString = getFormAgeView(rootView).getText().toString();
            if(ageString.isEmpty())
                ageString += "0";
            return Integer.parseInt(ageString);
        }

        public static void setFormAge(View rootView, int toSet){
            getFormAgeView(rootView).setText(toSet+"");
        }

        private static TextView getFormJobView(View rootView){
            return (TextView) rootView.findViewById(R.id.character_job_view);
        }

        public static String getFormJob(View rootView){
            return getFormJobView(rootView).getText().toString();
        }

        public static void setFormJob(View rootView, String toSet){
            getFormJobView(rootView).setText(toSet);
        }

    }

    /**
     * Gives ready-to-use currently known data about character
     * @return
     */
    public PlayerCharacter getPlayerCharacterData(){
        return ((CharacterManagerActivity)getActivity()).pc;
    }

    /**
     * Saves changes in layout data in character info buffer
     * @param viewCalling
     */
    public void commitChanges(View viewCalling){
        // get stored PC handler
        PlayerCharacter pc = getPlayerCharacterData();

        // fetch the name...
        String pcName = Layout.getFormName(this.rootView);
        // ...and save it if it was specified
        if(!pcName.isEmpty())
            pc.setName(pcName);

        // fetch gender...
        boolean pcGender = Layout.getFormGender(this.rootView);
        // ...and save it
        pc.setGender(pcGender);

        // fetch age...
        int pcAge = Layout.getFormAge(this.rootView);
        // ...and save it
        pc.setAge(pcAge);

        // fetch job...
        String pcJob = Layout.getFormJob(this.rootView);
        // ...and save it if it was specified
        if(!pcJob.isEmpty())
            pc.setJob(pcJob);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // inflate the view
        this.rootView = inflater.inflate(R.layout.fragment_basic_info, container, false);

        // get character profile handler
        PlayerCharacter pc = getPlayerCharacterData();

        // inject name into layout
        Layout.setFormName(this.rootView, pc.getName());

        // inject gender into layout
        Layout.setFormGender(this.rootView, pc.getGender());

        // inject age into layout
        Layout.setFormAge(this.rootView, pc.getAge());

        // inject job into layout
        Layout.setFormJob(this.rootView, pc.getJob());

        return this.rootView;
    }

    @Override
    public void onPause(){
        // call the parent method
        super.onPause();

        // save changes to the app-time buffer
        commitChanges(rootView);
    }
}