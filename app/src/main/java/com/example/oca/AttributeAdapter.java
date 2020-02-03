package com.example.oca;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oca.fragments.AttributesFragment;

import java.lang.ref.WeakReference;
import java.util.List;



public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.AttributeViewHolder> {
    private AttributesFragment.RecyclerViewClickListener listener;
    public List<AttributeModel> dataset;

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    public static class AttributeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<AttributesFragment.RecyclerViewClickListener> listenerRef;
        private TextView attCounter;
        private TextView attTitle;
        private ImageView attImage;
        private ImageButton attIncrementButton;
        private ImageButton attDecrementButton;


        public AttributeViewHolder(View v, AttributesFragment.RecyclerViewClickListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);
            attCounter = (TextView) v.findViewById(R.id.attributes_attribute_counter);
            attTitle = (TextView) v.findViewById(R.id.attributes_attributeTitle);
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

    public AttributeAdapter(List<AttributeModel> dataset, AttributesFragment.RecyclerViewClickListener listener){
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
        holder.attCounter.setText("" + model.counter);
        holder.attTitle.setText(model.title);
        holder.attImage.setImageResource(model.imageId);
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }
}
