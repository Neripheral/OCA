package com.nerpage.oca.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.fragments.models.ActionCardModel;
import com.nerpage.oca.fragments.presenters.ActionsRecyclerPresenter;
import com.nerpage.oca.fragments.models.ActionsRecyclerModel;

import java.util.ArrayList;
import java.util.List;

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

    public void updateModel(List<Action> actions) {
        m.possibleActions = new ArrayList<>();
        for(Action action : actions){
            ActionCardModel newModel = new ActionCardModel();
            newModel.thumbnailResId = action.getThumbnailResId();
            newModel.title = action.getName(getContext());
            newModel.description = action.getDescription(requireContext());
            m.possibleActions.add(newModel);
        }
    }

    //TODO: notifyDataSetChanged shouldn't be used
    @SuppressLint("NotifyDataSetChanged")
    public void updatePresentation() {
        adapter.notifyDataSetChanged();
    }

    public void initRecyclerView(){
        p.getRecycler().setLayoutManager(layoutManager);
        p.getRecycler().setAdapter(adapter);
        p.getRecycler().addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                onRecyclerScrolled();
            }
        });
        (new LinearSnapHelper()).attachToRecyclerView(p.getRecycler());
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

            @Override
            public List<ActionCardModel> getDataset() {
                return m.possibleActions;
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(p.getDescribedLayoutId(), container, false);
        p = new ActionsRecyclerPresenter(root);

        initRecyclerView();

        return root;
    }

    // endregion //         Fragment overrides
    //================================================================================
}
