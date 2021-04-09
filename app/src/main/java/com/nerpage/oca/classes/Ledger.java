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

    private List<Row> rows;
    private List<Consumer<Row>> onRowAddedListeners;
    private boolean ledgerOpened = true;

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

    public List<Consumer<Row>> getOnRowAddedListeners() {
        return onRowAddedListeners;
    }

    private Ledger setOnRowAddedListeners(List<Consumer<Row>> onRowAddedListeners) {
        this.onRowAddedListeners = onRowAddedListeners;
        return this;
    }

    public boolean isLedgerOpened() {
        return ledgerOpened;
    }

    private Ledger setLedgerOpened(boolean ledgerOpened) {
        this.ledgerOpened = ledgerOpened;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    public Ledger addOnRowAddedListener(Consumer<Row> listener){
        if(this.isLedgerOpened())
            this.getOnRowAddedListeners().add(listener);
        return this;
    }

    protected Ledger addRow(Row newRow){
        if(this.isLedgerOpened()) {
            this.getRows().add(newRow);
            this.getOnRowAddedListeners().forEach(listener -> listener.accept(newRow));
        }
        return this;
    }

    public Ledger addRow(String string){
        return this.addRow(new StringRow(string));
    }

    public void open(){
        this.setLedgerOpened(true);
    }

    public void close(){
        this.setLedgerOpened(false);
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
