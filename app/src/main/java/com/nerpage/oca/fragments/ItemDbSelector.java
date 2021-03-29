package com.nerpage.oca.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.adapters.ItemListAdapter;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.classes.ItemDatabase;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.models.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemDbSelector extends ItemListFragment {
    @Override
    public ItemStorage getCorrespondingInventory() {
        return ((CharacterEditorActivity) getActivity()).getPc().getInventories().get(0).getInventory();
    }

    public List<ItemModel> getDataset(){
        java.util.List<com.nerpage.oca.models.ItemModel> dataset = new ArrayList<>();

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

    public void onItemSelected(String id){
        Item item = ItemDatabase.getItemById(id);
        if(item == null)
            Log.e("exception", "Item id not found in database: " + id);
        else{
            AlertDialog dialog = item.initByDialog(new AlertDialog.Builder(getActivity()), () -> {this.addItemToCorrespondingInventory(item);});
            if(dialog != null) {
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        }
    }

    public void onItemHolderClick(int position){
        ItemModel model = this.getAdapter().dataset.get(position);
        this.onItemSelected(model.getId());
    }

    @Override
    public ItemListAdapter.Workmode getAdapterWorkMode() {
        return ItemListAdapter.Workmode.ITEMDB;
    }

    @Override
    public void clickOperator(View v, int position){
        this.onItemHolderClick(position);
    }

    @Override
    public void onPause() {
        ActionBar actionbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionbar.show();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionbar.hide();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment f = this;
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController controller = NavHostFragment.findNavController(f);
                controller.navigate(R.id.nav_belongings);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.rootView = inflater.inflate(R.layout.fragment_itemdb_selector, container, false);
        this.setupRecycler(true);
        return this.rootView;
    }


    public ItemDbSelector() {
        // Required empty public constructor
    }
}
