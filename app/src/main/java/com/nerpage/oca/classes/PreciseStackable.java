package com.nerpage.oca.classes;

import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

public abstract class PreciseStackable extends Stackable {
    //================================================================================
    // Attributes
    //================================================================================

    int precision = 1;

    //================================================================================
    // Accessors
    //================================================================================

    public int getPrecision() {
        return precision;
    }

    public PreciseStackable setPrecision(int precision) {
        this.precision = precision;
        return this;
    }

    //================================================================================
    // Methods
    //================================================================================

    public double getPreciseQuantity() {
        return (((double)super.getQuantity())/(Math.pow(10, getPrecision())));
    }

    //================================================================================
    // Stackable overrides
    //================================================================================

    @Override
    protected EditText getNumberInput(Context context) {
        EditText input = super.getNumberInput(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return input;
    }

    @Override
    public int getQuantityFromString(String qString) {
        double quantity = Double.parseDouble(qString);
        quantity *= Math.pow(10, this.getPrecision());
        int intQuantity = (int)quantity;
        return super.getQuantityFromString(String.valueOf(intQuantity));
    }

    @Override
    public String getShownQuantity() {
        return String.format("%." + this.getPrecision() + "f", this.getPreciseQuantity());
    }

    @Override
    public int getWeight() {
        return (int)(this.getPreciseQuantity() * this.getUnitWeight());
    }
}
