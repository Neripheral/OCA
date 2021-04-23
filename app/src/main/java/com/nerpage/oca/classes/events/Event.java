package com.nerpage.oca.classes.events;

import android.content.Context;

import androidx.annotation.NonNull;

public abstract class Event {
    @Override
    public String toString(){
        return "Event occured: " + this.getClass().toString();
    }

    // Return fancy, translated version
    public String toString(Context context){
        return this.toString();
    }
}
