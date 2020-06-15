package com.nerpage.oca.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.adapters.ItemListAdapter;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.interfaces.Inventory;
import com.nerpage.oca.misc.RecyclerViewClickListener;
import com.nerpage.oca.misc.SpacesItemDecoration;
import com.nerpage.oca.models.ItemModel;

import java.util.ArrayList;
import java.util.List;


public class ItemListFragment extends Fragment implements Inventory.ParentInventoryNotifier{
    public interface LayoutListener extends RecyclerViewClickListener {}

    private ItemStorage correspondingInventory = new ItemStorage();
    public View rootView = null;
    public View selectedItem = null;

    public static final class Layout{
        public static RecyclerView getRecycler(View rootView){
            return (RecyclerView) rootView.findViewById(R.id.inv_browser_recycler);
        }

    }

    public ItemListFragment setCorrespondingInventory(ItemStorage correspondingInventory) {
        this.correspondingInventory = correspondingInventory;
        return this;
    }

    public void refreshRecyclerData(){
        for(int i = 0; i < getAdapter().dataset.size(); i++)
            getAdapter().dataset.get(i).refreshData();
        getAdapter().notifyDataSetChanged();
        if(getParentFragment() instanceof ItemListFragment){
            ((Inventory.ParentInventoryNotifier)getParentFragment()).onChildChanged();
        }
    }

    public void updateRecyclerHolder(int position){
        if(position != -1){
            ItemModel oldModel = getAdapter().dataset.get(position);
            ItemModel newModel = composeDatasetEntryFor(oldModel.getItemRef().get());
            getAdapter().dataset.set(position, newModel);
            getAdapter().dataset.get(position).setNestedInvVisible(oldModel.isNestedInvVisible());
            getAdapter().dataset.get(position).setNestedInventoryHolderId(oldModel.getNestedInventoryHolderId());
            getAdapter().notifyItemChanged(position);
        } else{
            getAdapter().dataset = getDataset();
            getAdapter().notifyDataSetChanged();
        }

        if(getParentFragment() instanceof ItemListFragment){
            ((Inventory.ParentInventoryNotifier)getParentFragment()).onChildChanged();
        }
    }

    public void updateRecyclerHolder(){
        this.updateRecyclerHolder(-1);
    }

    @Override
    public void onChildChanged() {
        refreshRecyclerData();
    }

    public void onRemoveButtonClick(int position){
        AlertDialog dialog = this.getAdapter().dataset.get(position).getItemRef().get().removeByDialog(new AlertDialog.Builder(getActivity()), () -> {
            getCorrespondingInventory().cleanEmptyItems();
            updateRecyclerHolder();
        });
        if(dialog != null){
            dialog.show();
        }
    }

    public void onNestedInventoryShowMoreButtonClick(int position){
        ((Inventory)this.getAdapter().dataset.get(position).getItemRef().get()).setOpen(true);
        refreshRecyclerData();
    }

    public void onNestedInventoryShowLessButtonClick(int position){
        ((Inventory)this.getAdapter().dataset.get(position).getItemRef().get()).setOpen(false);
        refreshRecyclerData();
    }

    public void moveToHands(Item item){
        ((ItemListFragment)getParentFragment()).moveToHands(item);
        updateRecyclerHolder();
    }

    public void onMoveToHandsPressed(int position){
        this.moveToHands(getAdapter().dataset.get(position).getItemRef().get());
    }

    public void onMoveFromHandsPressed(int position){
    }

    public void clickOperator(View view, int position) {
        switch(view.getId()){
            case R.id.inventory_item_remove_button:
                this.onRemoveButtonClick(position);
                break;
            case R.id.inventory_item_nested_inv_menu_more_button:
                this.onNestedInventoryShowMoreButtonClick(position);
                break;
            case R.id.inventory_item_nested_inv_menu_less_button:
                this.onNestedInventoryShowLessButtonClick(position);
                break;
            case R.id.inventory_item_move_to_hand_button:
                this.onMoveToHandsPressed(position);
                break;
            case R.id.inventory_item_move_from_hand_button:
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

    public int getAdapterWorkMode(){
        return ItemListAdapter.WORKMODE_PCINVENTORY;
    }

    public void setupRecycler(){
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
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.rootView = inflater.inflate(R.layout.fragment_item_browser, container, false);
        setupRecycler();
        return this.rootView;
    }

    public ItemListFragment(){
        //Empty constructor
    }
}
