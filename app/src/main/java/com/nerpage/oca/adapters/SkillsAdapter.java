package com.nerpage.oca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.SkillsFragment;
import com.nerpage.oca.layouts.models.SkillModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillViewHolder> {
    private SkillsFragment.SkillLayoutChangeListener listener;
    public List<SkillModel> dataset;

    public static class SkillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeakReference<SkillsFragment.SkillLayoutChangeListener> listenerRef;
        private TextView skillSpentPointsCounter;
        private TextView skillTotalCounter;
        private TextView skillTitle;
        private ImageButton skillIncrementButton;
        private ImageButton skillDecrementButton;
        private ImageButton showDetailsButton;
        private ImageButton hideDetailsButton;
        private ConstraintLayout detailsContainer;
        private ImageView parentAttributeImage;
        private ImageButton rollDiceButton;
        private TextView criticalSuccessBoundaries;
        private TextView bigSuccessBoundaries;
        private TextView normalSuccessBoundaries;
        private TextView flowBoundaries;
        private TextView failureBoundaries;
        private TextView criticalFailureBoundaries;

        public SkillViewHolder(View v, SkillsFragment.SkillLayoutChangeListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);

            skillSpentPointsCounter = v.findViewById(R.id.skills_spentPointsCounter);
            skillTotalCounter = v.findViewById(R.id.skills_totalCounter);
            skillTitle = v.findViewById(R.id.skills_skillTitle);

            skillIncrementButton = v.findViewById(R.id.skills_incrementButton);
            skillDecrementButton = v.findViewById(R.id.skills_decrementButton);
            showDetailsButton = v.findViewById(R.id.skills_showDetailsButton);
            hideDetailsButton = v.findViewById(R.id.skills_hideDetailsButton);
            detailsContainer = v.findViewById(R.id.skills_skill_details_container);
            parentAttributeImage = v.findViewById(R.id.skills_parentAttributeImage);
            rollDiceButton = v.findViewById(R.id.skills_rollButton);
            criticalSuccessBoundaries = v.findViewById(R.id.skills_criticalSuccessBoundaries);
            bigSuccessBoundaries = v.findViewById(R.id.skills_bigSuccessBoundaries);
            normalSuccessBoundaries = v.findViewById(R.id.skills_normalSuccessBoundaries);
            flowBoundaries = v.findViewById(R.id.skills_flowBoundaries);
            failureBoundaries = v.findViewById(R.id.skills_failureBoundaries);
            criticalFailureBoundaries = v.findViewById(R.id.skills_criticalFailureBoundaries);

            skillIncrementButton.setOnClickListener(this);
            skillDecrementButton.setOnClickListener(this);
            showDetailsButton.setOnClickListener(this);
            hideDetailsButton.setOnClickListener(this);
            rollDiceButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){listenerRef.get().onClick(v, this.getLayoutPosition());}

        public void setDetailsVisible(){
            detailsContainer.setVisibility(View.VISIBLE);
            hideDetailsButton.setVisibility(View.VISIBLE);
            showDetailsButton.setVisibility(View.GONE);
        }

        public void setDetailsGone(){
            detailsContainer.setVisibility(View.GONE);
            hideDetailsButton.setVisibility(View.GONE);
            showDetailsButton.setVisibility(View.VISIBLE);
        }
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

        holder.skillSpentPointsCounter.setText(String.valueOf(model.getSpentPointsCounter()));
        holder.skillTotalCounter.setText(String.valueOf(model.getTotalCounter()));
        holder.skillTitle.setText(model.getTitle());
        holder.parentAttributeImage.setImageResource(model.getParentAttributeImageId());
        holder.criticalSuccessBoundaries.setText(model.getCriticalSuccessBoundaries());
        holder.bigSuccessBoundaries.setText(model.getBigSuccessBoundaries());
        holder.normalSuccessBoundaries.setText(model.getNormalSuccessBoundaries());
        holder.flowBoundaries.setText(model.getFlowBoundaries());
        holder.failureBoundaries.setText(model.getFailureBoundaries());
        holder.criticalFailureBoundaries.setText(model.getCriticalFailureBoundaries());
        if(model.areDetailsVisible())
            holder.setDetailsVisible();
        else
            holder.setDetailsGone();
    }

    @Override
    public int getItemCount(){return dataset.size();}
}
