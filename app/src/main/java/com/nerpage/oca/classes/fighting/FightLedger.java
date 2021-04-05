package com.nerpage.oca.classes.fighting;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.Ledger;

public class FightLedger extends Ledger {
    //================================================================================
    // region //            Inner classes

    class SelectedActionRow extends Row {
        Action action;

        public Action getAction() {
            return action;
        }

        private SelectedActionRow setAction(Action action) {
            this.action = action;
            return this;
        }

        @Override
        public String toString(Context context) {
            return context.getResources().getString(R.string.ledger_entity_chose_action,
                    this.getAction().getSource().getName(context),
                    this.getAction().getName(context),
                    this.getAction().getTarget().getName(context));
        }

        @NonNull
        @Override
        public String toString() {
            return this.getAction().getId();
        }

        public SelectedActionRow(Action action) {
            this.action = action;
        }
    }

    class ActionRow extends Row {
        Action action;

        public Action getAction() {
            return action;
        }

        private ActionRow setAction(Action action) {
            this.action = action;
            return this;
        }

        @Override
        public String toString(Context context) {
            return context.getResources().getString(R.string.ledger_entity_used_action_on_entity,
                        this.getAction().getSource().getName(context),
                        this.getAction().getName(context),
                        this.getAction().getTarget().getName(context));
        }

        @NonNull
        @Override
        public String toString() {
            return this.getAction().getId();
        }

        public ActionRow(Action action) {
            this.action = action;
        }
    }

    class EnrollRow extends Row{
        Fighter entity;

        public Fighter getEntity() {
            return entity;
        }

        public EnrollRow setEntity(Fighter entity) {
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
    }

    class ConclusionRow extends Row{
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

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields



    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors



    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    public Ledger addExecutedActionRow(Action action) {
        return super.addRow(new ActionRow(action));
    }

    public Ledger addSelectedActionRow(Action action){
        return super.addRow(new SelectedActionRow(action));
    }

    public Ledger addEndOfFightRow(){
        return super.addRow(new ConclusionRow());
    }

    // endregion //         Methods
    //================================================================================
}
