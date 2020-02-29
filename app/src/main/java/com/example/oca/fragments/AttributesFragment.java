package com.example.oca.fragments;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oca.AttributeAdapter;
import com.example.oca.models.AttributeModel;
import com.example.oca.CharacterViewerActivity;
import com.example.oca.R;
import com.example.oca.RecyclerViewClickListener;
import com.example.oca.SpacesItemDecoration;
import com.example.oca.classes.PlayerCharacter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AttributesFragment extends Fragment {
    public View rootView = null;

    public static class Layout{
        public static RecyclerView getRecycler(View rootView){
            // fetch the recycler from layout
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.attributes_recycler);
            return recyclerView;
        }

        public static FloatingActionButton getFloatingActionButton(View rootView){
            FloatingActionButton fabView = (FloatingActionButton) rootView.findViewById(R.id.attributes_floatingActionButton);
            return fabView;
        }

        public static TextView getFABRemainingPoints(View rootView){
            TextView textView = (TextView) rootView.findViewById(R.id.attributes_floatingActionButton_remainingPoints);
            return textView;
        }

        public static ConstraintLayout getFABRemainingPointsContainer(View rootView){
            ConstraintLayout layout = (ConstraintLayout) rootView.findViewById(R.id.attributes_floatingActionButton_remainingPoints_container);
            return layout;
        }
    }

    public interface AttributeLayoutChangeListener extends RecyclerViewClickListener{}

    public AttributesFragment() {
        // Required empty public constructor
    }

    public PlayerCharacter getPlayerCharacterData(){
        return ((CharacterViewerActivity) getActivity()).pc;
    }

    private int getCorrespondingBalanceColor(int balance){
        if(balance > 0)
            return R.color.optionSemiwrong;
        if(balance == 0)
            return R.color.optionCorrect;
        return R.color.optionWrong;
    }

    public List<AttributeModel> getDataset(){
        // create a list that will be returned
        List<AttributeModel> toReturn = new ArrayList<>();

        // get the PC data
        PlayerCharacter pc = getPlayerCharacterData();

        // add all of the attributes

        //// Vitality
        AttributeModel model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.VITALITY))
                .setTitle(getString(R.string.attribute_vitality))
                .setImageId(R.drawable.attribute_vitality)
                .setDescription(getString(R.string.attribute_vitality_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_vitality_counterDescriptions)[model.getCounter()]);
        toReturn.add(model);

        //// Reflex
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.REFLEX))
                .setTitle(getString(R.string.attribute_reflex))
                .setImageId(R.drawable.attribute_reflex)
                .setDescription(getString(R.string.attribute_reflex_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_reflex_counterDescriptions)[model.getCounter()])
                .setParentImageId(R.drawable.attribute_vitality)
                .setBalance(pc.getBalance(pc.REFLEX))
                .setBalanceColor(this.getCorrespondingBalanceColor(model.getBalance()));
        toReturn.add(model);

        //// Organism
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.ORGANISM))
                .setTitle(getString(R.string.attribute_organism))
                .setImageId(R.drawable.attribute_organism)
                .setDescription(getString(R.string.attribute_organism_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_organism_counterDescriptions)[model.getCounter()])
                .setParentImageId(R.drawable.attribute_vitality)
                .setBalance(pc.getBalance(pc.ORGANISM))
                .setBalanceColor(this.getCorrespondingBalanceColor(model.getBalance()));
        toReturn.add(model);

        //// Condition
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.CONDITION))
                .setTitle(getString(R.string.attribute_condition))
                .setImageId(R.drawable.attribute_condition)
                .setDescription(getString(R.string.attribute_condition_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_condition_counterDescriptions)[model.getCounter()]);
        toReturn.add(model);

        //// Strength
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.STRENGTH))
                .setTitle(getString(R.string.attribute_strength))
                .setImageId(R.drawable.attribute_strength)
                .setDescription(getString(R.string.attribute_strength_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_strength_counterDescriptions)[model.getCounter()])
                .setParentImageId(R.drawable.attribute_condition)
                .setBalance(pc.getBalance(pc.CONDITION))
                .setBalanceColor(this.getCorrespondingBalanceColor(model.getBalance()));
        toReturn.add(model);

        //// Agility
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.AGILITY))
                .setTitle(getString(R.string.attribute_agility))
                .setImageId(R.drawable.attribute_agility)
                .setDescription(getString(R.string.attribute_agility_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_agility_counterDescriptions)[model.getCounter()])
                .setParentImageId(R.drawable.attribute_condition)
                .setBalance(pc.getBalance(pc.AGILITY))
                .setBalanceColor(this.getCorrespondingBalanceColor(model.getBalance()));
        toReturn.add(model);

        //// Psyche
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.PSYCHE))
                .setTitle(getString(R.string.attribute_psyche))
                .setImageId(R.drawable.attribute_psyche)
                .setDescription(getString(R.string.attribute_psyche_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_psyche_counterDescriptions)[model.getCounter()]);
        toReturn.add(model);

        //// Discipline
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.DISCIPLINE))
                .setTitle(getString(R.string.attribute_discipline))
                .setImageId(R.drawable.attribute_discipline)
                .setDescription(getString(R.string.attribute_discipline_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_discipline_counterDescriptions)[model.getCounter()])
                .setParentImageId(R.drawable.attribute_psyche)
                .setBalance(pc.getBalance(pc.PSYCHE))
                .setBalanceColor(this.getCorrespondingBalanceColor(model.getBalance()));
        toReturn.add(model);

        //// Intelligence
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.INTELLIGENCE))
                .setTitle(getString(R.string.attribute_intelligence))
                .setImageId(R.drawable.attribute_intelligence)
                .setDescription(getString(R.string.attribute_intelligence_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_intelligence_counterDescriptions)[model.getCounter()])
                .setParentImageId(R.drawable.attribute_psyche)
                .setBalance(pc.getBalance(pc.INTELLIGENCE))
                .setBalanceColor(this.getCorrespondingBalanceColor(model.getBalance()));
        toReturn.add(model);

        //// Charisma
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.CHARISMA))
                .setTitle(getString(R.string.attribute_charisma))
                .setImageId(R.drawable.attribute_charisma)
                .setDescription(getString(R.string.attribute_charisma_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_charisma_counterDescriptions)[model.getCounter()]);
        toReturn.add(model);

        //// Empathy
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.EMPATHY))
                .setTitle(getString(R.string.attribute_empathy))
                .setImageId(R.drawable.attribute_empathy)
                .setDescription(getString(R.string.attribute_empathy_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_empathy_counterDescriptions)[model.getCounter()])
                .setParentImageId(R.drawable.attribute_charisma)
                .setBalance(pc.getBalance(pc.CHARISMA))
                .setBalanceColor(this.getCorrespondingBalanceColor(model.getBalance()));
        toReturn.add(model);

        //// Manipulation
        model = new AttributeModel();
        model.setCounter(pc.getAttribute(pc.MANIPULATION))
                .setTitle(getString(R.string.attribute_manipulation))
                .setImageId(R.drawable.attribute_manipulation)
                .setDescription(getString(R.string.attribute_manipulation_description))
                .setCounterDescription(getResources().getStringArray(R.array.attribute_manipulation_counterDescriptions)[model.getCounter()])
                .setParentImageId(R.drawable.attribute_charisma)
                .setBalance(pc.getBalance(pc.MANIPULATION))
                .setBalanceColor(this.getCorrespondingBalanceColor(model.getBalance()));
        toReturn.add(model);

        if(pc.areAttributesCommitted())
            for(AttributeModel m : toReturn){
                m.setCommitted(true);
            }

        // return a created list
        return toReturn;
    }

    private void updateRecyclerData(){
        ((AttributeAdapter)Layout.getRecycler(rootView).getAdapter()).dataset = getDataset();
        Layout.getRecycler(rootView).getAdapter().notifyDataSetChanged();
    }

    private void updateFloatingActionButton(){
        // get all needed views
        FloatingActionButton fabView = Layout.getFloatingActionButton(rootView);
        TextView balanceView = Layout.getFABRemainingPoints(rootView);
        ConstraintLayout layout = Layout.getFABRemainingPointsContainer(rootView);

        // set default values
        int imageToSet = android.R.drawable.checkbox_off_background;
        int balanceVisibility = View.VISIBLE;

        if(getPlayerCharacterData().areAttributesCorrect()){
            imageToSet = android.R.drawable.checkbox_on_background;
            balanceVisibility = View.GONE;
        }
        fabView.setImageResource(imageToSet);
        balanceView.setText(String.valueOf(getPlayerCharacterData().ATTRIBUTES_POINTS - getPlayerCharacterData().countCurrentMainAttributesAmount()));
        layout.setVisibility(balanceVisibility);
    }

    public void clickOperator(View v, int position){
        switch(v.getId()){
            case R.id.attributes_attribute_incrementButton:
                incrementAttributeCounter(position);
                break;
            case R.id.attributes_attribute_decrementButton:
                decrementAttributeCounter(position);
                break;
            default:
                Log.d("Warning", "clickOperator error in AttributeAdapter: button not recognized");
        }
        int currentCounter = getPlayerCharacterData().getAttribute(position);
        updateRecyclerData();
        updateFloatingActionButton();
    }

    private void increasePCAttribute(int pos, int numberToAdd){
        int currentCounter = getPlayerCharacterData().getAttribute(pos);
        getPlayerCharacterData().setAttribute(pos, currentCounter+numberToAdd);
    }

    private void incrementAttributeCounter(int pos){
        increasePCAttribute(pos, 1);
    }

    private void decrementAttributeCounter(int pos){
        increasePCAttribute(pos, -1);
    }

    public boolean commitAttributeChanges(){
        if(getPlayerCharacterData().areAttributesCorrect()) {
            getPlayerCharacterData().setAttributesCommitted(true);
            updateRecyclerData();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_attributes, container, false);
        RecyclerView recyclerView = Layout.getRecycler(rootView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new AttributeAdapter(getDataset(), new AttributeLayoutChangeListener() {
            @Override
            public void onClick(View view, int position) {
                clickOperator(view, position);
            }
        });
        recyclerView.setAdapter(mAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        FloatingActionButton fab = this.rootView.findViewById(R.id.attributes_floatingActionButton);
        updateFloatingActionButton();
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                commitAttributeChanges();
            }
        });

        return this.rootView;
    }
}