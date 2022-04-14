package com.nerpage.oca.fragments.presenters;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.Presenter;

public class ActionsRecyclerPresenter extends Presenter {
    //================================================================================
    // region //            POI

    public enum POI implements Presenter.POI {
        RECYCLER(R.id.actions_recycler);

        int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id) {
            this.id = id;
        }
    }

    // endregion //         POI
    //================================================================================
    //================================================================================
    // region //            Interface

    public int getDescribedLayoutId() {
        return R.layout.fragment_actions_recycler;
    }

    // endregion //         Interface
    //================================================================================
}
