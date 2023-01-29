package com.nerpage.oca.fragments;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nerpage.oca.pac.Presenter;

public abstract class PACFragment<M extends Model, P extends Presenter> extends Fragment {
    public interface CallbackToParent<C extends CallbackToParent.Callback>{
        interface Callback{}

        void registerCallback(C callback);
    }

    View root;

    protected M m;
    protected P p;

    public abstract void initPAC();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initPAC();
    }
}
