package com.nerpage.oca.classes;

import android.content.Context;

public interface Identifiable {
    default String getId(){
        String className = this.getClass().getSimpleName();
        return ( className.substring(0, 1).toLowerCase() + className.substring(1) );
    }

    String getPrefix();

    default int getNameResId(Context context){
        return context.getResources().getIdentifier(this.getPrefix() + "_" + getId(), "string", context.getPackageName());
    }

    default String getName(Context context){
        int id = getNameResId(context);
        if(id == 0)
            return this.getId();
        return context.getResources().getString(id);
    }
}
