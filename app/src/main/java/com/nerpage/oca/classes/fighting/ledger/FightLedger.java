package com.nerpage.oca.classes.fighting.ledger;

import com.nerpage.oca.classes.Ledger;
import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.ledger.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.fighting.ledger.events.EntitySelectedActionEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FightEndedEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FighterEnrolledEvent;

public class FightLedger extends Ledger {
    //================================================================================
    // region //            Methods

    public FightLedger addPerformedActionEvent(Action action) {
        return (FightLedger)super.addEvent(new EntityPerformedActionEvent(action));
    }

    public FightLedger addSelectedActionEvent(Action action){
        return (FightLedger)super.addEvent(new EntitySelectedActionEvent(action));
    }

    public FightLedger addFighterEnrolledEvent(Fighter fighter){
        return (FightLedger)super.addEvent(new FighterEnrolledEvent(fighter));

    }

    public FightLedger addFightEndedEvent(){
        return (FightLedger)super.addEvent(new FightEndedEvent());
    }

    // endregion //         Methods
    //================================================================================
}
