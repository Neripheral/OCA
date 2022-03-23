package com.nerpage.oca.fragments;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class PACFragment<M extends Model, P extends Presenter> extends Fragment {
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
