package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;

import java.util.ArrayList;
import java.util.List;

class ObserversManager {
    //================================================================================
    // region //            Inner classes

    public interface FightListener{
        void notifyAbout(FightEvent event);
    }

    class FightObserver{
        public FightListener listener;
        boolean isReady;

        public FightObserver(FightListener listener, boolean isReady) {
            this.listener = listener;
            this.isReady = isReady;
        }
    }

    public interface EveryoneIsReadyListener {
        void notifyEveryoneIsReady();
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private final List<FightObserver> observers = new ArrayList<>();
    private final EveryoneIsReadyListener everyoneIsReadyListener;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public List<FightObserver> getObservers() {
        return observers;
    }

    public EveryoneIsReadyListener getEveryoneIsReadyListener() {
        return everyoneIsReadyListener;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private void onReadyNotified(FightObserver observer){
        observer.isReady = true;
        if(isEveryoneReady())
            getEveryoneIsReadyListener().notifyEveryoneIsReady();
    }

    private boolean isEveryoneReady(){
        for(FightObserver observer : getObservers()){
            if(!observer.isReady)
                return false;
        }
        return true;
    }

    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public void notifyObserversAbout(FightEvent event){
        for(FightObserver observer: getObservers()){
            observer.isReady = false;
            observer.listener.notifyAbout(event);
        }
    }

    public Runnable addObserver(FightListener listener){
        FightObserver newObserver = new FightObserver(listener, true);
        getObservers().add(newObserver);
        return () -> onReadyNotified(newObserver);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public ObserversManager(EveryoneIsReadyListener listener) {
        this.everyoneIsReadyListener = listener;
    }

    // endregion //         Constructors
    //================================================================================
}
