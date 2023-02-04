package com.nerpage.oca.pac.controllers;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.AbstractController;
import com.nerpage.oca.pac.models.ActionCardModel;
import com.nerpage.oca.pac.presenters.ActionCardPresenter;
import com.nerpage.ocaproc.HasStandardModel;

@HasStandardModel({
        "String title \"missing_title\"",
        "Integer thumbnailResId " + android.R.drawable.ic_menu_report_image,
        "String description \"missing_description\"",
        "Boolean open true"
})
public abstract class ActionCardController extends AbstractController<ActionCardModel, ActionCardPresenter> {
    public abstract void setTitle(String title);
    public abstract void setThumbnailResId(int resId);
    public abstract void setDescription(String description);
    public abstract void setIsDescriptionBoxOpen(boolean isOpen);
}