package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.adapters.BattlegroundActionAdapter;
import com.nerpage.oca.fragments.presenters.ActionsRecyclerPresenter;
import com.nerpage.oca.fragments.models.ActionsRecyclerModel;

public class ActionsRecyclerFragment extends PACFragment<ActionsRecyclerModel, ActionsRecyclerPresenter> implements PACFragment.CallbackToParent<ActionsRecyclerFragment.Callback>{
    //================================================================================
    // region //            Callback

    public interface Callback extends PACFragment.CallbackToParent.Callback {
        void tellActionItemWasClicked(int position);
    }

    // endregion //         Callback
    //================================================================================
    //================================================================================
    // region //            Fields

    private LinearLayoutManager layoutManager;
    private BattlegroundActionAdapter adapter;
    private @Nullable Callback callToParent;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors


    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private void onRecyclerScrolled(){
        updateDetailsVisibility();
    }

    private void updateDetailsVisibility(){
        BattlegroundActionAdapter.ActionCardHolder firstHolder =
                (BattlegroundActionAdapter.ActionCardHolder)
                        p.getRecycler().findViewHolderForLayoutPosition(
                            layoutManager.findFirstVisibleItemPosition());
        BattlegroundActionAdapter.ActionCardHolder lastHolder =
                (BattlegroundActionAdapter.ActionCardHolder)
                        p.getRecycler().findViewHolderForLayoutPosition(
                            layoutManager.findLastVisibleItemPosition());

        if(firstHolder != null && lastHolder != null)
            if(firstHolder == lastHolder){
                firstHolder.showDetails();
            }else{
                firstHolder.hideDetails();
                lastHolder.hideDetails();
            }
    }

    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    //TODO: only for migration purposes, remove when unnecessary
    public void injectRoot(View newRoot){
        p.setRoot(newRoot);
    }

    public void updateModel(/*arg list*/) {
        //main, public model updating method
    }

    public void updatePresentation() {
        //main, public presentation updating method
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Fragment overrides


    @Override
    public void registerCallback(Callback callback) {
        callToParent = callback;
    }

    @Override
    public void initPAC() {
        //default initPAC, not necessary to change
        m = new ActionsRecyclerModel();
        p = new ActionsRecyclerPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new BattlegroundActionAdapter(){
            @Override
            public void onCardClicked(int position) {
                if(callToParent != null)
                    callToParent.tellActionItemWasClicked(position);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(p.getDescribedLayoutId(), container, false);
        p.setRoot(root);

        p.getRecycler().setLayoutManager(layoutManager);
        p.getRecycler().setAdapter(adapter);
        p.getRecycler().addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                onRecyclerScrolled();
            }
        });
        (new LinearSnapHelper()).attachToRecyclerView(p.getRecycler());

        return root;
    }

    // endregion //         Fragment overrides
    //================================================================================
}
