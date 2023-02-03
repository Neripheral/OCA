package com.nerpage.oca.pac.controllers;

import androidx.annotation.Nullable;

import com.nerpage.oca.pac.AbstractController;
import com.nerpage.oca.pac.Presenter;
import com.nerpage.oca.pac.models.ExampleModel;
import com.nerpage.oca.pac.presenters.ExamplePresenter;
import com.nerpage.ocaproc.HasStandardModel;

@HasStandardModel({
        "String title \"Example Fragment\""
})
public abstract class ExampleController extends AbstractController<ExampleModel, ExamplePresenter> {
    @Override
    public void setPresenterFactory(@Nullable Presenter.Factory<ExamplePresenter> presenterFactory){
        super.setPresenterFactory(presenterFactory);
    }
}
