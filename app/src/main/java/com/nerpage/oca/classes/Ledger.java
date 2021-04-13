package com.nerpage.oca.classes;


import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Ledger {
    //================================================================================
    // region //            Inner classes

    public static abstract class Event {
        @NonNull
        @Override
        public abstract String toString();

        // Return fancy, translated version
        public String toString(Context context){
            return this.toString();
        }
    }

    public class StringEvent extends Event {
        private String data;

        public String getData() {
            return data;
        }

        public StringEvent setData(String data) {
            this.data = data;
            return this;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getData();
        }

        public StringEvent(String data) {
            this.data = data;
        }
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private List<Event> events;
    private List<Consumer<Event>> onEventAddedListeners;
    private boolean ledgerOpened = true;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public List<Event> getEvents() {
        return events;
    }

    private Ledger setEvents(List<Event> events) {
        this.events = events;
        return this;
    }

    public List<Consumer<Event>> getOnEventAddedListeners() {
        return onEventAddedListeners;
    }

    private Ledger setOnEventAddedListeners(List<Consumer<Event>> onEventAddedListeners) {
        this.onEventAddedListeners = onEventAddedListeners;
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

    public Ledger addOnEventAddedListener(Consumer<Event> listener){
        if(this.isLedgerOpened())
            this.getOnEventAddedListeners().add(listener);
        return this;
    }

    protected Ledger addEvent(Event newEvent){
        if(this.isLedgerOpened()) {
            this.getEvents().add(newEvent);
            for(Consumer<Event> listener : this.getOnEventAddedListeners())
                listener.accept(newEvent);
        }
        return this;
    }

    public Ledger addEvent(String string){
        return this.addEvent(new StringEvent(string));
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

    public Ledger(List<Event> events) {
        this.events = events;
        this.onEventAddedListeners = new ArrayList<>();
    }

    public Ledger(){
        this(new ArrayList<>());
    }

    // endregion //         Constructors
    //================================================================================
}
