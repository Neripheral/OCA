package com.nerpage.oca.classes.fighting;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.Entity;

abstract public class Status implements Cloneable{
    abstract public void onApplication(Entity entity);

    @NonNull
    @Override
    public Status clone() {
        try {
            return (Status)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
