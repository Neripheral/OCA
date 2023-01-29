package com.nerpage.oca.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nerpage.oca.pac.AbstractController;
import com.nerpage.oca.fragments.models.ExampleModel;
import com.nerpage.oca.pac.presenters.DefaultExamplePresenter;
import com.nerpage.oca.pac.presenters.ExamplePresenter;

/**
 * @see ExampleModel
 * @see ExamplePresenter
 */


public final class ExampleFragment extends AbstractController<ExampleModel, ExamplePresenter> {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(ExamplePresenter.getDescribedLayoutId(), container, false);
        getPresenterFactory().ifPresent(
                f -> {
                    ExamplePresenter p = f.createFor(root);
                    p.setOnConfirmButtonPressedCallback(this::onConfirmButtonPressed);
                    setPresenter(p);
                }
        );
        return root;
    }

    private void onConfirmButtonPressed(){
        ExamplePresenter p = getPresenter().orElseThrow(IllegalStateException::new);
        String newTitlesText = p.getInputFieldText();
        p.setTitle(newTitlesText);
    }

    public ExampleFragment(){
        setPresenterFactory(new DefaultExamplePresenter.Factory());
    }
}
