package com.nerpage.oca.classes.fighting.events;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Entity;

public final class EntityEnrolledEvent extends FightEvent {
    Entity entity;

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String toString(Context context) {
        return context.getResources().getString(R.string.ledger_entity_enrolled,
                this.getEntity().getName(context));
    }

    @NonNull
    @Override
    public String toString() {
        return this.getEntity().getId();
    }

    public EntityEnrolledEvent(Entity entity) {
        this.entity = entity;
    }
}
