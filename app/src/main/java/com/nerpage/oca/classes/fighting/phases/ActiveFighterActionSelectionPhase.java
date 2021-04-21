package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.ledger.events.EntitySelectedActionEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;

public class ActiveFighterActionSelectionPhase extends FighterTurnFightPhase {
    //================================================================================
    // region //            Fields

    private Action selectedAction;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private Action getSelectedAction() {
        return selectedAction;
    }

    private ActiveFighterActionSelectionPhase setSelectedAction(Action selectedAction) {
        this.selectedAction = selectedAction;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    //TODO: there shouldn't be a fighter argument needed - investigate
    private void onActionSelected(Fighter fighter, Action action){
        getActiveFighter().setPendingAction(action);
        setSelectedAction(action);
        if(action != null)
            getActiveFighter().addToStopwatch(action.getTimeSpan());
    }

    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterCalculationPhase(getFight());
        //TODO: branch off to end the fight
    }

    @Override
    public void execute() {
        getActiveFighter().promptAction(getFight().getFightersWithout(getActiveFighter()), this::onActionSelected);
    }

    @Override
    public FightEvent getEventAfterPhase() {
        return new EntitySelectedActionEvent(getSelectedAction(), getActiveFighter().getEntity());
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public ActiveFighterActionSelectionPhase(Fight fight, Fighter fighter) {
        super(fight, fighter);
    }

    // endregion //         Constructors
    //================================================================================
}
