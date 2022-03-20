package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.controllers.FighterCardController;
import com.nerpage.oca.layouts.models.FighterCardModel;

public final class FighterCardFragment extends PACFragment<FighterCardController> {
    //================================================================================
    // region //            Fields



    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors



    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods



    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public void playEffect(int resId, int duration, float scale, Runnable after){
        c.playEffect(resId, duration, scale, after);
    }

    public void updateView(){
        c.updatePresentation();
    }

    public void updateData(FighterCardModel newModel){
        c.updateModel(newModel);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Fragment overrides

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = new FighterCardController(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.battleground_fightercard, container, false);
        c.setRoot(root);
        return root;
    }

    // endregion //         Fragment overrides
    //================================================================================
}
