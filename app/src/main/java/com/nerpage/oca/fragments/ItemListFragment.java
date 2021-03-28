package com.nerpage.oca.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.adapters.ItemListAdapter;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.interfaces.Inventory;
import com.nerpage.oca.misc.RecyclerViewClickListener;
import com.nerpage.oca.misc.SpacesItemDecoration;
import com.nerpage.oca.models.ItemModel;

import java.util.ArrayList;
import java.util.List;


public class ItemListFragment extends Fragment implements Inventory.ParentInventoryNotifier{
    public interface LayoutListener extends RecyclerViewClickListener {}
    public Runnable callback;

    private ItemStorage correspondingInventory = new ItemStorage();
    public View rootView = null;

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    public static final class Layout{
        public static RecyclerView getRecycler(View rootView){
            return (RecyclerView) rootView.findViewById(R.id.inv_browser_recycler);
        }
    }

    public ItemListFragment setCorrespondingInventory(ItemStorage correspondingInventory) {
        this.correspondingInventory = correspondingInventory;
        return this;
    }

    public void refreshRecyclerData(int position){
        this.getAdapter().dataset.get(position).refreshData();
        getAdapter().notifyItemChanged(position);
        if(getParentFragment() instanceof ItemListFragment){
            this.callback.run();
        }
    }

    public void updateRecyclerHolder(){
        setupRecycler(false);

        if(getParentFragment() instanceof ItemListFragment){
            this.callback.run();
        }
    }

    @Override
    public void onChildChanged(int position) {
        refreshRecyclerData(position);
    }

    public ItemListFragment setOnChildChangedCallback(Runnable callback){
        this.callback = callback;
        return this;
    }

    public void onRemoveButtonClick(int position){
        AlertDialog dialog = this.getAdapter().dataset.get(position).getItemRef().get().removeByDialog(new AlertDialog.Builder(getActivity()), (Item removed) -> {
            //getCorrespondingInventory().cleanEmptyItems();

        });
        if(dialog != null){
            dialog.show();
        }
        //updateRecyclerHolder();
    }

    public void onNestedInventoryShowMoreButtonClick(int position){
        ((Inventory)this.getAdapter().dataset.get(position).getItemRef().get()).setOpen(true);
        refreshRecyclerData(position);
    }

    public void onNestedInventoryShowLessButtonClick(int position){
        ((Inventory)this.getAdapter().dataset.get(position).getItemRef().get()).setOpen(false);
        refreshRecyclerData(position);
    }

    public void moveToHoldingSpace(Item item){
        ((ItemListFragment)getParentFragment()).moveToHoldingSpace(item);
    }

    public Item moveFromHoldingSpace(){
        Item item = ((ItemListFragment)getParentFragment()).moveFromHoldingSpace();
        return item;
    }

    public void onMoveToHandsPressed(int position){
        AlertDialog dialog = this.getAdapter().dataset.get(position).getItemRef().get().moveByDialog(new AlertDialog.Builder(getActivity()), (Item moved) -> {
            this.moveToHoldingSpace(moved);
            getCorrespondingInventory().cleanEmptyItems();
            updateRecyclerHolder();
        });
        if(dialog != null){
            dialog.show();
        }
    }

    public void onMoveFromHandsPressed(int position){
        Item item = this.moveFromHoldingSpace();
        if(item != null) {
            ((Inventory) getAdapter().dataset.get(position).getItemRef().get()).getInventory().add(item);
            updateRecyclerHolder();
        }
    }

    public void clickOperator(View view, int position) {
        switch(view.getId()){
            case R.id.item_removebtn:
                this.onRemoveButtonClick(position);
                break;
            case R.id.item_inventory_expandbtn:
                this.onNestedInventoryShowMoreButtonClick(position);
                break;
            case R.id.item_inventory_collapsebtn:
                this.onNestedInventoryShowLessButtonClick(position);
                break;
            case R.id.item_holdingspace_givebtn:
                this.onMoveToHandsPressed(position);
                break;
            case R.id.item_holdingspace_takebtn:
                this.onMoveFromHandsPressed(position);
                break;
        }
    }

    public ItemStorage getCorrespondingInventory(){
        return this.correspondingInventory;
    }

    public void addItemToCorrespondingInventory(Item item){
        getCorrespondingInventory().add(item);
        Toast toast = Toast.makeText(getContext(), item.composeOnAddToastMessage(getContext()), Toast.LENGTH_SHORT);
        toast.show();
    }

    public ItemModel composeDatasetEntryFor(Item item){
        return new ItemModel(item, getContext());
    }

    public List<ItemModel> getDataset(){
        List<ItemModel> dataset = new ArrayList<>();
        ItemStorage itemStorage = getCorrespondingInventory();
        ArrayList<Item> items = itemStorage.getStoredItems();
        for(Item item : items){
            dataset.add(this.composeDatasetEntryFor(item));
        }
        return dataset;
    }

    public ItemListAdapter getAdapter(){
        return (ItemListAdapter)Layout.getRecycler(this.rootView).getAdapter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ItemListAdapter.Workmode getAdapterWorkMode(){
        return ItemListAdapter.Workmode.PCINVENTORY;
    }

    public void setupRecycler(boolean withDecoration){
        RecyclerView recyclerView = Layout.getRecycler(this.rootView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new ItemListAdapter(getActivity(), this, this.getDataset(), new LayoutListener(){
            @Override
            public void onClick(View view, int position) {
                clickOperator(view, position);
            }
        }, this.getAdapterWorkMode());
        recyclerView.setAdapter(adapter);
        if(withDecoration) {
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
            recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.rootView = inflater.inflate(R.layout.fragment_item_browser, container, false);
        setupRecycler(true);
        return this.rootView;
    }

    public ItemListFragment(){
        //Empty constructor
    }
}
