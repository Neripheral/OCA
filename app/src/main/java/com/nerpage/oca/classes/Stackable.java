package com.nerpage.oca.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nerpage.oca.R;

public abstract class Stackable extends Item implements Item.Groupable {
    //================================================================================
    // Attributes
    //================================================================================

    private int quantity = 0;

    //================================================================================
    // Accessors
    //================================================================================

    public int getQuantity() {
        return quantity;
    }

    public Stackable setQuantity(int quantity) {
        quantity = (quantity > 0 ? quantity : 0);
        this.quantity = quantity;
        return this;
    }

    //================================================================================
    // Private methods
    //================================================================================

    private EditText getNumberInput(Context context){
        EditText input = new EditText(context);
        input.setPadding(50,50,50,50);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        return input;
    }

    //================================================================================
    // Public methods
    //================================================================================

    public int modifyQuantity(int quantity){
        this.setQuantity(this.getQuantity() + quantity);
        return this.getQuantity();
    }

    public boolean isCompatibleWith(Item item){
        if(item.getClass() == this.getClass()) {
            return true;
        }
        return false;
    }

    public Item combine(Stackable item){
        this.modifyQuantity(item.getQuantity());
        return null;
    }

    //================================================================================
    // Item.Groupable overrides
    //================================================================================

    @Override
    public boolean eagerToAdd(Item item) {
        return this.isCompatibleWith(item);
    }

    @Override
    public boolean canAdd(Item item){
        return this.eagerToAdd(item);
    }

    @Override
    public Item add(Item item){
        if(this.canAdd(item))
            return this.combine((Stackable)item);
        return item;
    }

    @Override
    public boolean eagerToSubtract(Item item) {
        return this.isCompatibleWith(item);
    }

    @Override
    public boolean canSubtract(Item item) {
        if(this.eagerToSubtract(item)){
            if(((Stackable)item).getQuantity() > this.getQuantity())
                return true;
        }
        return false;
    }

    @Override
    public Item subtract(Item item) {
        if(this.canSubtract(item)) {
            this.modifyQuantity(-((Stackable)item).getQuantity());
            return null;
        }
        return item;
    }

    @Override
    public String getShownQuantity() {
        return String.valueOf(getQuantity());
    }

    //================================================================================
    // Item overrides
    //================================================================================

    @Override
    public boolean shouldBeDiscarded() {
        if(this.getQuantity() == 0)
            this.discard();
        return super.shouldBeDiscarded();
    }

    @Override
    public AlertDialog initByDialog(AlertDialog.Builder builder, Runnable onSuccess) {
        EditText input = getNumberInput(builder.getContext());
        builder.setView(input)
                .setTitle(R.string.dialog_howmuch)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int quantity = 0;
                        try {
                            quantity = Integer.parseInt(input.getText().toString());
                        }catch(NumberFormatException e){
                            Log.e("exception", e.getMessage());
                        }
                        if(quantity > 0) {
                            setQuantity(quantity);
                            Stackable.super.initByDialog(builder, onSuccess);
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public AlertDialog removeByDialog(AlertDialog.Builder builder, Runnable onRemove) {
        EditText input = getNumberInput(builder.getContext());
        builder.setView(input)
                .setTitle(R.string.dialog_howmuch)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        int quantity = Integer.parseInt(input.getText().toString());
                        if(quantity > getQuantity()){
                            input.getBackground().setColorFilter(builder.getContext().getResources().getColor(R.color.optionWrong, null), PorterDuff.Mode.SRC_ATOP);
                        }else{
                            modifyQuantity(-quantity);
                            onRemove.run();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return dialog;
    }
}
