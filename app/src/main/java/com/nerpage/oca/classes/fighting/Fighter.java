package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

public class Fighter {
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public Fighter setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public Fighter(Entity entity){
        this.setEntity(entity);
    }
}
