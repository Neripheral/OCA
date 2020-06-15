package com.nerpage.oca.itemsdb;

import android.content.Context;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.PreciseStackable;

public class Currency extends PreciseStackable {
    public Currency(){
        this.setPrecision(2);
    }

    @Override
    public int getNameResId(Context context) {
        return R.string.currency_name_main;
    }

    @Override
    public String getName(Context context) {
        return "(" + context.getResources().getString(R.string.currency_symbol) + ") " + context.getResources().getString(this.getNameResId(context));
    }

    @Override
    public void initTags() {
        this.getTags().add(Tag.VALUABLE);
    }

    @Override
    protected int getUnitWeight() {
        return 10;
    }
}
