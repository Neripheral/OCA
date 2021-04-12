package com.nerpage.oca.models;

import com.nerpage.oca.adapters.BattlegroundActionAdapter;

public class ActionCardModel {
    //================================================================================
    // region //            ActionViewModel: Fields

    private String title;
    private int thumbnailImageId;
    private String description;

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

    public int getThumbnailImageId() {
        return thumbnailImageId;
    }

    public ActionCardModel setThumbnailImageId(int thumbnailImageId) {
        this.thumbnailImageId = thumbnailImageId;
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

    public ActionCardModel(String title, int thumbnailImageId, String description) {
        this.title = title;
        this.thumbnailImageId = thumbnailImageId;
        this.description = description;
    }

    // endregion //         ActionViewModel: Constructors
    //================================================================================
}
