package com.nerpage.oca.pac.presenters;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.Presenter;
import com.nerpage.oca.util.AnimatedDrawable;

public interface FighterCardPresenter extends Presenter {
    static int getDescribedLayoutId() {
        return R.layout.battleground_fightercard;
    }

    void updateTitle(String newTitle);

    void updateCurrentBlood(String newCurrentBlood);

    void updateMaxBlood(String newMaxBlood);

    void playEffectOnAvatar(AnimatedDrawable animatedDrawable);


    enum POI implements PointOfInterest {
        CONTAINER(R.id.fighter_root),
        CARD(R.id.fighter_container),
        TITLE(R.id.fighter_title),
        CURRENT_BLOOD(R.id.fighter_currentBlood),
        MAX_BLOOD(R.id.fighter_maxBlood),
        EFFECT(R.id.fighter_effect_attack);

        private final int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id){
            this.id = id;
        }
    }
}