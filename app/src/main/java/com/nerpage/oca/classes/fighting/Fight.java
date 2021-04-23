package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.events.Event;
import com.nerpage.oca.classes.events.EventController;
import com.nerpage.oca.classes.events.FlowFreezer;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.events.FightEvent;
import com.nerpage.oca.classes.fighting.phases.FightPhase;
import com.nerpage.oca.classes.fighting.phases.StartFightPhase;

import java.util.ArrayList;
import java.util.List;

public class Fight implements EventController.EventReceiver, EventController.EventEmitter {
    //================================================================================
    // region //            Inner classes

    public interface FightListener{
        void notifyAbout(FightEvent event);
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private final List<Fighter> fighters = new ArrayList<>();
    private FightPhase currentPhase;
    private final List<Object> flowFreezers = new ArrayList<>();
    private EventController.EventListener eventListener;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private FightPhase getCurrentPhase() {
        return currentPhase;
    }

    private Fight setCurrentPhase(FightPhase currentPhase) {
        this.currentPhase = currentPhase;
        return this;
    }

    private List<Object> getFlowFreezers() {
        return flowFreezers;
    }

    private EventController.EventListener getEventListener() {
        return eventListener;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private void proceed(FightPhase currentPhase){
        setCurrentPhase(currentPhase);
        getCurrentPhase().execute();
        if(getCurrentPhase().getEventAfterPhase() != null){
            getEventListener().emitEvent(getCurrentPhase().getEventAfterPhase());
        }
        tryToProceed();
    }

    private void proceed(){
        proceed(getCurrentPhase().getNextPhase());
    }

    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public List<Fighter> getFighters() {
        return fighters;
    }

    public List<Fighter> getFightersWithout(Fighter fighter){
        List<Fighter> toReturn = new ArrayList<>(this.getFighters());
        toReturn.remove(fighter);
        return toReturn;
    }

    public Fighter enrollFighter(Entity entity, FightingBehavior behavior, int handicapTime){
        Fighter newFighter = new Fighter(entity, behavior);
        newFighter.setStopwatchTime(handicapTime);
        this.getFighters().add(newFighter);
        return newFighter;
    }

    public void tryToProceed() {
        if(getFlowFreezers().isEmpty())
            proceed();
    }

    public void start(){
        proceed(new StartFightPhase(this));
    }



    @Override
    public void onEventReceived(Event event) {
        if(event.getClass() == FlowFreezer.FreezeFlow.class){
            FlowFreezer.FreezeFlow freezeFlowEvent = (FlowFreezer.FreezeFlow) event;
            getFlowFreezers().add(freezeFlowEvent.getIdentifier());
        } else if(event.getClass() == FlowFreezer.ResumeFlow.class){
            FlowFreezer.ResumeFlow resumeFlowEvent = (FlowFreezer.ResumeFlow) event;
            getFlowFreezers().remove(resumeFlowEvent.getIdentifier());
            tryToProceed();
        }
    }

    @Override
    public void setEventListener(EventController.EventListener eventListener) {
        this.eventListener = eventListener;
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Fight(){
    }

    // endregion //         Constructors
    //================================================================================

}
