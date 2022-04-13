package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nerpage.oca.fragments.presenters.ActionsRecyclerPresenter;
import com.nerpage.oca.fragments.models.ActionsRecyclerModel;

public final class ActionsRecyclerFragment extends PACFragment<ActionsRecyclerModel, ActionsRecyclerPresenter> {
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

    public void updateModel(/*arg list*/) {
        //main, public model updating method
    }

    public void updatePresentation() {
        //main, public presentation updating method
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Fragment overrides


    @Override
    public void initPAC() {
        //default initPAC, not necessary to change
        m = new ActionsRecyclerModel();
        p = new ActionsRecyclerPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //default onCreateView, not necessary to change
        root = inflater.inflate(p.getDescribedLayoutId(), container, false);
        p.setRoot(root);
        return root;
    }

    // endregion //         Fragment overrides
    //================================================================================
}
