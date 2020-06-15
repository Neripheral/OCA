package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.classes.ItemDatabase;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.models.ItemModel;

public class InventoryManagerFragment extends ItemListFragment {
    public static class Layout{
        public static int getHoldingSpaceId(){
            return R.id.inventory_held_item_include;
        }

        public static View getHoldingSpaceView(View root){
            return root.findViewById(getHoldingSpaceId());
        }

        public static void showHoldingSpace(View root){
            getHoldingSpaceView(root).setVisibility(View.VISIBLE);
        }

        public static void hideHoldingSpace(View root){
            getHoldingSpaceView(root).setVisibility(View.GONE);
        }

    }

    public PlayerCharacter getPCData(){
        return ((CharacterEditorActivity) getActivity()).pc;
    }

    @Override
    public ItemStorage getCorrespondingInventory() {
        return getPCData().getInventory();
    }

    public void updateHoldingSpaceView(){
        Item heldItem = getPCData().getItemInHands();
        if(heldItem == null)
            Layout.hideHoldingSpace(rootView);
        else{
            Layout.showHoldingSpace(rootView);
            ItemModel model = new ItemModel(heldItem, this.getActivity());
            model.initLayoutHelperFor(Layout.getHoldingSpaceView(rootView), getChildFragmentManager())
                    .prepareHolder()
                    .showSideMenu()
                    .showRemoveButton()
                    .setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch(v.getId()){
                                case R.id.inventory_item_remove_button:
                                    getPCData().unequipFromHands();
                            }
                            Log.e("debug", "test");
                        }
                    });
        }
    }

    @Override
    public void moveToHands(Item item) {
        getPCData().equipInHands((Item)item.clone());
        item.discard();
        getCorrespondingInventory().cleanEmptyItems();
        updateHoldingSpaceView();
        updateRecyclerHolder();
    }

    public void onAddNewItemButtonPressed(){
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.nav_itemdb_selector);
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
        this.setupRecycler();
        this.updateHoldingSpaceView();
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
