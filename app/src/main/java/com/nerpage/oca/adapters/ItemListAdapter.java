package com.nerpage.oca.adapters;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.ItemListFragment;
import com.nerpage.oca.models.ItemModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{
    public int workMode;
    public static final int WORKMODE_PCINVENTORY = 1;
    public static final int WORKMODE_ITEMDB = 2;

    private ItemListFragment.LayoutListener listener;
    public List<ItemModel> dataset;
    public int currentWorkmode;

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<ItemListFragment.LayoutListener> listenerRef;
        private TextView itemTitle;
        private ImageButton removeButton;

        private ConstraintLayout itemQuantityContainer;
        private TextView itemQuantity;

        public void allVisibilityTo(int vis){
            removeButton.setVisibility(vis);
            itemQuantityContainer.setVisibility(vis);
        }

        public void prepareHolder(int workMode, ItemModel model){
            this.allVisibilityTo(View.VISIBLE);
            if(workMode == WORKMODE_ITEMDB){
                this.itemQuantityContainer.setVisibility(View.GONE);
                this.removeButton.setVisibility(View.GONE);
            }

            if(model.getQuantity().isEmpty()){
                this.itemQuantityContainer.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            this.listenerRef.get().onClick(v, this.getLayoutPosition());
        }

        public ItemViewHolder(View v, ItemListFragment.LayoutListener listener, int workMode){
            super(v);
            this.listenerRef = new WeakReference<>(listener);
            this.itemTitle = v.findViewById(R.id.inventory_item_title);
            this.removeButton = v.findViewById(R.id.inventory_item_remove_button);

            this.itemQuantityContainer = v.findViewById(R.id.inventory_item_quantity_container);
            this.itemQuantity = v.findViewById(R.id.inventory_item_quantity);

            this.allVisibilityTo(View.VISIBLE);

            v.setOnClickListener(this);
            removeButton.setOnClickListener(this);
        }
    }

    @Override
    public ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listitem, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v, this.listener, this.workMode);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position){
        ItemModel model = dataset.get(position);

        holder.prepareHolder(this.workMode, model);

        holder.itemTitle.setText(model.getTitle());
        holder.itemQuantity.setText(model.getQuantity());
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }


    public ItemListAdapter(List<ItemModel> dataset, ItemListFragment.LayoutListener listener, int workMode){
        this.listener = listener;
        this.dataset = dataset;
        this.workMode = workMode;
    }
}
