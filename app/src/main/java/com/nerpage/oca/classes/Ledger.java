package com.nerpage.oca.classes;


import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

    List<Row> rows;
    List<Consumer<String>> onRowAddedListeners;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public List<Row> getRows() {
        return rows;
    }

    private Ledger setRows(List<Row> rows) {
        this.rows = rows;
        return this;
    }

    public List<Consumer<String>> getOnRowAddedListeners() {
        return onRowAddedListeners;
    }

    private Ledger setOnRowAddedListeners(List<Consumer<String>> onRowAddedListeners) {
        this.onRowAddedListeners = onRowAddedListeners;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    public Ledger addOnRowAddedListener(Consumer<String> listener){
        this.getOnRowAddedListeners().add(listener);
        return this;
    }

    protected Ledger addRow(Row newRow){
        this.getRows().add(newRow);
        return this;
    }

    public Ledger addRow(String string){
        return this.addRow(new StringRow(string));
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Ledger(List<Row> rows) {
        this.rows = rows;
        this.onRowAddedListeners = new ArrayList<>();
    }

    public Ledger(){
        this(new ArrayList<>());
    }

    // endregion //         Constructors
    //================================================================================
}
