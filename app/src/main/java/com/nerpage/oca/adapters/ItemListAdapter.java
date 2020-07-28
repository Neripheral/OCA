package com.nerpage.oca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.CarryingSpace;
import com.nerpage.oca.fragments.ItemListFragment;
import com.nerpage.oca.models.ItemModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{
    public enum Workmode{
        PCINVENTORY,
        ITEMDB,
        EQUIPMENT;
    }
    public Workmode workmode;
    private Context context;
    public static final String NESTED_INVENTORY = "NESTED_INVENTORY";
    private ItemListFragment.LayoutListener listener;
    public List<ItemModel> dataset;
    ItemListFragment parent;

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<ItemListFragment.LayoutListener> listenerRef;
        private View rootView;
        private ItemListFragment nestedInventoryFragment;

        @Override
        public void onClick(View v) {
            this.listenerRef.get().onClick(v, this.getLayoutPosition());
        }

        public ItemViewHolder(View v, ItemListFragment.LayoutListener listener){
            super(v);
            this.rootView = v;
            this.listenerRef = new WeakReference<>(listener);
            this.nestedInventoryFragment = null;
        }
    }

    @Override
    public ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listitem, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v, this.listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position){
        ItemModel model = dataset.get(position);
        switch(this.workmode) {
            case PCINVENTORY:
                ItemModel.LayoutHelper helper = model.initLayoutHelperFor(holder.rootView)
                            .setListener(holder)
                            .prepareHolder()
                            .showRemoveButton()
                            .showOperationSpaceTransferButtons()
                            .showSideMenu();
                if(model.getItemRef().get() instanceof CarryingSpace)
                    helper.hideSideMenu();
                if(model.getBoundItemStorage() != null)
                    holder.nestedInventoryFragment =
                            new ItemListFragment()
                                    .setCorrespondingInventory(model.getBoundItemStorage())
                                    .setOnChildChangedCallback(() -> {
                                        parent.onChildChanged(position);
                                    });
                break;
            case ITEMDB:
                model.initLayoutHelperFor(holder.rootView)
                        .setListener(holder)
                        .prepareHolder()
                        .setOverallListener();
                break;
            case EQUIPMENT:
                model.initLayoutHelperFor(holder.rootView)
                        .setListener(holder)
                        .prepareHolder()
                        .showEquipButton();
                break;
        }
    }

    @Override
    public void onViewRecycled(@NonNull ItemViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public void bindNestedInventoryFragment(View v, ItemListFragment fragment){
        View fragmentHolder = v.findViewById(R.id.inventory_nested_inv_fragment_holder);
        fragmentHolder.setId(View.generateViewId());
        fragmentHolder.setTag(NESTED_INVENTORY);
        parent.getChildFragmentManager().beginTransaction()
                .add(fragmentHolder.getId(), fragment)
                .commitNow();
    }

    public void releaseNestedInventoryFragment(ItemListFragment f){
        parent.getChildFragmentManager().beginTransaction()
                .remove(f)
                .commit();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ItemViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder.nestedInventoryFragment != null)
            this.bindNestedInventoryFragment(holder.rootView, holder.nestedInventoryFragment);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ItemViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        if(holder.nestedInventoryFragment != null) {
            this.releaseNestedInventoryFragment(holder.nestedInventoryFragment);
            holder.rootView.findViewWithTag(NESTED_INVENTORY).setId(R.id.inventory_nested_inv_fragment_holder);
        }
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    public ItemListAdapter(Context context, ItemListFragment parent, List<ItemModel> dataset, ItemListFragment.LayoutListener listener, Workmode workmode){
        this.context = context;
        this.parent = parent;
        this.listener = listener;
        this.dataset = dataset;
        this.workmode = workmode;
    }
}
