package com.nerpage.oca.fragments.battleground_fragment;

import com.nerpage.oca.classes.fighting.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.fighting.events.FightEvent;
import com.nerpage.oca.layouts.BattlegroundLayout;

public class EffectsManager {
    private BattlegroundLayout layout;
    private Runnable onObserverReadyListener;



    private BattlegroundLayout getLayout() {
        return layout;
    }

    private Runnable getOnObserverReadyListener() {
        return onObserverReadyListener;
    }



    public void onEventRegistered(FightEvent event){
        if(event.getClass() == EntityPerformedActionEvent.class){

        }
        getOnObserverReadyListener().run();
    }

    private EffectsManager(BattlegroundLayout layout, Runnable onObserverReadyListener) {
        this.layout = layout;
        this.onObserverReadyListener = onObserverReadyListener;
    }
}
