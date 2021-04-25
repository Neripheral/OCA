package com.nerpage.oca.models;

import androidx.lifecycle.ViewModel;

public class FighterCardModel extends ViewModel {
    //================================================================================
    // region //            Fields

    private String title;
    private String currentBlood;
    private String maxBlood;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public String getTitle() {
        return title;
    }

    public FighterCardModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCurrentBlood() {
        return currentBlood;
    }

    public FighterCardModel setCurrentBlood(String currentBlood) {
        this.currentBlood = currentBlood;
        return this;
    }

    public String getMaxBlood() {
        return maxBlood;
    }

    public FighterCardModel setMaxBlood(String maxBlood) {
        this.maxBlood = maxBlood;
        return this;
    }


    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Constructor

    public FighterCardModel() {}

    // endregion //         Constructor
    //================================================================================
}
