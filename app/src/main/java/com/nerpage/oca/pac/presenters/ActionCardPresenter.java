package com.nerpage.oca.pac.presenters;

import android.view.View;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.Presenter;
import com.nerpage.oca.util.Presenters;

public interface ActionCardPresenter extends Presenter {
    static int getDescribedLayoutId() {
        return R.layout.fragment_actioncard;
    }

    static Factory<ActionCardPresenter> getPresenter(){
        return new DefaultActionCardPresenter.DefaultActionCardPresenterFactory();
    }

    void updateThumbnail(int imgResId);
    void updateTitle(String title);
    void updateDescription(String description);
    void updateIsOpen(boolean isOpen);

    enum POI implements PointOfInterest {
        THUMBNAIL(R.id.action_thumbnail),
        TITLE(R.id.action_title),
        INFO_BOX(R.id.action_info),
        DESCRIPTION(R.id.action_description);

        private final int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id) {
            this.id = id;
        }
    }
}