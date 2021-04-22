package com.nerpage.oca.fragments.battleground_fragment;

import com.nerpage.oca.classes.fighting.ledger.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;
import com.nerpage.oca.layouts.BattlegroundLayoutHelper;

public class EffectsManager {
    private BattlegroundLayoutHelper layout;
    private Runnable onObserverReadyListener;



    private BattlegroundLayoutHelper getLayout() {
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

    private EffectsManager(BattlegroundLayoutHelper layout, Runnable onObserverReadyListener) {
        this.layout = layout;
        this.onObserverReadyListener = onObserverReadyListener;
    }
}
