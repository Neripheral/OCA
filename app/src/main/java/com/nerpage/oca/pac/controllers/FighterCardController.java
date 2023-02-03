package com.nerpage.oca.pac.controllers;

import com.nerpage.oca.pac.AbstractController;
import com.nerpage.oca.pac.models.FighterCardModel;
import com.nerpage.oca.pac.presenters.FighterCardPresenter;
import com.nerpage.oca.util.AnimatedDrawable;
import com.nerpage.ocaproc.HasStandardModel;

@HasStandardModel({
    "String title \"\"",
    "String currentBlood \"\"",
    "String maxBlood \"\""
})
public abstract class FighterCardController extends AbstractController<FighterCardModel, FighterCardPresenter> {
    public abstract void updateTitle(String newTitle);
    public abstract void updateCurrentBlood(int newCurrentBlood);
    public abstract void updateMaxBlood(int newMaxBlood);
    public abstract void playAnimationOnAvatar(AnimatedDrawable animatedDrawable);
}
