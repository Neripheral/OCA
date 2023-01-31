package com.nerpage.oca.pac.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.nerpage.oca.pac.AbstractController;
import com.nerpage.oca.pac.models.FighterCardModel;
import com.nerpage.oca.pac.presenters.DefaultFighterCardPresenter;
import com.nerpage.oca.pac.presenters.FighterCardPresenter;

public final class FighterCardController extends AbstractController<FighterCardModel, FighterCardPresenter> {
    public void updateTitle(String newTitle) {
        getModel().ifPresent(m-> {
            if(!m.getTitle().equals(newTitle))
                m.setTitle(newTitle);
        });
    }

    public void updateCurrentBlood(String newCurrentBlood) {
        getModel().ifPresent(m-> {
            if(!m.getCurrentBlood().equals(newCurrentBlood))
                m.setCurrentBlood(newCurrentBlood);
        });
    }

    public void updateMaxBlood(String newMaxBlood) {
        getModel().ifPresent(m-> {
            if(!m.getMaxBlood().equals(newMaxBlood))
                m.setMaxBlood(newMaxBlood);
        });
    }

    public void playEffect(int resId, int duration, float scale, Runnable after){
        getPresenter().ifPresent(p->
                p.playEffectOnAvatar(resId, duration, scale, after)
        );
    }

    public void updatePresentation(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel(new ViewModelProvider(this).get(FighterCardModel.class));
        initModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(FighterCardPresenter.getDescribedLayoutId(), container, false);
        if (tryBuildPresenter(root).isPresent())
            initPresenter();
        return root;
    }

    private void initModel() {
        FighterCardModel m = requireModel();
        m.setOnTitleChanged(this, this::onTitleChanged);
        m.setOnCurrentBloodChanged(this, this::onCurrentBloodChanged);
        m.setOnMaxBloodChanged(this, this::onMaxBloodChanged);
    }

    private void initPresenter() {
        FighterCardPresenter p = requirePresenter();

        getModel().ifPresent(m ->{
                    p.updateTitle(m.getTitle());
                    p.updateCurrentBlood(m.getCurrentBlood());
                    p.updateMaxBlood(m.getMaxBlood());
                }
        );
    }

    private void onTitleChanged(String title){
        getPresenter().ifPresent(
                p -> p.updateTitle(title)
        );
    }

    private void onCurrentBloodChanged(String currentBlood){
        getPresenter().ifPresent(
                p -> p.updateCurrentBlood(currentBlood)
        );
    }

    private void onMaxBloodChanged(String maxBlood){
        getPresenter().ifPresent(
                p -> p.updateMaxBlood(maxBlood)
        );
    }

    public FighterCardController() {
        setPresenterFactory(new DefaultFighterCardPresenter.DefaultFighterCardPresenterFactory());
    }
}