
package com.nerpage.oca.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


import com.nerpage.oca.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public abstract class Item implements Identifiable{
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
         */
        String getShownQuantity();
    }

    public enum Tag{
        BACKPACK(R.drawable.tag_backpack),
        CUSTOM(R.drawable.tag_custom),
        GEM(R.drawable.tag_gem),
        VALUABLE(R.drawable.tag_valuable),
        GLOVE(R.drawable.tag_glove),
        CLOTHING_TOP(R.drawable.tag_clothing_top);

        int titleId;
        int imageId;

        public int getImageId() {
            return imageId;
        }

        Tag(int imageId){
            this.imageId = imageId;
        }
    }

    //================================================================================
    // Fields
    //================================================================================

    private boolean toDiscard = false;
    private List<Tag> tags = new ArrayList<>();

    //================================================================================
    // Accessors
    //================================================================================

    public boolean getToDiscard(){
        return this.toDiscard;
    }
    public Item setToDiscard(boolean toDiscard){
        this.toDiscard = toDiscard;
        return this;
    }

    public List<Tag> getTags(){
        return this.tags;
    }
    public Item setTags(List<Tag> tags){
        this.tags = tags;
        return this;
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

    @Override
    public String getPrefix() {
        return "item";
    }

    public boolean equals(Item item){
        return this.getId().equals(item.getId());
    }
    public Item copy(){
        Item clone = null;
        try {
            clone = this.getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        assert clone != null;
        clone.setToDiscard(this.getToDiscard());
        clone.setTags(new ArrayList<>(this.getTags()));
        return clone;
    }
    public String composeOnAddToastMessage(Context context){
        return context.getResources().getString(R.string.item_add_simple_notification, this.getName(context));
    }
    public AlertDialog initByDialog(AlertDialog.Builder builder, Runnable onSuccess){
        onSuccess.run();
        return null;
    }
    public AlertDialog removeByDialog(AlertDialog.Builder builder, Consumer<Item> onRemove){
        builder.setTitle(builder.getContext().getResources().getString(R.string.dialog_remove_item_question, this.getName(builder.getContext())))
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Item removed = null;
                        try {
                            removed = (Item)this.clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        discard();
                        onRemove.accept(removed);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_no, (dialog, which) -> dialog.dismiss());
        return builder.create();
    }
    public Item move(){
        Item toReturn = this.copy();
        this.discard();
        return toReturn;
    }
    public AlertDialog moveByDialog(AlertDialog.Builder builder, Consumer<Item> onMove){
        Item item = this.move();
        onMove.accept(item);
        return null;
    }
    public abstract void initTags();
    public Tag getMainTag(){
        if(this.getTags().size() > 0)
            return this.getTags().get(0);
        else
            return Tag.CUSTOM;
    }
    public abstract int getWeight();
    public String getWeightToDisplay(){
        return String.format(Locale.getDefault(), "%.2f", (double)this.getWeight()/1000);
    }
}

