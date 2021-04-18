package com.nerpage.oca.classes.fighting.ledger.events;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.fighting.actions.Action;

abstract class ActionEvent extends FightEvent {
    private final Action action;

    public Action getAction() {
        return action;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getAction().getId();
    }

    public ActionEvent(Action action) {
        this.action = action;
    }
}
