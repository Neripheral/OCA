package com.nerpage.oca.pac.controllers.implementation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.nerpage.oca.pac.AbstractController;
import com.nerpage.oca.pac.Presenter;
import com.nerpage.oca.pac.controllers.ExampleController;
import com.nerpage.oca.pac.models.ExampleModel;
import com.nerpage.oca.pac.presenters.DefaultExamplePresenter;
import com.nerpage.oca.pac.presenters.ExamplePresenter;

/**
 * Node serving as a helping template and for testing purposes.
 */
public final class DefaultExampleController extends ExampleController {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel(new ViewModelProvider(this).get(ExampleModel.class));
        initModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(ExamplePresenter.getDescribedLayoutId(), container, false);
        if(tryBuildPresenter(root).isPresent())
            initPresenter();
        return root;
    }

    private void initModel(){
        ExampleModel m = requireModel();
        m.setOnTitleChanged(this, this::onTitleChanged);
    }

    private void initPresenter(){
        ExamplePresenter p = requirePresenter();
        p.setOnConfirmButtonPressedCallback(this::onConfirmButtonPressed);

        getModel().ifPresent(m ->
                p.setTitle(m.getTitle())
        );
    }

    private void onConfirmButtonPressed(){
        String newTitlesText = requirePresenter().getInputFieldText();
        getModel().ifPresent(m -> m.setTitle(newTitlesText));
    }

    private void onTitleChanged(String title){
        getPresenter().ifPresent(p->p.setTitle(title));
    }

    public DefaultExampleController(){
        setPresenterFactory(new DefaultExamplePresenter.DefaultExamplePresenterFactory());
    }
}
