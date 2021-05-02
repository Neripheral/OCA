package com.nerpage.oca.layouts.models;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.nerpage.oca.classes.Item;
import com.nerpage.oca.layouts.ItemLayout;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.interfaces.Equipable;
import com.nerpage.oca.interfaces.Inventory;

import java.lang.ref.WeakReference;

public class ItemModel extends ViewModel implements Comparable<ItemModel> {
    //================================================================================
    // Fields
    //================================================================================

    private WeakReference<Item> itemRef;
    private String id = "";
    private String title = "";
    private String quantity = "";
    private int mainTagImageId = 0;
    private ItemStorage boundItemStorage = null;
    private boolean nestedInvVisible = false;
    private double fullness = 0.0;
    private String weight = "";
    private String capacity = "";
    private boolean hiddenTransferToHandsButton = false;

    //================================================================================
    // Accessors
    //================================================================================

    public String getId(){
        return id;
    }
    public ItemModel setId(String id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }
    public ItemModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }
    public ItemModel setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getMainTagImageId() {
        return mainTagImageId;
    }
    public ItemModel setMainTagImageId(int mainTagImageId) {
        this.mainTagImageId = mainTagImageId;
        return this;
    }

    public ItemStorage getBoundItemStorage() {
        return this.boundItemStorage;
    }

    public ItemModel setBoundItemStorage(ItemStorage boundItemStorage) {
        this.boundItemStorage = boundItemStorage;
        return this;
    }

    public boolean isNestedInvVisible() {
        return nestedInvVisible;
    }
    public ItemModel setNestedInvVisible(boolean nestedInvVisible) {
        this.nestedInvVisible = nestedInvVisible;
        return this;
    }

    public double getFullness() {
        return fullness;
    }
    public ItemModel setFullness(double fullness) {
        this.fullness = fullness;
        return this;
    }

    public WeakReference<Item> getItemRef(){
        return this.itemRef;
    }

    public String getWeight() {
        return weight;
    }
    public ItemModel setWeight(String weight) {
        this.weight = weight;
        return this;
    }

    public String getCapacity() {
        return capacity;
    }
    public ItemModel setCapacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    public boolean isHiddenTransferToHandsButton() {
        return hiddenTransferToHandsButton;
    }
    public ItemModel setHiddenTransferToHandsButton(boolean hiddenTransferToHandsButton) {
        this.hiddenTransferToHandsButton = hiddenTransferToHandsButton;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public ItemModel(Item item, Context context){
        this.itemRef = new WeakReference<>(item);
        this.setId(item.getId());
        this.setTitle(item.getName(context));
        this.setMainTagImageId(item.getMainTag().getImageId());
        if(item instanceof Item.Groupable){
            this.setQuantity(((Item.Groupable)item).getShownQuantity());
        }
        if(item instanceof Inventory){
            this.setFullness(countFullnessFor((Inventory)item));
            this.setWeight(((Inventory) item).getContentWeightToDisplay());
            this.setCapacity(((Inventory) item).getCapacityToDisplay());
            this.setBoundItemStorage(((Inventory) item).getInventory());
            this.setNestedInvVisible(((Inventory) item).isOpen());
        }
    }

    //================================================================================
    // Methods
    //================================================================================

    public static double countFullnessFor(Inventory inv){
        return (double)inv.getInventory().getContentWeight()/inv.getCapacity();
    }

    public String getFullnessString(){
        return String.valueOf((int)(getFullness()*100)) + "%";
    }

    public ItemLayout initLayoutHelperFor(View v, View.OnClickListener listener){
        ItemLayout lh = new ItemLayout(this, v, listener);
        return lh;
    }

    public boolean isEquipable(){
        return (this.getItemRef().get() instanceof Equipable);
    }

    public void refreshData(){
        if(getItemRef().get() instanceof Inventory){
            this.setFullness(countFullnessFor((Inventory)getItemRef().get()));
            this.setNestedInvVisible(((Inventory) getItemRef().get()).isOpen());
            this.setWeight(((Inventory) getItemRef().get()).getContentWeightToDisplay());
        }
    }

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public int compareTo(ItemModel other){
        String myStr = this.getTitle();
        String otherStr = other.getTitle();
        return myStr.compareTo(otherStr);
    }
}
