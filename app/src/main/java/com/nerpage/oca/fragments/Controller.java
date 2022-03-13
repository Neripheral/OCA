package com.nerpage.oca.fragments;

import android.content.Context;

public abstract class Controller<M extends Model, P extends Presenter> {
    protected final M m;
    protected final P p;

    private final Context context;

    protected Context getContext(){
        return context;
    }

    Controller(M newM, P newP, Context newContext){
        m = newM;
        p = newP;
        context = newContext;
    }
}
