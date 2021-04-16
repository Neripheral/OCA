package com.nerpage.oca.classes.fighting.ledger.events;

import android.content.Context;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.fighting.actions.Action;

public final class EntityPerformedActionEvent extends ActionEvent{
    @Override
    public String toString(Context context) {
        return context.getResources().getString(R.string.ledger_entity_used_action_on_entity,
                (this.getAction().getSource() == null ? "?" : this.getAction().getSource().getName(context)),
                this.getAction().getName(context),
                (this.getAction().getTarget() == null ? "?" : this.getAction().getTarget().getName(context)));
    }

    public EntityPerformedActionEvent(Action action) {
        super(action);
    }
}
