package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Equipment;

import java.util.HashMap;
import java.util.Map;

public class ArmorStandHumanFragment extends Fragment {
    public static class Layout{
        public enum ArmorStandSlot{
            SHIRT(R.id.img_armorpiece_shirt),
            LEFT_GLOVE(R.id.img_armorpiece_gloveleft),
            RIGHT_GLOVE(R.id.img_armorpiece_gloveright);

            int resId;

            ArmorStandSlot(int resId){
                this.resId = resId;
            }

            void show(View rootView){
                rootView.findViewById(this.resId).setVisibility(View.VISIBLE);
            }

            void hide(View rootView){
                rootView.findViewById(this.resId).setVisibility(View.GONE);
            }
        }
    }

    //================================================================================
    // Fields
    //================================================================================

    public View rootView;

    //================================================================================
    // Methods
    //================================================================================

    void hideAllPieces(){
        for(Layout.ArmorStandSlot slot: Layout.ArmorStandSlot.values()){
            slot.hide(rootView);
        }
    }

    void setupFromEquipment(Equipment eq){
        hideAllPieces();
        Map<Equipment.Slot, Layout.ArmorStandSlot> map = new HashMap<>();

        map.put(Equipment.Slot.SHIRT, Layout.ArmorStandSlot.SHIRT);
        map.put(Equipment.Slot.RIGHT_HAND, Layout.ArmorStandSlot.RIGHT_GLOVE);
        map.put(Equipment.Slot.LEFT_HAND, Layout.ArmorStandSlot.LEFT_GLOVE);

        for(Map.Entry<Equipment.Slot, Layout.ArmorStandSlot> entry : map.entrySet())
            if(eq.getSlots().containsKey(entry.getKey()))
                entry.getValue().show(rootView);


    }

    //================================================================================
    // Overrides
    //================================================================================

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_armor_stand_human, container);
        this.hideAllPieces();
        return this.rootView;
    }
}
