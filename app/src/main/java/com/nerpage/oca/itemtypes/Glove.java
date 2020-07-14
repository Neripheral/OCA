package com.nerpage.oca.itemtypes;

import com.nerpage.oca.classes.Item;
import com.nerpage.oca.interfaces.Equipable;

public abstract class Glove extends Item implements Equipable {
    @Override
    public void initTags() {
        this.getTags().add(Tag.GLOVE);
    }
}
