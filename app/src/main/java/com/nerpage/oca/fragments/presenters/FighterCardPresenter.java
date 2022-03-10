package com.nerpage.oca.fragments.presenters;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.Presenter;

public class FighterCardPresenter extends Presenter {
    //================================================================================
    // region //            POI

    public enum POI implements Presenter.POI {
        CONTAINER(R.id.fighter_root),
        CARD(R.id.fighter_container),
        TITLE(R.id.fighter_title),
        CURRENT_BLOOD(R.id.fighter_currentBlood),
        MAX_BLOOD(R.id.fighter_maxBlood),
        EFFECT(R.id.fighter_effect_attack);

        int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id){
            this.id = id;
        }
    }

    // endregion //         POI
    //================================================================================
    //================================================================================
    // region //            Interface

    public void updateTitle(String newTitle){
        updateText(POI.TITLE, newTitle);
    }

    public void updateCurrentBlood(String newCurrentBlood){
        updateText(POI.CURRENT_BLOOD, newCurrentBlood);
    }

    public void updateMaxBlood(String newMaxBlood){
        updateText(POI.MAX_BLOOD, newMaxBlood);
    }

    public void playEffectOnAvatar(int resId, int duration, float scale, Runnable after){
        playEffect(POI.EFFECT, resId, duration, scale, after);
    }

    // endregion //         Interface
    //================================================================================
}
