package com.nerpage.oca.fragments.presenters;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.fragments.ActionsRecyclerFragment;
import com.nerpage.oca.fragments.FighterCardFragment;
import com.nerpage.oca.pac.AbstractPresenter;

public class BattlegroundPresenter extends AbstractPresenter {
    //================================================================================
    // region //            POI

    public enum POI implements PointOfInterest {
        ENEMY_CONTAINER(R.id.battleground_enemy_frame),
        PC_CURRENT_BLOOD(R.id.battleground_pc_currentBlood),
        PC_MAX_BLOOD(R.id.battleground_pc_maxBlood),
        BEHAVIOR_NAVBAR(R.id.battleground_behavior_navbar),
        BEHAVIOR_SURRENDER_BUTTON(R.id.battleground_behavior_surrenderbtn),
        BEHAVIOR_ATTACK_BUTTON(R.id.battleground_behavior_attackbtn),
        BEHAVIOR_DEFEND_BUTTON(R.id.battleground_behavior_defendbtn),
        ACTIONS_RECYCLER_FRAME(R.id.battleground_actionsRecycler_frame),
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



    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public @NonNull FighterCardFragment findFighterCardFragment(FragmentManager fm){
        FighterCardFragment fragment = (FighterCardFragment) fm.findFragmentByTag("battleground_enemy");
        assert fragment != null;
        return fragment;
    }

    public View getEnemyFrame(){
        return getView(POI.ENEMY_CONTAINER);
    }

    public @NonNull ActionsRecyclerFragment findActionRecyclerFragment(FragmentManager fm){
        ActionsRecyclerFragment fragment = (ActionsRecyclerFragment) fm.findFragmentByTag("battleground_actionsRecycler");
        assert fragment != null;
        return fragment;
    }

    public View getActionsRecyclerFrame(){
        return getView(POI.ACTIONS_RECYCLER_FRAME);
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
    //================================================================================
    // region //            Constructor

    public BattlegroundPresenter(View root){
        super(root);
    }

    // endregion //         Constructor
    //================================================================================
}
