package com.nerpage.oca.fragments;

import android.view.View;

import androidx.fragment.app.Fragment;

public class PACFragment<C extends Controller<?,?>> extends Fragment {
    View root;

    C c;
}
