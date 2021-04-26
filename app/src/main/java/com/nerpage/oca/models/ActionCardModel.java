package com.nerpage.oca.models;

import androidx.lifecycle.ViewModel;

public class ActionCardModel extends ViewModel {
    //================================================================================
    // region //            ActionViewModel: Fields

    private String title = "";
    private int thumbnailResId = 0;
    private String description = "";

    // endregion //         ActionViewModel: Fields
    //================================================================================
    //================================================================================
    // region //            ActionViewModel: Accessors

    public String getTitle() {
        return title;
    }

    public ActionCardModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getThumbnailResId() {
        return thumbnailResId;
    }

    public ActionCardModel setThumbnailResId(int thumbnailResId) {
        this.thumbnailResId = thumbnailResId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActionCardModel setDescription(String description) {
        this.description = description;
        return this;
    }

    // endregion //         ActionViewModel: Accessors
    //================================================================================
    //================================================================================
    // region //            ActionViewModel: Methods



    // endregion //         ActionViewModel: Methods
    //================================================================================
    //================================================================================
    // region //            ActionViewModel: Constructors

    public ActionCardModel(){}

    // endregion //         ActionViewModel: Constructors
    //================================================================================
}
