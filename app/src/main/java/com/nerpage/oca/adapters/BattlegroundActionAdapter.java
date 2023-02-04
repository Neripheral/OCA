package com.nerpage.oca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.presenters.ActionCardPresenter;
import com.nerpage.oca.fragments.models.ActionCardModel;
import com.nerpage.oca.pac.controllers.ActionCardController;
import com.nerpage.oca.pac.controllers.implementation.DefaultActionCardController;

import java.util.List;

public abstract class BattlegroundActionAdapter extends RecyclerView.Adapter<BattlegroundActionAdapter.ActionCardHolder>{
    //================================================================================
    // region //            Inner classes

    public static abstract class ActionCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //================================================================================
        // region //           ActionViewHolder: Fields

        private ActionCardController controller;
        public View root;

        // endregion //        ActionViewHolder: Fields
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Methods
        
        public void updateView(){
        }

        public void updateModel(ActionCardModel newModel){
            controller.setThumbnailResId(newModel.thumbnailResId);
            controller.setTitle(newModel.title);
            controller.setDescription(newModel.description);
        }

        public abstract void onCardClicked(int position);

        public void showDetails(){
            controller.setIsDescriptionBoxOpen(true);
        }

        public void hideDetails(){
            controller.setIsDescriptionBoxOpen(false);
        }

        // endregion //        ActionViewHolder: Methods
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Overrides

        @Override
        public void onClick(View view) {
            onCardClicked(this.getAdapterPosition());
        }

        // endregion //        ActionViewHolder: Overrides
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Constructors

        public ActionCardHolder(View v, ActionCardController controller) {
            super(v);
            this.root = v;
            this.controller = controller;
        }

        // endregion //        ActionViewHolder: Constructors
        //================================================================================
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Abstract

    public abstract void onCardClicked(int position);

    public abstract List<ActionCardModel> getDataset();

    public abstract FragmentManager getFragmentManager();


    // endregion //         Abstract
    //================================================================================
    //================================================================================
    // region //            Interface

    private static int viewIdCounter = 1;

    @NonNull
    @Override
    public ActionCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.frameholder,
                        parent,
                        false);
        v.setLayoutParams(new ViewGroup.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT));
        v.setTag(R.layout.frameholder, viewIdCounter);
        int id = View.generateViewId();
        v.setId(id);
        viewIdCounter++;
        ActionCardController actionCard = new DefaultActionCardController();
        getFragmentManager().beginTransaction().replace(id, actionCard, "actionCard" + viewIdCounter).commit();
        return new ActionCardHolder(v, actionCard){
            @Override
            public void onCardClicked(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ActionCardHolder holder, int position) {
        holder.updateModel(getDataset().get(position));
        holder.updateView();
        holder.hideDetails();
        holder.root.setOnClickListener((v)->BattlegroundActionAdapter.this.onCardClicked(position));
    }

    @Override
    public int getItemCount() {
        return getDataset().size();
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public BattlegroundActionAdapter() {}

    // endregion //         Constructors
    //================================================================================
}
