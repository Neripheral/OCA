package com.nerpage.oca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.models.ItemModel;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ItemViewHolder> {
    public List<ItemModel> dataset;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTitle;

        public ItemViewHolder(View v){
            super(v);
            itemTitle = v.findViewById(R.id.inventory_item_title);
        }
    }

    @Override
    public InventoryAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listitem, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position){
        ItemModel model = dataset.get(position);

        holder.itemTitle.setText(model.getTitle());
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }


    public InventoryAdapter(List<ItemModel> dataset){
        this.dataset = dataset;
    }
}
