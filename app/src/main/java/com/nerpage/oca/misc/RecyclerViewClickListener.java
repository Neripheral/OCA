package com.nerpage.oca.misc;

import android.view.MotionEvent;
import android.view.View;

import java.util.EventListener;

public interface RecyclerViewClickListener{
    void onClick(View view, int position);
    default boolean onTouch(View view, int position, MotionEvent event){
        return false;
    };
}
