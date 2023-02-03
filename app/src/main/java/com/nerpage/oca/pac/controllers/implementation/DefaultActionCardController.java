package com.nerpage.oca.pac.controllers.implementation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.nerpage.oca.pac.controllers.ActionCardController;
import com.nerpage.oca.pac.models.ActionCardModel;
import com.nerpage.oca.pac.presenters.ActionCardPresenter;

import java.util.Objects;

public final class DefaultActionCardController extends ActionCardController {
    @Override
    public void setTitle(String title){
        Objects.requireNonNull(title);
        getModel().ifPresent(m-> m.setTitle(title));
    }

    @Override
    public void setThumbnailResId(int resId){
        getModel().ifPresent(m-> m.setThumbnailResId(resId));
    }

    @Override
    public void setDescription(String description){
        Objects.requireNonNull(description);
        getModel().ifPresent(m-> m.setDescription(description));
    }

    @Override
    public void setIsDescriptionBoxOpen(boolean isOpen){
        getModel().ifPresent(m-> m.setOpen(isOpen));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel(new ViewModelProvider(this).get(ActionCardModel.class));
        initModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(ActionCardPresenter.getDescribedLayoutId(), container, false);
        if (tryBuildPresenter(root).isPresent())
            initPresenter();
        return root;
    }



    private void initModel() {
        ActionCardModel m = requireModel();
        m.setOnTitleChanged(this, this::onTitleChanged);
        m.setOnThumbnailResIdChanged(this, this::onThumbnailChanged);
        m.setOnDescriptionChanged(this, this::onDescriptionChanged);
        m.setOnOpenChanged(this, this::onOpenChanged);
    }

    private void initPresenter() {
        ActionCardPresenter p = requirePresenter();

        getModel().ifPresent(m -> {
            p.updateTitle(m.getTitle());
            p.updateThumbnail(m.getThumbnailResId());
            p.updateDescription(m.getDescription());
            p.updateIsOpen(m.isOpen());
        });
    }

    private void onTitleChanged(String title){
        getPresenter().ifPresent(p->p.updateTitle(title));
    }
    private void onThumbnailChanged(int resId){
        getPresenter().ifPresent(p->p.updateThumbnail(resId));
    }
    private void onDescriptionChanged(String description){
        getPresenter().ifPresent(p->p.updateDescription(description));
    }
    private void onOpenChanged(boolean isOpen){
        getPresenter().ifPresent(p->p.updateIsOpen(isOpen));
    }

    public DefaultActionCardController() {
        setPresenterFactory(ActionCardPresenter.getPresenter());
    }
}