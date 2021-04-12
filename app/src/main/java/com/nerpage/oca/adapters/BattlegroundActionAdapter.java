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
import com.nerpage.oca.models.ActionCardModel;

import java.util.ArrayList;
import java.util.List;

public class BattlegroundActionAdapter extends RecyclerView.Adapter<BattlegroundActionAdapter.ActionViewHolder> {
    //================================================================================
    // region //            Inner classes

    public static class ActionViewHolder extends RecyclerView.ViewHolder implements HasLayout {
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

        // endregion //        ActionViewHolder: Accessors
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Methods

        public void updateWithModel(ActionCardModel model){
            ((ImageView)this.getView(POI.THUMBNAIL)).setImageResource(model.getThumbnailResId());
            ((TextView)this.getView(POI.TITLE)).setText(model.getTitle());
            ((TextView)this.getView(POI.DESCRIPTION)).setText(model.getDescription());
        }

        // endregion //        ActionViewHolder: Methods
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Constructors

        public ActionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.root = itemView;
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

    public List<ActionCardModel> getDataset() {
        return dataset;
    }

    public BattlegroundActionAdapter setDataset(List<ActionCardModel> dataset) {
        this.dataset = dataset;
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
        return new ActionViewHolder(v);
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

    public BattlegroundActionAdapter(List<ActionCardModel> dataset) {
        this.dataset = dataset;
    }

    public BattlegroundActionAdapter() {
        dataset = new ArrayList<>();
    }

    // endregion //         Constructors
    //================================================================================
}
