package com.nerpage.oca.pac.presenters;

import android.view.View;

import com.nerpage.oca.pac.AbstractPresenter;
import com.nerpage.oca.util.Presenters;


public final class DefaultActionCardPresenter extends AbstractPresenter implements ActionCardPresenter {

    public DefaultActionCardPresenter(View root) {
        super(root);
    }


    @Override
    public void updateThumbnail(int imgResId){
        Presenters.setImage(POI.THUMBNAIL, getRoot(), imgResId);
    }

    @Override
    public void updateTitle(String title){
        Presenters.setText(POI.TITLE, getRoot(), title);
    }

    @Override
    public void updateDescription(String description){
        Presenters.setText(POI.DESCRIPTION, getRoot(), description);
    }

    @Override
    public void updateIsOpen(boolean isOpen){
        if(isOpen)
            Presenters.show(POI.INFO_BOX, getRoot());
        else
            Presenters.hide(POI.INFO_BOX, getRoot());
    }



    public static class DefaultActionCardPresenterFactory implements Factory<ActionCardPresenter> {
        @Override
        public ActionCardPresenter createFor(View root) {
            return new DefaultActionCardPresenter(root);
        }
    }
}