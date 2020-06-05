package com.nerpage.oca.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.nerpage.oca.R;
import com.nerpage.oca.adapters.ItemListAdapter;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.models.ItemModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class InventoryManagerFragment extends ItemListFragment {
    public void updateRecyclerHolder(int position){
        getAdapter().dataset = getDataset();
        getAdapter().notifyDataSetChanged();
    }

    public void onRemoveButtonClick(int position){
        AlertDialog dialog = this.getAdapter().dataset.get(position).getItemRef().get().removeByDialog(new AlertDialog.Builder(getActivity()), () -> {
            this.getPlayerCharacterData().getInventory().cleanEmptyItems();
            updateRecyclerHolder(position);
        });
        if(dialog != null){
            dialog.show();
        }
    }

    @Override
    public void clickOperator(View view, int position) {
        switch(view.getId()){
            case R.id.inventory_item_remove_button:
                this.onRemoveButtonClick(position);
                break;
        }
    }

    @Override
    public List<ItemModel> getDataset(){
        List<ItemModel> dataset = new ArrayList<>();

        ItemStorage itemStorage = getPlayerCharacterData().getInventory();
        ArrayList<Item> items = itemStorage.getStoredItems();
        for(Item item : items){
            dataset.add(this.composeDatasetEntryFor(item));
        }
        return dataset;
    }

    public void onAddNewItemButtonPressed(){
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.nav_itemdb_selector);
    }

    @Override
    public int getAdapterWorkMode() {
        return ItemListAdapter.WORKMODE_PCINVENTORY;
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
        this.rootView = inflater.inflate(R.layout.fragment_inventory_manager, container, false);
        super.onCreateView(inflater, rootView.findViewById(R.id.embed_item_browser), savedInstanceState);
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.inventory_browser_add_new_item_button:
                this.onAddNewItemButtonPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inventory_browser_action_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public InventoryManagerFragment(){
        //Empty constructor
    }
}
