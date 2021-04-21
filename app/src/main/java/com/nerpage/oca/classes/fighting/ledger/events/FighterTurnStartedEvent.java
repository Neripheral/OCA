package com.nerpage.oca.classes.fighting.ledger.events;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.fighting.Fighter;

public final class FighterTurnStartedEvent extends FightEvent{
    private final Fighter fighter;

    public Fighter getFighter() {
        return fighter;
    }

    @NonNull
    @Override
    public String toString() {
        return getFighter().getEntity().getId() + " started their turn.";
    }

    @Override
    public String toString(Context context) {
        return context.getString(R.string.fighter_turn_started, getFighter().getEntity().getName(context));
    }

    public FighterTurnStartedEvent(Fighter fighter){
        this.fighter = fighter;
    }
}
