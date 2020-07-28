package com.nerpage.oca.classes;


import com.nerpage.oca.itemtypes.Container;

public class CarryingSpace extends Container {
    @Override
    public int getCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public double getWeightReduction() {
        return 0;
    }

    @Override
    public void initTags() {

    }

    @Override
    public int getContainerWeight() {
        return 0;
    }


}
