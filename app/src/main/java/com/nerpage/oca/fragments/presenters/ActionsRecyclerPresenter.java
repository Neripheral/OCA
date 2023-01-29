package com.nerpage.oca.fragments.presenters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.AbstractPresenter;

public class ActionsRecyclerPresenter extends AbstractPresenter {
    //================================================================================
    // region //            POI

    public enum POI implements PointOfInterest {
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
    // region //            Private methods
    
    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public int getDescribedLayoutId() {
        return R.layout.fragment_actions_recycler;
    }

    public RecyclerView getRecycler(){
        return (RecyclerView) getView(POI.RECYCLER);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructor

    public ActionsRecyclerPresenter(View root){
        super(root);
    }

    // endregion //         Constructor
    //================================================================================
}
