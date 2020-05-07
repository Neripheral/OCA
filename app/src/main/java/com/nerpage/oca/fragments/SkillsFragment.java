package com.nerpage.oca.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.CharacterViewerActivity;
import com.nerpage.oca.R;
import com.nerpage.oca.RecyclerViewClickListener;
import com.nerpage.oca.SkillsAdapter;
import com.nerpage.oca.SpacesItemDecoration;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.Skill;
import com.nerpage.oca.models.SkillModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SkillsFragment extends Fragment {
    public View rootView = null;

    /**
     * Subclass being the bridge between this fragment and resource-layout file
     */
    public static final class Layout{
        public static RecyclerView getRecycler(View rootView){
            return (RecyclerView) rootView.findViewById(R.id.skills_recycler);
        }

        public static SkillsAdapter.SkillViewHolder getHolderOnPosition(View rootView, int position){
            return (SkillsAdapter.SkillViewHolder)Layout.getRecycler(rootView).findViewHolderForLayoutPosition(position);
        }

        public static TextView getBalanceCounter(View rootView){
            return (TextView) rootView.findViewById(R.id.skills_balance);
        }
    }

    public interface SkillLayoutChangeListener extends RecyclerViewClickListener {}


    /**
     * constructor
     */
    public SkillsFragment(){
        //Empty constructor
    }

    public void updateBalanceCounter(){
        int balance = getPlayerCharacterData().getSkillsBalance();
        Layout.getBalanceCounter(rootView).setText(String.valueOf(balance));
    }

    public void onRollDiceButtonClicked(int position){
        int total = getDataset().get(position).getTotalCounter();
        Random rng = new Random();
        int rolledNumber = rng.nextInt(100)+1;

        String title = "missingTitle";
        String message = String.valueOf(rolledNumber) + " / " + String.valueOf(total);
        double ratio = (double)rolledNumber / (double)total;
        if(ratio < 0.1)
            title = getString(R.string.criticalSuccess);
        else if(ratio < 0.5)
            title = getString(R.string.bigSuccess);
        else if(ratio < 1)
            title = getString(R.string.success);
        else if(ratio == 1)
            title = getString(R.string.successFlow);
        else {
            title = getString(R.string.failure);
        }

        if(rolledNumber == 100)
            title = getString(R.string.criticalFailure);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clickOperator(View v, int position){
        switch(v.getId()){
            case R.id.skills_incrementButton:
                this.onIncrementButtonClicked(position);
                break;
            case R.id.skills_decrementButton:
                this.onDecrementButtonClicked(position);
                break;
            case R.id.skills_showDetailsButton:
                this.onShowDetailsButtonClicked(position);
                break;
            case R.id.skills_hideDetailsButton:
                this.onHideDetailsButtonClicked(position);
                break;
            case R.id.skills_rollButton:
                this.onRollDiceButtonClicked(position);
        }
    }

    public SkillsAdapter getAdapter(){
        return ((SkillsAdapter)(Layout.getRecycler(rootView).getAdapter()));
    }

    public int addToPCSkill(int position, int numberToAdd){
        SkillModel model = getAdapter().dataset.get(position);
        PlayerCharacter pc = getPlayerCharacterData();
        pc.setSkill(model.getId(), pc.getSkill(model.getId()).getSpentPoints() + numberToAdd);
        return pc.getSkill(model.getId()).getSpentPoints();
    }

    public void updateRecyclerHolder(int position) {
        SkillsAdapter adapter = getAdapter();

        SkillModel oldModel = adapter.dataset.get(position);
        String id = oldModel.getId();
        Skill skill = getPlayerCharacterData().getSkill(id);

        SkillModel newModel = composeDatasetEntryFor(skill);
        newModel.setDetailsVisible(oldModel.areDetailsVisible());

        adapter.dataset.set(position, newModel);
        adapter.notifyItemChanged(position);
        updateBalanceCounter();
    }

    public void onIncrementButtonClicked(int position){
        addToPCSkill(position, 1);
        updateRecyclerHolder(position);
    }

    public void onDecrementButtonClicked(int position){
        addToPCSkill(position, -1);
        updateRecyclerHolder(position);
    }

    public void onShowDetailsButtonClicked(int position){
        Layout.getHolderOnPosition(rootView, position).setDetailsVisible();
        getAdapter().dataset.get(position).setDetailsVisible(true);
    }

    public void onHideDetailsButtonClicked(int position){
        Layout.getHolderOnPosition(rootView, position).setDetailsGone();
        getAdapter().dataset.get(position).setDetailsVisible(false);
    }

    public PlayerCharacter getPlayerCharacterData(){
        return ((CharacterViewerActivity) getActivity()).pc;
    }

    public SkillModel composeDatasetEntryFor(Skill skill){
        List<List<Integer>> boundaries = skill.getSkillThrowResultBoundaries(getPlayerCharacterData());
        return new SkillModel(skill.getTotalCounter(getPlayerCharacterData()),
                skill.getSpentPoints(),
                skill.getId(),
                skill.getTitle(getContext()),
                getPlayerCharacterData().getAttributeImageId(skill.getParentAttribute()),
                String.valueOf(boundaries.get(Skill.CRITICAL_SUCCESS).get(0)) + " - " + boundaries.get(Skill.CRITICAL_SUCCESS).get(1),
                boundaries.get(Skill.BIG_SUCCESS).get(0) + " - " + boundaries.get(Skill.BIG_SUCCESS).get(1),
                boundaries.get(Skill.NORMAL_SUCCESS).get(0) + " - " + boundaries.get(Skill.NORMAL_SUCCESS).get(1),
                String.valueOf(boundaries.get(Skill.FLOW).get(0)),
                boundaries.get(Skill.FAILURE).get(0) + " - " + boundaries.get(Skill.FAILURE).get(1),
                String.valueOf(boundaries.get(Skill.CRITICAL_FAILURE).get(0))
                );
    }

    public List<SkillModel> getDataset(){
        List<SkillModel> dataset = new ArrayList<>();

        Map<String, Skill> skills = getPlayerCharacterData().getSkills();
        for(Skill skill : skills.values()){
            dataset.add(composeDatasetEntryFor(skill));
        }

        Collections.sort(dataset);
        return dataset;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // create the foundation for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_skills, container, false);

        // get the recycler
        RecyclerView recyclerView = Layout.getRecycler(this.rootView);

        // tell the recycler that it shall be one dimensional
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new SkillsAdapter(getDataset(), new SkillLayoutChangeListener(){
            @Override
            public void onClick(View view, int position){
                clickOperator(view, position);
            }
        });
        recyclerView.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        updateBalanceCounter();
        return this.rootView;
    }
}