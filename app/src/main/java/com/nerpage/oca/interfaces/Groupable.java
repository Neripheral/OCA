package com.nerpage.oca.interfaces;

import com.nerpage.oca.items.Item;

public interface Groupable {
    boolean compatibleWith(Item item);
    default boolean canAdd(Item item){return true;}
    boolean add(Item item);
    boolean canRemove(Item item);
    boolean remove(Item item);
}
