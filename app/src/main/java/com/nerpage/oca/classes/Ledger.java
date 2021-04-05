package com.nerpage.oca.classes;


import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Ledger {
    //================================================================================
    // region //            Inner classes

    public abstract class Row{
        @NonNull
        @Override
        public abstract String toString();

        // Return fancy, translated version
        public String toString(Context context){
            return this.toString();
        }
    }

    public class StringRow extends Row{
        private String data;

        public String getData() {
            return data;
        }

        public StringRow setData(String data) {
            this.data = data;
            return this;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getData();
        }

        public StringRow(String data) {
            this.data = data;
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
    //================================================================================
    // region //            Constructors



    // endregion //         Constructors
    //================================================================================
}
