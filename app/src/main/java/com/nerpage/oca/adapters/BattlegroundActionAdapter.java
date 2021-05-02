package com.nerpage.oca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.layouts.ActionCardLayout;
import com.nerpage.oca.interfaces.listeners.OnRecyclerItemClicked;
import com.nerpage.oca.layouts.models.ActionCardModel;

import java.util.ArrayList;
import java.util.List;

public class BattlegroundActionAdapter extends RecyclerView.Adapter<BattlegroundActionAdapter.ActionCardHolder>{
    //================================================================================
    // region //            Inner classes

    public static class ActionCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //================================================================================
        // region //           ActionViewHolder: Fields

        private ActionCardLayout layout;
        private OnRecyclerItemClicked listener;

        // endregion //        ActionViewHolder: Fields
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Accessors


        private ActionCardLayout getLayout() {
            return layout;
        }

        private ActionCardHolder setLayout(ActionCardLayout layout) {
            this.layout = layout;
            return this;
        }

        private OnRecyclerItemClicked getListener() {
            return listener;
        }

        private ActionCardHolder setListener(OnRecyclerItemClicked listener) {
            this.listener = listener;
            return this;
        }

        // endregion //        ActionViewHolder: Accessors
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Methods

        public void updateWithModel(ActionCardModel model){
            getLayout().setModel(model);
            getLayout().updateViewData();
        }

        @Override
        public void onClick(View v) {
            getListener().onRecyclerItemClicked(v, getAdapterPosition());
        }

        // endregion //        ActionViewHolder: Methods
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Constructors

        public ActionCardHolder(@NonNull View itemView, OnRecyclerItemClicked listener) {
            super(itemView);
            this.layout = new ActionCardLayout(itemView);
            this.listener = listener;
            getLayout().getView(ActionCardLayout.POI.CONTAINER).setOnClickListener(this);
        }

        // endregion //        ActionViewHolder: Constructors
        //================================================================================
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private List<ActionCardModel> dataset;
    private OnRecyclerItemClicked onRecyclerItemClicked;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public List<ActionCardModel> getDataset() {
        return dataset;
    }

    public BattlegroundActionAdapter setDataset(List<ActionCardModel> dataset) {
        this.dataset = dataset;
        return this;
    }

    public OnRecyclerItemClicked getOnRecyclerItemClicked() {
        return onRecyclerItemClicked;
    }

    public BattlegroundActionAdapter setOnRecyclerItemClicked(OnRecyclerItemClicked onRecyclerItemClicked) {
        this.onRecyclerItemClicked = onRecyclerItemClicked;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    @NonNull
    @Override
    public ActionCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.battleground_action_listitem,
                        parent,
                        false);
        return new ActionCardHolder(v, getOnRecyclerItemClicked());
    }

    @Override
    public void onBindViewHolder(@NonNull ActionCardHolder holder, int position) {
        holder.updateWithModel(this.getDataset().get(position));
    }

    @Override
    public int getItemCount() {
        return this.getDataset().size();
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public BattlegroundActionAdapter(List<ActionCardModel> dataset, OnRecyclerItemClicked listener) {
        this.dataset = dataset;
        this.onRecyclerItemClicked = listener;
    }

    public BattlegroundActionAdapter(OnRecyclerItemClicked listener) {
        this(new ArrayList<>(), listener);
    }

    // endregion //         Constructors
    //================================================================================
}
