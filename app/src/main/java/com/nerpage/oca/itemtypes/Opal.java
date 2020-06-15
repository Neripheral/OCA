package com.nerpage.oca.itemtypes;


import com.nerpage.oca.classes.Item;
import com.nerpage.oca.classes.Stackable;

public abstract class Opal extends Stackable {
    @Override
    public void initTags() {
        this.getTags().add(Tag.GEM);
    }

    @Override
    protected int getUnitWeight() {
        return 2090;
    }
}
