package com.nerpage.oca.classes;

import android.content.Context;

import java.util.List;

public class Body {
    //================================================================================
    // Fields
    //================================================================================

    private int nameId;

    //================================================================================
    // Accessors
    //================================================================================

    public int getNameId() {
        return nameId;
    }

    public Body setNameId(int nameId) {
        this.nameId = nameId;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Body(){}

    //================================================================================
    // Methods
    //================================================================================

    public String getName(Context context){
        return context.getResources().getString(this.getNameId());
    }

}
