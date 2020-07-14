package com.nerpage.oca.itemtypes;

import android.app.AlertDialog;

import com.nerpage.oca.classes.Item;
import com.nerpage.oca.classes.ItemDatabase;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.interfaces.Inventory;
import com.nerpage.oca.itemsdb.BlackOpal;
import com.nerpage.oca.itemsdb.WhiteOpal;

public abstract class Container extends Item implements Inventory {
    private ItemStorage inventory;
    private boolean open = false;

    @Override
    public ItemStorage getInventory() {
        return this.inventory;
    }

    public void setInventory(ItemStorage inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public Item copy() {
        Container copy = ((Container)super.copy());
        copy.setInventory(this.getInventory());
        copy.setOpen(this.isOpen());
        return copy;
    }

    @Override
    public AlertDialog initByDialog(AlertDialog.Builder builder, Runnable onSuccess) {
        BlackOpal opal = (BlackOpal) ItemDatabase.getItemById("blackOpal");
        WhiteOpal opal2 = (WhiteOpal) ItemDatabase.getItemById("whiteOpal");
        opal.init(2);
        opal2.init(2);
        this.getInventory().add(opal);
        this.getInventory().add(opal2);
        return super.initByDialog(builder, onSuccess);
    }

    public abstract int getContainerWeight();

    @Override
    public int getWeight() {
        return this.getInventory().getContentWeight() + this.getContainerWeight();
    }

    public Container(){
        super();
        this.inventory = new ItemStorage();
    }
}
