package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.fragments.presenters.FighterCardPresenter;
import com.nerpage.oca.fragments.models.FighterCardModel;

public final class FighterCardFragment extends PACFragment<FighterCardModel, FighterCardPresenter> {
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

    //TODO: for compatibility purposes only! after successful Layout->Controller transition this
    // method should become obsolete and should be deleted!
    public void updateModel(com.nerpage.oca.layouts.models.FighterCardModel otherModel){
        m.maxBlood = otherModel.getMaxBlood();
        m.currentBlood = otherModel.getCurrentBlood();
        m.title = otherModel.getTitle();
    }

    public void updateModel(Fighter fighter){
        m.maxBlood = String.valueOf(fighter.getEntity().getMaxBlood());
        m.currentBlood = String.valueOf(fighter.getEntity().getBlood());
        m.title = fighter.getEntity().getName(getContext());
    }

    public void playEffect(int resId, int duration, float scale, Runnable after){
        p.playEffectOnAvatar(resId, duration, scale, after);
    }

    public void updatePresentation() {
        p.updateTitle(m.title);
        p.updateCurrentBlood(m.currentBlood);
        p.updateMaxBlood(m.maxBlood);
    }

    public void updateData(com.nerpage.oca.layouts.models.FighterCardModel newModel){
        updateModel(newModel);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Fragment overrides


    @Override
    public void initPAC() {
        m = new FighterCardModel();
        p = new FighterCardPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.battleground_fightercard, container, false);
        p.setRoot(root);
        return root;
    }

    // endregion //         Fragment overrides
    //================================================================================
}
