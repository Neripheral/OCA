package com.nerpage.oca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.presenters.ActionCardPresenter;
import com.nerpage.oca.fragments.models.ActionCardModel;

import java.util.List;

public abstract class BattlegroundActionAdapter extends RecyclerView.Adapter<BattlegroundActionAdapter.ActionCardHolder>{
    //================================================================================
    // region //            Inner classes

    public static abstract class ActionCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //================================================================================
        // region //           ActionViewHolder: Fields

        private ActionCardPresenter p;
        private ActionCardModel m;

        // endregion //        ActionViewHolder: Fields
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Methods
        
        public void updateView(){
            p.updateThumbnail(m.thumbnailResId);
            p.updateTitle(m.title);
            p.updateDescription(m.description);
            p.setOverallListener(this);
        }

        public void updateModel(ActionCardModel newModel){
            m.thumbnailResId = newModel.thumbnailResId;
            m.title = newModel.title;
            m.description = newModel.description;
        }

        public abstract void onCardClicked(int position);

        public void showDetails(){
            p.showDetails();
        }

        public void hideDetails(){
            p.hideDetails();
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

        public ActionCardHolder(@NonNull View root) {
            super(root);
            p = new ActionCardPresenter(root);
            m = new ActionCardModel();
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

    // endregion //         Abstract
    //================================================================================
    //================================================================================
    // region //            Interface

    @NonNull
    @Override
    public ActionCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_actioncard,
                        parent,
                        false);
        return new ActionCardHolder(v){
            @Override
            public void onCardClicked(int position) {
                BattlegroundActionAdapter.this.onCardClicked(position);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ActionCardHolder holder, int position) {
        holder.updateModel(getDataset().get(position));
        holder.updateView();
        holder.hideDetails();
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
