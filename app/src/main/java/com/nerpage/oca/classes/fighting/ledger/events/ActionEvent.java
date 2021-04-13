package com.nerpage.oca.classes.fighting.ledger.events;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.Ledger;
import com.nerpage.oca.classes.fighting.Action;

public abstract class ActionEvent extends Ledger.Event {
    Action action;

    public Action getAction() {
        return action;
    }

    private ActionEvent setAction(Action action) {
        this.action = action;
        return this;
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
