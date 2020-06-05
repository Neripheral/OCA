
package com.nerpage.oca.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.nerpage.oca.R;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {
    //================================================================================
    // Inner Classes
    //================================================================================

    public interface Groupable{
        boolean eagerToAdd(Item item);
        boolean canAdd(Item item);
        Item add(Item item);

        boolean eagerToSubtract(Item item);
        boolean canSubtract(Item item);
        Item subtract(Item item);

        /**
         * Gets the String supposed to represent quantity of the group. It is only a visual representation
         * of a group's size and shouldn't serve any backend, logical purpose.
         * @return
         */
        String getShownQuantity();
    }

    public enum Tag{
        MATERIAL,
        TOOL,
        FOOD,
        CARCASS,
        HERB,
        TECH;

        int titleId;
        int imageId;
    }

    //================================================================================
    // Attributes
    //================================================================================

    private boolean toDiscard = false;
    private List<Tag> tags = new ArrayList<>();

    //================================================================================
    // Accessors
    //================================================================================

    public List<Tag> getTags(){
        return this.tags;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Item(){
        this.initTags();
    }

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        if(o instanceof Item)
            return this.equals((Item)o);
        return false;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Function is called when deciding if certain Item should be removed from
     * ItemStorage (e.g. if it's quantity equals 0)
     */
    public boolean shouldBeDiscarded(){
        return this.toDiscard;
    }
    public void discard(){
        this.toDiscard = true;
    }
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
        return this.getId().equals(item.getId());
    }
    public String composeOnAddToastMessage(Context context){
        return context.getResources().getString(R.string.item_add_simple_notification, this.getName(context));
    }
    public AlertDialog initByDialog(AlertDialog.Builder builder, Runnable onSuccess){
        onSuccess.run();
        return null;
    }
    public AlertDialog removeByDialog(AlertDialog.Builder builder, Runnable onRemove){
        builder.setTitle(builder.getContext().getResources().getString(R.string.dialog_remove_item_question, this.getName(builder.getContext())))
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        discard();
                        onRemove.run();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
    public boolean matchesSearch(Item item, int tags){
        return this.equals(item);
    }
    public abstract void initTags();
}

