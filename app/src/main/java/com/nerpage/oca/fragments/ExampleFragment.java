package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nerpage.oca.fragments.presenters.ExamplePresenter;
import com.nerpage.oca.fragments.models.ExampleModel;

/**
 * @see ExampleModel
 * @see ExamplePresenter
 */
public final class ExampleFragment extends PACFragment<ExampleModel, ExamplePresenter> {
    //================================================================================
    // region //            Fragment overrides

    @Override
    public void initPAC() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //default onCreateView, not necessary to change
        root = inflater.inflate(ExamplePresenter.getDescribedLayoutId(), container, false);
        p = new ExamplePresenter(root);
        p.setOnConfirmButtonPressedCallback(this::onConfirmButtonPressed);
        return root;
    }

    private void onConfirmButtonPressed(){
        String newTitlesText = p.getInputFieldText();
        p.setTitle(newTitlesText);
    }

    // endregion //         Fragment overrides
    //================================================================================
}
