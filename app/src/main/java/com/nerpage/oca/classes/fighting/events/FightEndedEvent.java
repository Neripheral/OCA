package com.nerpage.oca.classes.fighting.events;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;

public final class FightEndedEvent extends FightEvent {
    @Override
    public String toString(Context context) {
        return context.getResources().getString(R.string.ledger_fight_finished);
    }

    @NonNull
    @Override
    public String toString() {
        return "endOfFight";
    }
}
