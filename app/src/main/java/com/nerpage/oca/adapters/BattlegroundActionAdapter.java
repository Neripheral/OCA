package com.nerpage.oca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.LayoutHelper;
import com.nerpage.oca.interfaces.HasLayout;
import com.nerpage.oca.interfaces.listeners.OnRecyclerItemClicked;
import com.nerpage.oca.models.ActionCardModel;

import java.util.ArrayList;
import java.util.List;

public class BattlegroundActionAdapter extends RecyclerView.Adapter<BattlegroundActionAdapter.ActionViewHolder>{
    //================================================================================
    // region //            Inner classes

    public static class ActionViewHolder extends RecyclerView.ViewHolder implements HasLayout, View.OnClickListener{
        //================================================================================
        // region //           ActionViewHolder: Inner classes

        enum POI implements LayoutHelper.POI {
            THUMBNAIL(R.id.action_thumbnail),
            TITLE(R.id.action_title),
            INFO_BOX(R.id.action_info),
            DESCRIPTION(R.id.action_description);

            int id;

            @Override
            public int getId() {
                return this.id;
            }

            POI(int id) {
                this.id = id;
            }
        }

        // endregion //        ActionViewHolder: Inner classes
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Fields

        private View root;
        private OnRecyclerItemClicked listener;

        // endregion //        ActionViewHolder: Fields
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Accessors

        @Override
        public View getRoot() {
            return root;
        }

        public ActionViewHolder setRoot(View root) {
            this.root = root;
            return this;
        }

        public OnRecyclerItemClicked getListener() {
            return listener;
        }

        public ActionViewHolder setListener(OnRecyclerItemClicked listener) {
            this.listener = listener;
            return this;
        }

        // endregion //        ActionViewHolder: Accessors
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Methods

        public void updateWithModel(ActionCardModel model){
            ((ImageView)this.getView(POI.THUMBNAIL)).setImageResource(model.getThumbnailResId());
            ((TextView)this.getView(POI.TITLE)).setText(model.getTitle());
            ((TextView)this.getView(POI.DESCRIPTION)).setText(model.getDescription());
        }

        @Override
        public void onClick(View v) {
            getListener().onRecyclerItemClicked(v, getAdapterPosition());
        }

        // endregion //        ActionViewHolder: Methods
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Constructors

        public ActionViewHolder(@NonNull View itemView, OnRecyclerItemClicked listener) {
            super(itemView);
            this.root = itemView;
            this.listener = listener;
            itemView.findViewById(R.id.action_container).setOnClickListener(this);
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
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.battleground_action_listitem,
                        parent,
                        false);
        return new ActionViewHolder(v, getOnRecyclerItemClicked());
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
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
