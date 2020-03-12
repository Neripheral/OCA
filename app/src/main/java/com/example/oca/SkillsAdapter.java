package com.example.oca;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oca.fragments.SkillsFragment;
import com.example.oca.models.SkillModel;

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

            skillSpentPointsCounter = (TextView) v.findViewById(R.id.skills_spentPointsCounter);
            skillTotalCounter = (TextView) v.findViewById(R.id.skills_totalCounter);
            skillTitle = (TextView) v.findViewById(R.id.skills_skillTitle);

            skillIncrementButton = (ImageButton) v.findViewById(R.id.skills_incrementButton);
            skillDecrementButton = (ImageButton) v.findViewById(R.id.skills_decrementButton);
            showDetailsButton = (ImageButton) v.findViewById(R.id.skills_showDetailsButton);
            hideDetailsButton = (ImageButton) v.findViewById(R.id.skills_hideDetailsButton);
            detailsContainer = (ConstraintLayout) v.findViewById(R.id.skills_skill_details_container);
            parentAttributeImage = (ImageView) v.findViewById(R.id.skills_parentAttributeImage);
            rollDiceButton = (ImageButton) v.findViewById(R.id.skills_rollButton);
            criticalSuccessBoundaries = (TextView) v.findViewById(R.id.skills_criticalSuccessBoundaries);
            bigSuccessBoundaries = (TextView) v.findViewById(R.id.skills_bigSuccessBoundaries);
            normalSuccessBoundaries = (TextView) v.findViewById(R.id.skills_normalSuccessBoundaries);
            flowBoundaries = (TextView) v.findViewById(R.id.skills_flowBoundaries);
            failureBoundaries = (TextView) v.findViewById(R.id.skills_failureBoundaries);
            criticalFailureBoundaries = (TextView) v.findViewById(R.id.skills_criticalFailureBoundaries);

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
