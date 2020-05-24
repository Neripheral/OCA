package com.nerpage.oca.interfaces;

import com.nerpage.oca.classes.ItemStorage;

public interface Inventory {
    ItemStorage getInventory();
    void setInventory(ItemStorage itemStorage);
}
