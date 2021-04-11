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

import java.util.List;
import java.util.function.Consumer;

public class BattlegroundActionAdapter extends RecyclerView.Adapter<BattlegroundActionAdapter.ActionViewHolder> {
    //================================================================================
    // region //            Inner classes

    public static class ActionViewModel {
        //================================================================================
        // region //            ActionViewModel: Fields

        private String title;
        private int thumbnailImageId;
        private String description;

        // endregion //         ActionViewModel: Fields
        //================================================================================
        //================================================================================
        // region //            ActionViewModel: Accessors

        public String getTitle() {
            return title;
        }

        public ActionViewModel setTitle(String title) {
            this.title = title;
            return this;
        }

        public int getThumbnailImageId() {
            return thumbnailImageId;
        }

        public ActionViewModel setThumbnailImageId(int thumbnailImageId) {
            this.thumbnailImageId = thumbnailImageId;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public ActionViewModel setDescription(String description) {
            this.description = description;
            return this;
        }


        // endregion //         ActionViewModel: Accessors
        //================================================================================
        //================================================================================
        // region //            ActionViewModel: Methods



        // endregion //         ActionViewModel: Methods
        //================================================================================
        //================================================================================
        // region //            ActionViewModel: Constructors

        public ActionViewModel(String title, int thumbnailImageId, String description) {
            this.title = title;
            this.thumbnailImageId = thumbnailImageId;
            this.description = description;
        }

        // endregion //         ActionViewModel: Constructors
        //================================================================================
    }

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
        private Consumer<View> listener;

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

        public Consumer<View> getListener() {
            return listener;
        }

        public ActionViewHolder setListener(Consumer<View> listener) {
            this.listener = listener;
            return this;
        }

        // endregion //        ActionViewHolder: Accessors
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Methods

        public void updateWithModel(ActionViewModel model){
            ((ImageView)this.getView(POI.THUMBNAIL)).setImageResource(model.getThumbnailImageId());
            ((TextView)this.getView(POI.TITLE)).setText(model.getTitle());
            ((TextView)this.getView(POI.DESCRIPTION)).setText(model.getDescription());
        }

        // endregion //        ActionViewHolder: Methods
        //================================================================================
        //================================================================================
        // region //           ActionViewHolder: Constructors

        public ActionViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        // endregion //        ActionViewHolder: Constructors
        //================================================================================
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private List<ActionViewModel> dataset;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public List<ActionViewModel> getDataset() {
        return dataset;
    }

    public BattlegroundActionAdapter setDataset(List<ActionViewModel> dataset) {
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

    public BattlegroundActionAdapter(List<ActionViewModel> dataset) {
        this.dataset = dataset;
    }

    // endregion //         Constructors
    //================================================================================
}
