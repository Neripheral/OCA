package com.nerpage.oca.classes;

import android.content.Context;

import androidx.annotation.NonNull;

public abstract class Event {
    @NonNull
    @Override
    public abstract String toString();

    // Return fancy, translated version
    public String toString(Context context){
        return this.toString();
    }
}
