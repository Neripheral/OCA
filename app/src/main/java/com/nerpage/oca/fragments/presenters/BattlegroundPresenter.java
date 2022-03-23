package com.nerpage.oca.fragments.presenters;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.fragments.Presenter;
import com.nerpage.oca.layouts.BattlegroundLayout;

public class BattlegroundPresenter extends Presenter {
    //================================================================================
    // region //            POI

    public enum POI implements Presenter.POI {
        ENEMY_CONTAINER(R.id.enemy_include),
        PC_CURRENT_BLOOD(R.id.battleground_pc_currentBlood),
        PC_MAX_BLOOD(R.id.battleground_pc_maxBlood),
        BEHAVIOR_NAVBAR(R.id.battleground_behavior_navbar),
        BEHAVIOR_SURRENDER_BUTTON(R.id.battleground_behavior_surrenderbtn),
        BEHAVIOR_ATTACK_BUTTON(R.id.battleground_behavior_attackbtn),
        BEHAVIOR_DEFEND_BUTTON(R.id.battleground_behavior_defendbtn),
        ACTIONS_RECYCLER(R.id.battleground_actions_recycler),
        PC_EFFECT(R.id.battleground_pc_effect);

        int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id){
            this.id = id;
        }
    }

    // endregion //         POI
    //================================================================================
    //================================================================================
    // region //            Private methods

    private void changeInfoBoxVisibility(RecyclerView.ViewHolder firstHolder, RecyclerView.ViewHolder lastHolder) {
        //TODO: ActionCardFragment should be responsible for this
        if(firstHolder == lastHolder){
            firstHolder.itemView.findViewById(R.id.action_info).setVisibility(View.VISIBLE);
        }else{
            firstHolder.itemView.findViewById(R.id.action_info).setVisibility(View.GONE);
            lastHolder.itemView.findViewById(R.id.action_info).setVisibility(View.GONE);
        }
    }

    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public RecyclerView getRecycler(){
        return (RecyclerView) getView(POI.ACTIONS_RECYCLER);
    }

    //TODO: separate fragment should be responsible for handling recycler
    public void updateInfoBoxVisibility(){
        LinearLayoutManager manager = ((LinearLayoutManager)getRecycler().getLayoutManager());
        assert manager != null;

        RecyclerView.ViewHolder firstHolder =
                getRecycler().findViewHolderForLayoutPosition(
                        manager.findFirstVisibleItemPosition());
        RecyclerView.ViewHolder lastHolder =
                getRecycler().findViewHolderForLayoutPosition(
                        manager.findLastVisibleItemPosition());

        if(firstHolder != null && lastHolder != null)
            changeInfoBoxVisibility(firstHolder, lastHolder);
    }

    public BottomNavigationView getBehaviorNavbar(){
        return ((BottomNavigationView)getView(POI.BEHAVIOR_NAVBAR));
    }

    public void updatePCCurrentBlood(String newCurrentBlood){
        updateText(POI.PC_CURRENT_BLOOD, newCurrentBlood);
    }

    public void updatePCMaxBlood(String newMaxBlood){
        updateText(POI.PC_MAX_BLOOD, newMaxBlood);
    }

    public void playEffectOnPC(int resId, int duration, float scale, Runnable after) {
        playEffect(POI.PC_EFFECT, resId, duration, scale, after);
    }

    public void highlightEnemyCard(Runnable after){
        Animator animation = AnimatorInflater.loadAnimator(getRoot().getContext(), R.animator.enemycard_highlight);
        animation.setInterpolator(new AnticipateOvershootInterpolator());
        animation.setTarget(getView(POI.ENEMY_CONTAINER));
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                after.run();
            }
        });
        animation.start();
    }

    public void unhighlightEnemyCard(Runnable after){
        Animator animation = AnimatorInflater.loadAnimator(getRoot().getContext(), R.animator.enemycard_highlight);
        animation.setInterpolator(new AnticipateOvershootInterpolator(){
            @Override
            public float getInterpolation(float input) {
                return Math.abs(super.getInterpolation(input) - 1f);
            }
        });
        animation.setTarget(getView(POI.ENEMY_CONTAINER));
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                after.run();
            }
        });
        animation.start();
    }

    public void shakeEnemyCard(){
        Animator animation = AnimatorInflater.loadAnimator(getRoot().getContext(), R.animator.enemycard_attackshake);
        animation.setTarget(getView(POI.ENEMY_CONTAINER));
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getView(POI.ENEMY_CONTAINER).setRotation(0);
            }
        });
        animation.start();
    }

    // endregion //         Interface
    //================================================================================
}
