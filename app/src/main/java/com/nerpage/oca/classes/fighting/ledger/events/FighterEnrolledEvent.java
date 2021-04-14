package com.nerpage.oca.classes.fighting.ledger.events;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Event;
import com.nerpage.oca.classes.fighting.Fighter;

public final class FighterEnrolledEvent extends Event {
    Fighter entity;

    public Fighter getEntity() {
        return entity;
    }

    private FighterEnrolledEvent setEntity(Fighter entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public String toString(Context context) {
        return context.getResources().getString(R.string.ledger_entity_enrolled,
                this.getEntity().getEntity().getName(context));
    }

    @NonNull
    @Override
    public String toString() {
        return this.getEntity().getEntity().getId();
    }

    public FighterEnrolledEvent(Fighter entity) {
        this.entity = entity;
    }
}
