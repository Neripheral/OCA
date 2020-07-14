package com.nerpage.oca.classes;

import android.content.Context;

public class CustomItem extends Item {
    //================================================================================
    // Attributes
    //================================================================================

    private String customId;
    private String name;

    //================================================================================
    // Accessors
    //================================================================================

    public String getCustomId(){
        return this.customId;
    }

    public CustomItem setCustomId(String customId){
        this.customId = customId;
        return this;
    }

    public String getName(){
        return this.name;
    }

    public CustomItem setName(String name){
        this.name = name;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public CustomItem(String id, String name){
        super();
        this.setCustomId(id);
        this.setName(name);
    }

    public CustomItem(String id){
        this(id, "missing_name");
    }

    public CustomItem(){
        this("missingId");
    }

    //================================================================================
    // Item overrides
    //================================================================================

    @Override
    public String getId(){
        return this.getCustomId();
    }

    @Override
    public String getName(Context context){
        return this.getName();
    }

    @Override
    public void initTags() {
        this.getTags().add(Tag.CUSTOM);
    }

    @Override
    public int getWeight() {
        return 1000;
    }
}
