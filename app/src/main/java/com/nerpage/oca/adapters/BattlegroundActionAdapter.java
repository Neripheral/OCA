package com.nerpage.oca.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.presenters.ActionCardPresenter;
import com.nerpage.oca.layouts.ActionCardLayout;
import com.nerpage.oca.interfaces.listeners.OnRecyclerItemClicked;
import com.nerpage.oca.fragments.models.ActionCardModel;

import java.util.ArrayList;
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

        public void setRoot(View root){
            p.setRoot(root);
        }
        
        public void updateView(){
            p.updateThumbnail(m.thumbnailResId);
            p.updateTitle(m.title);
            p.updateDescription(m.description);
            p.setOverallListener(this);
        }

        public void updateModel(ActionCardModel newModel){
            m.thumbnailResId = newModel.thumbnailResId;;
            m.title = newModel.title;
            m.description = newModel.description;
        }

        public abstract void onCardClicked(int position);
        
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
            p = new ActionCardPresenter();
            setRoot(root);
            m = new ActionCardModel();
        }

        // endregion //        ActionViewHolder: Constructors
        //================================================================================
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private List<ActionCardModel> dataset;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public void updateDataOnPosition(int position, ActionCardModel newModel){
        dataset.set(position, newModel);
        notifyItemChanged(position);
    }

    public BattlegroundActionAdapter setDataset(List<ActionCardModel> dataset) {
        this.dataset = dataset;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Interface

    public abstract void onCardClicked(int position);

    @NonNull
    @Override
    public ActionCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.battleground_action_listitem,
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
        holder.updateModel(dataset.get(position));
        holder.updateView();
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public BattlegroundActionAdapter(List<ActionCardModel> dataset) {
        this.dataset = dataset;
    }

    public BattlegroundActionAdapter() {
        this(new ArrayList<>());
    }

    // endregion //         Constructors
    //================================================================================
}
