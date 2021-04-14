package com.nerpage.oca.classes;

import androidx.annotation.NonNull;

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
