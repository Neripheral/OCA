package com.nerpage.oca.fragments.presenters;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.Presenter;

public class ActionsRecyclerPresenter extends Presenter {
    //================================================================================
    // region //            POI

    public enum POI implements Presenter.POI {
        //EXAMPLE_POI(R.id.examplepoi)
        ;

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
        //method returning R.layout id of layout that is described by this presenter
        return 0;
    }

    // endregion //         Interface
    //================================================================================
}
