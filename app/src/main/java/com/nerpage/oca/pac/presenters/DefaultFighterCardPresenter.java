package com.nerpage.oca.pac.presenters;

import android.view.View;
import com.nerpage.oca.pac.AbstractPresenter;
import com.nerpage.oca.util.AnimatedDrawable;
import com.nerpage.oca.util.Presenters;


public final class DefaultFighterCardPresenter extends AbstractPresenter implements FighterCardPresenter {

    @Override
    public void updateTitle(String newTitle){
        Presenters.setText(POI.TITLE, getRoot(), newTitle);
    }

    @Override
    public void updateCurrentBlood(String newCurrentBlood){
        Presenters.setText(POI.CURRENT_BLOOD, getRoot(), newCurrentBlood);
    }

    @Override
    public void updateMaxBlood(String newMaxBlood){
        Presenters.setText(POI.MAX_BLOOD, getRoot(), newMaxBlood);
    }

    @Override
    public void playEffectOnAvatar(AnimatedDrawable animatedDrawable){
        Presenters.playEffect(POI.EFFECT, getRoot(), animatedDrawable);
    }


    public DefaultFighterCardPresenter(View root) {
        super(root);
    }


    public static class DefaultFighterCardPresenterFactory implements Factory<FighterCardPresenter> {
        @Override
        public FighterCardPresenter createFor(View root) {
            return new DefaultFighterCardPresenter(root);
        }
    }
}