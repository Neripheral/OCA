package com.nerpage.oca.classes.fighting.ledger.events;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.fighting.Fighter;

public final class FighterTurnEndedEvent extends FightEvent{
    private final Fighter fighter;

    public Fighter getFighter() {
        return fighter;
    }

    @NonNull
    @Override
    public String toString() {
        return getFighter().getEntity().getId() + " ended their turn.";
    }

    @Override
    public String toString(Context context) {
        return context.getString(R.string.fighter_turn_ended, getFighter().getEntity().getName(context));
    }

    public FighterTurnEndedEvent(Fighter fighter) {
        this.fighter = fighter;
    }
}
