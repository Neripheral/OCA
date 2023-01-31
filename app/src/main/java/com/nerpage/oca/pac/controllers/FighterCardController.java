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
import com.nerpage.oca.util.AnimatedDrawable;
import com.nerpage.oca.util.Callback;

/**
 * Node responsible for display and manipulation of a fighter card present during a fight.
 * It's supposed to be a representation of an enemy and their stats.
 */
public final class FighterCardController extends AbstractController<FighterCardModel, FighterCardPresenter> {
    /**
     * Updates this card's title.
     * @param newTitle a new title of type String
     */
    public void updateTitle(String newTitle) {
        getModel().ifPresent(m-> {
            if(!m.getTitle().equals(newTitle))
                m.setTitle(newTitle);
        });
    }

    /**
     * Updates this card's current blood level.
     * @param newCurrentBlood a new current blood level of type int
     */
    public void updateCurrentBlood(int newCurrentBlood) {
        String stringCurrentBlood = String.valueOf(newCurrentBlood);
        getModel().ifPresent(m-> {
            if(!m.getCurrentBlood().equals(stringCurrentBlood))
                m.setCurrentBlood(stringCurrentBlood);
        });
    }

    /**
     * Updates this card's maximum blood level.
     * @param newMaxBlood a new current blood level of type int
     */
    public void updateMaxBlood(int newMaxBlood) {
        String stringMaxBlood = String.valueOf(newMaxBlood);
        getModel().ifPresent(m-> {
            if(!m.getMaxBlood().equals(stringMaxBlood))
                m.setMaxBlood(stringMaxBlood);
        });
    }

    public void playAnimation(int animationResId, int duration, float scale, Callback onAnimationEnd){

    }

    public void playEffect(AnimatedDrawable animatedDrawable){
        getPresenter().ifPresent(p->
                p.playEffectOnAvatar(animatedDrawable)
        );
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