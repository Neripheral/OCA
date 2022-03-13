package com.nerpage.oca.fragments;

import android.content.Context;
import android.view.View;

public abstract class Controller<M extends Model, P extends Presenter> {
    protected final M m;
    protected final P p;

    private Context context;

    protected Context getContext(){
        return context;
    }

    public void setRoot(View newRoot){
        context = newRoot.getContext();
        p.setRoot(newRoot);
    }

    Controller(M newM, P newP){
        m = newM;
        p = newP;
    }
}
