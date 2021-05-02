package com.nerpage.oca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.AttributesFragment;
import com.nerpage.oca.layouts.models.AttributeModel;

import java.lang.ref.WeakReference;
import java.util.List;



public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.AttributeViewHolder> {
    private AttributesFragment.AttributeLayoutChangeListener listener;
    public List<AttributeModel> dataset;

    public static class AttributeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<AttributesFragment.AttributeLayoutChangeListener> listenerRef;
        private View attRoot;
        private TextView attCounter;
        private TextView attTitle;
        private TextView attDescription;
        private TextView attCounterDescription;
        private ImageView attImage;
        private ImageButton attIncrementButton;
        private ImageButton attDecrementButton;
        private ImageView attParentImage;
        private TextView attBalance;


        public AttributeViewHolder(View v, AttributesFragment.AttributeLayoutChangeListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);
            attRoot = v;
            attCounter = v.findViewById(R.id.attributes_attribute_counter);
            attTitle = v.findViewById(R.id.attributes_attributeTitle);
            attDescription = v.findViewById(R.id.attributes_attributeDescription);
            attCounterDescription = v.findViewById(R.id.attributes_attributeCounterDescription);
            attImage = v.findViewById(R.id.attributes_attributeImage);
            attParentImage = v.findViewById(R.id.attributes_attributeParent);
            attBalance = v.findViewById(R.id.attributes_attributeBalance);

            attIncrementButton = v.findViewById(R.id.attributes_attribute_incrementButton);
            attDecrementButton = v.findViewById(R.id.attributes_attribute_decrementButton);

            attIncrementButton.setOnClickListener(this);
            attDecrementButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            listenerRef.get().onClick(v, this.getLayoutPosition());
        }
    }

    public AttributeAdapter(List<AttributeModel> dataset, AttributesFragment.AttributeLayoutChangeListener listener){
        this.dataset = dataset;
        this.listener = listener;
    }

    @Override
    public AttributeAdapter.AttributeViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attribute_listitem, parent, false);
        AttributeViewHolder vh = new AttributeViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(AttributeViewHolder holder, int position){
        // get specific model from dataset
        AttributeModel model = dataset.get(position);

        // update holder with fresh data
        holder.attCounter.setText(String.valueOf(model.getCounter()));
        holder.attTitle.setText(model.getTitle());
        holder.attDescription.setText(model.getDescription());
        holder.attCounterDescription.setText(model.getCounterDescription());
        holder.attImage.setImageResource(model.getImageId());

        // what happens whether it is a main attribute or child
        holder.attParentImage.setImageResource(model.getParentImageId());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.attRoot.getLayoutParams();
        if(model.getParentImageId() != 0) {
            params.leftMargin = 64;
            holder.attBalance.setText(String.valueOf(model.getBalance()));
        } else {
            params.leftMargin = 0;
            holder.attBalance.setText("");
        }
        holder.attBalance.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), model.getBalanceColor()));

        // what happens when user committed his attribute changes
        if(model.isCommitted()){
            holder.attBalance.setVisibility(View.GONE);
            holder.attDecrementButton.setVisibility(View.GONE);
            holder.attIncrementButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }
}
