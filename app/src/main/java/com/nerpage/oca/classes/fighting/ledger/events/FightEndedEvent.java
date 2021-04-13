package com.nerpage.oca.classes.fighting.ledger.events;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Ledger;

public final class FightEndedEvent extends Ledger.Event {
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
