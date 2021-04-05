package com.nerpage.oca.classes.fighting;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.Ledger;

import java.util.ArrayList;

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
            //TODO: continue
            return "";
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
