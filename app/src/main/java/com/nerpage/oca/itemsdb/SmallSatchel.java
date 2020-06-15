package com.nerpage.oca.itemsdb;

import com.nerpage.oca.itemtypes.Backpack;

public class SmallSatchel extends Backpack {
    @Override
    public int getCapacity() {
        return 15000;
    }

    @Override
    public int getContainerWeight() {
        return 700;
    }

    @Override
    public double getWeightReduction() {
        return 0.7;
    }
}
