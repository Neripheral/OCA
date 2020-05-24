
package com.nerpage.oca.items;

import android.content.Context;

public abstract class Item {
    public abstract String getId();

    public int getNameResId(){
        return 0;
    }


    public String getName(Context context){
        if(getNameResId() == 0)
            return "";
        return context.getResources().getString(this.getNameResId());
    }

    public boolean equals(Item item){
        if(this.getId() == item.getId())
            return true;
        return false;
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

