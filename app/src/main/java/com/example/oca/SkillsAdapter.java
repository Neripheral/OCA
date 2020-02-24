package com.example.oca;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oca.classes.PlayerCharacter;
import com.example.oca.models.SkillModel;

import java.lang.ref.WeakReference;
import java.util.List;

class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillViewHolder> {
    private SkillsFragment.SkillLayoutChangeListener listener;
    public List<SkillModel> dataset;

    public static class SkillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<SkillsFragment.SkillLayoutChangeListener> listenerRef;
        private TextView skillCounter;
        private TextView skillTitle;
        private ImageButton skillIncrementButton;
        private ImageButton skillDecrementButton;


        public SkillViewHolder(View v, SkillsFragment.SkillLayoutChangeListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);

            skillCounter = (TextView) v.findViewById(R.id.skills_counter);
            skillTitle = (TextView) v.findViewById(R.id.skills_skillTitle);

            skillIncrementButton = (ImageButton) v.findViewById(R.id.skills_incrementButton);
            skillDecrementButton = (ImageButton) v.findViewById(R.id.skills_decrementButton);

            skillIncrementButton.setOnClickListener(this);
            skillDecrementButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){listenerRef.get().onClick(v, this.getLayoutPosition());}
    }

    public SkillsAdapter(List<SkillModel> dataset, SkillsFragment.SkillLayoutChangeListener listener) {
        this.dataset = dataset;
        this.listener = listener;
    }

    @Override
    public SkillsAdapter.SkillViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_listitem, parent, false);
        SkillViewHolder vh = new SkillViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(SkillViewHolder holder, int position){
        // get specific model from dataset
        SkillModel model = dataset.get(position);

        // update holder with fresh data
        holder.skillCounter.setText("" + model.getCounter());
        holder.skillTitle.setText(model.getTitle());
    }

    @Override
    public void onViewAttachedToWindow(final SkillViewHolder holder){
        holder.skillCounter.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
                listener.onCounterChanged(holder.skillCounter, holder.getLayoutPosition());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }

    @Override
    public int getItemCount(){return dataset.size();}
}
