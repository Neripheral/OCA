package com.example.oca;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oca.fragments.AttributesFragment;
import com.example.oca.models.AttributeModel;

import java.lang.ref.WeakReference;
import java.util.List;



public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.AttributeViewHolder> {
    private AttributesFragment.AttributeLayoutChangeListener listener;
    public List<AttributeModel> dataset;

    public static class AttributeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<AttributesFragment.AttributeLayoutChangeListener> listenerRef;
        private TextView attCounter;
        private TextView attTitle;
        private ImageView attImage;
        private ImageButton attIncrementButton;
        private ImageButton attDecrementButton;


        public AttributeViewHolder(View v, AttributesFragment.AttributeLayoutChangeListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);
            attCounter = (TextView) v.findViewById(R.id.attributes_attribute_counter);
            attTitle = (TextView) v.findViewById(R.id.attributes_attributeTitle);
            attCounter.addTextChangedListener(new TextWatcher(){
                public void afterTextChanged(Editable s){
                    listenerRef.get().onCounterChanged(attCounter, getLayoutPosition());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after){}

                public void onTextChanged(CharSequence s, int start, int before, int count){}
            });
            attImage = (ImageView) v.findViewById(R.id.attributes_attributeImage);

            attIncrementButton = (ImageButton) v.findViewById(R.id.attributes_attribute_incrementButton);
            attDecrementButton = (ImageButton) v.findViewById(R.id.attributes_attribute_decrementButton);

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
        holder.attCounter.setText("" + model.getCounter());
        holder.attTitle.setText(model.getTitle());
        holder.attImage.setImageResource(model.getImageId());
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }
}
