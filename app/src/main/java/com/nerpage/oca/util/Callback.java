package com.nerpage.oca.util;

public interface Callback {
    void call();

    Callback EMPTY = () -> {};
}
