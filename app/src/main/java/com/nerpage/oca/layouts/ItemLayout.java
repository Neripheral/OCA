package com.nerpage.oca.layouts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nerpage.oca.R;
import com.nerpage.oca.layouts.models.ItemModel;

public class ItemLayout extends Layout<ItemModel> {
    //================================================================================
    // POI Override
    //================================================================================

    enum ItemPOI implements Layout.POI{
        SYNOPSIS(R.id.item_synopsis),
            INVENTORYMENU(R.id.item_inventory_menu),
                FULLNESS(R.id.item_inventory_fullness),
                COLLAPSEBTN(R.id.item_inventory_collapsebtn),
                EXPANDBTN(R.id.item_inventory_expandbtn),
            QUANTITYCONTAINER(R.id.item_quantity_container),
                QUANTITY(R.id.item_quantity),
            TITLE(R.id.item_title),
            MAINTAGIMG(R.id.item_maintag),
            EQUIPBTN(R.id.item_equipbtn),
            HOLDINGSPACE(R.id.item_holdingspace),
                MOVETOHOLDINGSPACE(R.id.item_holdingspace_givebtn),
                MOVEFROMHOLDINGSPACE(R.id.item_holdingspace_takebtn),
            REMOVE(R.id.item_removebtn),
        INVENTORY(R.id.item_inventory),
            INVENTORYWEIGHT(R.id.item_inventory_weight),
            INVENTORYCAPACITY(R.id.item_inventory_capacity),
            INVENTORYFRAGMENT(R.id.item_inventory_fragment);

        int id;

        ItemPOI(int id){
            this.id = id;
        }

        @Override
        public int getId() {
            return this.id;
        }
    }

    //================================================================================
    // Fields
    //================================================================================

    private View.OnClickListener listener;

    //================================================================================
    // Accessors
    //================================================================================

    public View.OnClickListener getListener() {
        return listener;
    }

    public ItemLayout setListener(View.OnClickListener listener){
        this.listener = listener;
        return this;
    }

    //================================================================================
    // Compound view manipulation
    //================================================================================


    @Override
    public void updateViewData() {
        //TODO: implement this... and refactor everything
    }

    private void hideEverything(){
        for(ItemPOI view : ItemPOI.values()){
            hide(view);
        }
    }

    private void setupInitState(){
        this.hideEverything();
        this.show(ItemPOI.SYNOPSIS);
        this.show(ItemPOI.TITLE);
        this.show(ItemPOI.MAINTAGIMG);

        ((TextView)this.getView(ItemPOI.TITLE)).setText(getModel().getTitle());
        ((ImageView)this.getView(ItemPOI.MAINTAGIMG)).setImageResource(getModel().getMainTagImageId());
    }

    private void prepareGroupableElements(){
        // Prepare visibility
        this.show(ItemPOI.QUANTITYCONTAINER);
        this.show(ItemPOI.QUANTITY);

        // Prepare shown data
        ((TextView)this.getView(ItemPOI.QUANTITY)).setText(getModel().getQuantity());
    }

    private void prepareNestedInventoryElements(){
        // Prepare visibility & listeners
        this.show(ItemPOI.INVENTORYMENU);
        this.show(ItemPOI.INVENTORY);
        this.show(ItemPOI.FULLNESS);
        this.show(ItemPOI.INVENTORYWEIGHT);
        this.show(ItemPOI.INVENTORYCAPACITY);

        if(getModel().isNestedInvVisible()) {
            this.show(ItemPOI.COLLAPSEBTN).setOnClickListener(this.getListener());
            this.show(ItemPOI.INVENTORYFRAGMENT);
        }else
            this.show(ItemPOI.EXPANDBTN).setOnClickListener(this.getListener());

        // Prepare shown data
        ((TextView)this.getView(ItemPOI.FULLNESS)).setText(getModel().getFullnessString());
        ((TextView)this.getView(ItemPOI.INVENTORYWEIGHT)).setText(getModel().getWeight());
        ((TextView)this.getView(ItemPOI.INVENTORYCAPACITY)).setText(getModel().getCapacity());
    }

    public ItemLayout prepareHoldingSpaceButtons(){
        this.show(ItemPOI.HOLDINGSPACE);
        if(!getModel().isHiddenTransferToHandsButton())
            this.show(ItemPOI.MOVETOHOLDINGSPACE).setOnClickListener(this.getListener());
        if(this.getModel().getBoundItemStorage() != null)
            this.show(ItemPOI.MOVEFROMHOLDINGSPACE).setOnClickListener(this.getListener());
        return this;
    }

    private void prepareEquipableElements(){

    }

    public ItemLayout prepareHolder(){
        setupInitState();

        if(!getModel().getQuantity().isEmpty())
            prepareGroupableElements();

        if(getModel().getBoundItemStorage() != null)
            prepareNestedInventoryElements();

        prepareHoldingSpaceButtons();

        return this;
    }

    public ItemLayout setOverallListener(){
        getRoot().setOnClickListener(this.getListener());
        return this;
    }

    public ItemLayout(ItemModel model, View root, View.OnClickListener listener){
        super(root);
        this.setModel(model);
        this.setListener(listener);
    }

    public ItemLayout(ItemModel model, View root){
        this(model, root, null);
    }


}
