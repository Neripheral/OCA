package com.nerpage.oca.classes.fighting;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.Ledger;

public class FightLedger extends Ledger {
    //================================================================================
    // region //            Inner classes

    class ActionRow extends Ledger.Row {
        //================================================================================
        // region //            Fields

        Action action;

        // endregion //         Fields
        //================================================================================
        //================================================================================
        // region //            Accessors

        public Action getAction() {
            return action;
        }

        private ActionRow setAction(Action action) {
            this.action = action;
            return this;
        }

        // endregion //         Accessors
        //================================================================================

        @NonNull
        @Override
        public String toString() {
            return this.getAction().getId();
        }

        @Override
        public String toString(Context context) {
            return this.getAction().getName(context);
        }

        public ActionRow(Action action) {
            this.action = action;
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



    // endregion //         Methods
    //================================================================================
}
