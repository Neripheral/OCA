package com.nerpage.oca.classes.fighting.events;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.fighting.actions.Action;

public final class EntitySelectedActionEvent extends ActionEvent {
    private final Entity entity;

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String toString(Context context) {
        return context.getResources().getString(R.string.ledger_entity_chose_action,
                (this.getAction().getSource() == null ? "?" : this.getAction().getSource().getName(context)),
                this.getAction().getName(context),
                (this.getAction().getTarget() == null ? "?" : this.getAction().getTarget().getName(context)));
    }

    @NonNull
    @Override
    public String toString() {
        return this.getAction().getId();
    }

    public EntitySelectedActionEvent(Action action, Entity entity) {
        super(action);
        this.entity = entity;
    }
}
