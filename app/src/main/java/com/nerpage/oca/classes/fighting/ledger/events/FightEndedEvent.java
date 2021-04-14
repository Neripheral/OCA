package com.nerpage.oca.classes.fighting.ledger.events;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Event;

public final class FightEndedEvent extends Event {
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
