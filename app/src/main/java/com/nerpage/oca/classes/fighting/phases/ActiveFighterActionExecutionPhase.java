package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.fighting.events.FightEvent;

public class ActiveFighterActionExecutionPhase extends FighterTurnFightPhase {
    //================================================================================
    // region //            Fields

    private Action performedAction = null;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private Action getPerformedAction() {
        return performedAction;
    }

    private ActiveFighterActionExecutionPhase setPerformedAction(Action performedAction) {
        this.performedAction = performedAction;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterAwaitingActionPhase(getFight(), getActiveFighter());
    }

    @Override
    public void execute() {
        Action pendingAction = getActiveFighter().getPendingAction();
        if(pendingAction != null){
            setPerformedAction(pendingAction);
            //TODO: clashing Actions
            pendingAction.getTarget().applyStatus(pendingAction.getAppliedStatus());
            //TODO: Phase ApplyStatus
        }
        getActiveFighter().setPendingAction(null);
    }

    @Override
    public FightEvent getEventAfterPhase() {
        if(getPerformedAction() == null)
            return null;
        else
            return new EntityPerformedActionEvent(getPerformedAction(), getActiveFighter().getEntity());
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public ActiveFighterActionExecutionPhase(Fight fight, Fighter activeFighter) {
        super(fight, activeFighter);
    }

    // endregion //         Constructors
    //================================================================================

}
