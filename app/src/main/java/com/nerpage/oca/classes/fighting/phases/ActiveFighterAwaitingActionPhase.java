package com.nerpage.oca.classes.fighting.phases;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;

public class ActiveFighterAwaitingActionPhase extends FighterTurnFightPhase {
    public static class AwaitingFighterActionEvent extends FightEvent {
        private final Fighter fighter;

        private Fighter getFighter() {
            return fighter;
        }

        @NonNull
        @Override
        public String toString() {
            return "Awaiting input from " + getFighter().getEntity().getId();
        }

        @Override
        public String toString(Context context) {
            return context.getString(R.string.awaiting_input_from_blank, getFighter().getEntity().getName(context));
        }

        private AwaitingFighterActionEvent(Fighter fighter) {
            this.fighter = fighter;
        }
    }

    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterActionSelectionPhase(getFight(), getActiveFighter());
    }

    @Override
    public void execute() {

    }

    @Override
    public FightEvent getEventAfterPhase() {
        return new AwaitingFighterActionEvent(getActiveFighter());
    }

    public ActiveFighterAwaitingActionPhase(Fight fight, Fighter activeFighter) {
        super(fight, activeFighter);
    }
}
