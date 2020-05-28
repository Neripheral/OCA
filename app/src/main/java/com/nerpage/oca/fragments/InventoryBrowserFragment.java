package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterManagerActivity;
import com.nerpage.oca.adapters.InventoryAdapter;
import com.nerpage.oca.classes.ItemDatabase;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.misc.SpacesItemDecoration;
import com.nerpage.oca.models.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
/*
        ItemStorage itemStorage = getPlayerCharacterData().getInventory();
        ArrayList<Item> items = itemStorage.getStoredItems();
        for(Item item : items){
            dataset.add(this.composeDatasetEntryFor(item));
        }
*/
        Map<String, Class<Item>> allItems = ItemDatabase.getItemList();
        for(Map.Entry<String, Class<Item>> entry : allItems.entrySet()){
            Class<Item> itemClass = entry.getValue();
            try {
                Item item;
                item = itemClass.newInstance();
                dataset.add(this.composeDatasetEntryFor(item));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
        return dataset;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.character_manager_toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.rootView = inflater.inflate(R.layout.fragment_item_browser, container, false);


        RecyclerView recyclerView = Layout.getRecycler(this.rootView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new InventoryAdapter(this.getDataset());
        recyclerView.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        return this.rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inventory_browser_action_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public InventoryBrowserFragment(){
        //Empty constructor
    }
}
