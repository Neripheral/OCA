package com.nerpage.oca.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.fragments.ItemListFragment;
import com.nerpage.oca.interfaces.Inventory;
import com.nerpage.oca.models.ItemModel;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.view.View.generateViewId;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{
    private Context context;
    public int workMode;
    public static final int WORKMODE_PCINVENTORY = 1;
    public static final int WORKMODE_ITEMDB = 2;

    private ItemListFragment.LayoutListener listener;
    public List<ItemModel> dataset;
    Fragment parent;

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<ItemListFragment.LayoutListener> listenerRef;
        private View rootView;

        @Override
        public void onClick(View v) {
            this.listenerRef.get().onClick(v, this.getLayoutPosition());
        }

        public ItemViewHolder(View v, ItemListFragment.LayoutListener listener, int workMode){
            super(v);
            this.rootView = v;
            this.listenerRef = new WeakReference<>(listener);
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

        if(workMode == WORKMODE_ITEMDB)
            model.initLayoutHelperFor(holder.rootView, parent.getChildFragmentManager())
                .setListener(holder)
                .prepareHolder()
                .setOverallListener();
        else
            model.initLayoutHelperFor(holder.rootView, parent.getChildFragmentManager())
                .setListener(holder)
                .prepareHolder()
                .showRemoveButton()
                .showOperationSpaceTransferButtons()
                .showSideMenu();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ItemViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }

    public ItemListAdapter(Context context, Fragment parent, List<ItemModel> dataset, ItemListFragment.LayoutListener listener, int workMode){
        this.context = context;
        this.parent = parent;
        this.listener = listener;
        this.dataset = dataset;
        this.workMode = workMode;
    }
}
