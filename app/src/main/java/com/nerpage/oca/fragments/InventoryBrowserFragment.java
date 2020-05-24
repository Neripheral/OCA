package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterManagerActivity;
import com.nerpage.oca.adapters.InventoryAdapter;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.items.Item;
import com.nerpage.oca.misc.SpacesItemDecoration;
import com.nerpage.oca.models.ItemModel;

import java.util.ArrayList;
import java.util.List;


public class InventoryBrowserFragment extends Fragment {
    public View rootView = null;

    public static final class Layout{
        public static RecyclerView getRecycler(View rootView){
            return (RecyclerView) rootView.findViewById(R.id.inv_browser_recycler);
        }
    }

    public PlayerCharacter getPlayerCharacterData(){
        return ((CharacterManagerActivity) getActivity()).pc;
    }

    public ItemModel composeDatasetEntryFor(Item item){
        return new ItemModel(item, getContext());
    }

    public List<ItemModel> getDataset(){
        List<ItemModel> dataset = new ArrayList<>();

        ItemStorage itemStorage = getPlayerCharacterData().getInventory();
        ArrayList<Item> items = itemStorage.getStoredItems();
        for(Item item : items){
            dataset.add(this.composeDatasetEntryFor(item));
        }

        return dataset;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.rootView = inflater.inflate(R.layout.fragment_inventory_browser, container, false);

        RecyclerView recyclerView = Layout.getRecycler(this.rootView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new InventoryAdapter(this.getDataset());
        recyclerView.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        return this.rootView;
    }

    public InventoryBrowserFragment(){
        //Empty constructor
    }
}
