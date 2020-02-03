package com.example.oca.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oca.AttributeAdapter;
import com.example.oca.AttributeModel;
import com.example.oca.CharacterViewerActivity;
import com.example.oca.R;
import com.example.oca.classes.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;



public class AttributesFragment extends Fragment {
    public View rootView = null;

    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }

    public static class Layout{
        public static RecyclerView getRecycler(View rootView){
            // fetch the recycler from layout
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.attributes_recycler);
            return recyclerView;
        }

    }

    public AttributesFragment() {
        // Required empty public constructor
    }

    public PlayerCharacter getPlayerCharacterData(){
        return ((CharacterViewerActivity) getActivity()).pc;
    }

    public List<AttributeModel> getDataset(){
        // create a list that will be returned
        List<AttributeModel> toReturn = new ArrayList<>();

        // get the PC data
        PlayerCharacter pc = getPlayerCharacterData();

        // add all of the attributes
        //// Vitality
        toReturn.add(new AttributeModel(
                pc.getAttribute(pc.VITALITY),
                getString(R.string.attribute_vitality),
                R.drawable.attribute_vitality));
        //// Reflex
        toReturn.add(new AttributeModel(
                pc.getAttribute(pc.REFLEX),
                getString(R.string.attribute_reflex),
                R.drawable.attribute_reflex));
        //// Organism
        toReturn.add(new AttributeModel(
                pc.getAttribute(pc.ORGANISM),
                getString(R.string.attribute_organism),
                R.drawable.attribute_organism));

        //TODO: add the rest of the attributes

        // return a created list
        return toReturn;
    }

    public void updateData(int value, int position){
        AttributeModel model =((AttributeAdapter)Layout.getRecycler(rootView).getAdapter()).dataset.get(position);
        model.counter = value;
        ((AttributeAdapter)Layout.getRecycler(rootView).getAdapter()).dataset.set(position, model);
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
        updateData(currentCounter, position);
        Layout.getRecycler(rootView).getAdapter().notifyItemChanged(position, currentCounter);
    }

    public void incrementAttributeCounter(int attrPosition){
        int currentCounter = getPlayerCharacterData().getAttribute(attrPosition);
        getPlayerCharacterData().setAttribute(attrPosition, currentCounter+1);
    }

    public void decrementAttributeCounter(int attrPosition){
        int currentCounter = getPlayerCharacterData().getAttribute(attrPosition);
        getPlayerCharacterData().setAttribute(attrPosition, currentCounter-1);
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

        RecyclerView.Adapter mAdapter = new AttributeAdapter(getDataset(), new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                clickOperator(view, position);
            }
        });
        recyclerView.setAdapter(mAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new AttributeAdapter.SpacesItemDecoration(spacingInPixels));
        return this.rootView;
    }
}