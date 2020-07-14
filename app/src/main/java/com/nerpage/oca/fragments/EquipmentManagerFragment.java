package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.adapters.ItemListAdapter;
import com.nerpage.oca.classes.Equipment;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.classes.ItemDatabase;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.interfaces.Equipable;
import com.nerpage.oca.models.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EquipmentManagerFragment extends ItemListFragment {
    //================================================================================
    // Fields
    //================================================================================

    public ArmorStandHumanFragment armorStandFragment;

    //================================================================================
    // Methods
    //================================================================================

    public void refresh(){
        this.updateRecyclerHolder();
        this.armorStandFragment.setupFromEquipment(getPCData().getEquipment());
    }

    public PlayerCharacter getPCData(){
        return ((CharacterEditorActivity)this.getActivity()).pc;
    }

    public void onEquipButton(int position){
        Equipable item = (Equipable)getAdapter().dataset.get(position).getItemRef().get();
        if(getPCData().getItemInHands() == null)
            getPCData().getEquipment().unequip(item.getEquipableSlot(), true);
        refresh();
    }

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public void clickOperator(View view, int position) {
        switch(view.getId()){
            case R.id.inventory_item_equip_button:
                this.onEquipButton(position);
                break;
        }
        super.clickOperator(view, position);
    }

    @Override
    public ItemListAdapter.Workmode getAdapterWorkMode() {
        return ItemListAdapter.Workmode.EQUIPMENT;
    }

    @Override
    public List<ItemModel> getDataset() {
        Map<Equipment.Slot, Item> slots = this.getPCData().getEquipment().getSlots();
        List<ItemModel> dataset = new ArrayList<>();
        for(Map.Entry<Equipment.Slot, Item> slot : slots.entrySet()){
            if(slot.getKey() == Equipment.Slot.RIGHT_PALM)
                continue;
            dataset.add(new ItemModel(slot.getValue(), this.getContext()));
        }
        return dataset;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_equipment_manager, container, false);
        this.setupRecycler(true);
        this.armorStandFragment = (ArmorStandHumanFragment)getChildFragmentManager().findFragmentById(R.id.equipment_armor_stand_fragment);
        this.armorStandFragment.setupFromEquipment(getPCData().getEquipment());
        return this.rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.refresh();
    }
}
