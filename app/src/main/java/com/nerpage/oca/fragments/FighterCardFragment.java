package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Fragment overrides


    @Override
    public void initPAC() {
        m = new FighterCardModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(p.getDescribedLayoutId(), container, false);
        p = new FighterCardPresenter(root);
        return root;
    }

    // endregion //         Fragment overrides
    //================================================================================
}
