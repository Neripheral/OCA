
package com.nerpage.oca.classes;

import android.content.Context;

public abstract class Item {
    public String getId(){
        String className = this.getClass().getSimpleName();
        return ( className.substring(0, 1).toLowerCase() + className.substring(1) );
    }

    public int getNameResId(Context context){
        return context.getResources().getIdentifier("item_" + getId(), "string", context.getPackageName());
    }


    public String getName(Context context){
        int id = getNameResId(context);
        if(id == 0)
            return this.getId();
        return context.getResources().getString(id);
    }

    public boolean equals(Item item){
        return this.getId() == item.getId();
    }

    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        if(o instanceof Item)
            this.equals((Item)o);
        return false;
    }

    public Item(){}
}

