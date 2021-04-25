package com.nerpage.oca.layouts;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.adapters.BattlegroundActionAdapter;
import com.nerpage.oca.classes.LayoutHelper;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.events.Event;
import com.nerpage.oca.classes.events.EventController;
import com.nerpage.oca.classes.events.FlowFreezer;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.helpers.AnimationHelper;
import com.nerpage.oca.interfaces.listeners.OnRecyclerItemClicked;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.ArrayList;

public class BattlegroundLayoutHelper extends LayoutHelper implements EventController.EventReceiver, EventController.EventEmitter {
    //================================================================================
    // region //            POI

    public enum POI implements LayoutHelper.POI {
        ENEMY_CONTAINER_WITH_EFFECT(R.id.fighter_root),
        ENEMY_CONTAINER(R.id.fighter_container),
        ENEMY_TITLE(R.id.fighter_title),
        ENEMY_CURRENT_BLOOD(R.id.fighter_currentBlood),
        ENEMY_MAX_BLOOD(R.id.fighter_maxBlood),
        PC_CURRENT_BLOOD(R.id.battleground_pc_currentBlood),
        PC_MAX_BLOOD(R.id.battleground_pc_maxBlood),
        BEHAVIOR_NAVBAR(R.id.battleground_behavior_navbar),
        BEHAVIOR_SURRENDER_BUTTON(R.id.battleground_behavior_surrenderbtn),
        BEHAVIOR_ATTACK_BUTTON(R.id.battleground_behavior_attackbtn),
        BEHAVIOR_DEFEND_BUTTON(R.id.battleground_behavior_defendbtn),
        ACTIONS_RECYCLER(R.id.battleground_actions_recycler),
        ENEMY_EFFECT(R.id.fighter_effect_attack),
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
    // region //            Fields

    private EventController.EventListener eventFreezer;
    private BattlegroundViewModel model;
    private boolean stopModelUpdates = true;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private EventController.EventListener getEventFreezer() {
        return eventFreezer;
    }

    private BattlegroundLayoutHelper setEventFreezer(EventController.EventListener eventFreezer) {
        this.eventFreezer = eventFreezer;
        return this;
    }

    private BattlegroundViewModel getModel() {
        return model;
    }

    private BattlegroundLayoutHelper setModel(BattlegroundViewModel model) {
        this.model = model;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private RecyclerView findRecycler(){
        return (RecyclerView)getView(POI.ACTIONS_RECYCLER);
    }

    private void changeInfoBoxVisibility(RecyclerView.ViewHolder firstHolder, RecyclerView.ViewHolder lastHolder) {
        if(firstHolder == lastHolder){
            firstHolder.itemView.findViewById(R.id.action_info).setVisibility(View.VISIBLE);
        }else{
            firstHolder.itemView.findViewById(R.id.action_info).setVisibility(View.GONE);
            lastHolder.itemView.findViewById(R.id.action_info).setVisibility(View.GONE);
        }
    }

    private void forceViewUpdate(){
        updateText( POI.ENEMY_TITLE,            getModel().getEnemyTitle());
        updateText( POI.ENEMY_CURRENT_BLOOD,    String.valueOf(getModel().getEnemyCurrentBlood()));
        updateText( POI.ENEMY_MAX_BLOOD,        String.valueOf(getModel().getEnemyMaxBlood()));
        updateText( POI.PC_CURRENT_BLOOD,       String.valueOf(getModel().getPcCurrentBlood()));
        updateText( POI.PC_MAX_BLOOD,           String.valueOf(getModel().getPcMaxBlood()));

        BattlegroundActionAdapter adapter = ((BattlegroundActionAdapter) findRecycler().getAdapter());
        assert adapter != null;
        adapter.setDataset(new ArrayList<>(getModel().getPossibleActions()));
        adapter.notifyDataSetChanged();
    }

    private void updateViewDataWithModel(){
        if(stopModelUpdates)
            return;

        forceViewUpdate();
    }

    private void unfreezeFlow(){
        getEventFreezer().emitEvent(new FlowFreezer.ResumeFlow(this));
    }

    private BattlegroundLayoutHelper playEffect(POI poi, int resId, int duration, float scale, Runnable after){
        getView(poi).setScaleX(scale);
        getView(poi).setScaleY(scale);
        AnimationHelper.playCustomDurationAnimation((ImageView)getView(poi), resId, duration, after);
        return this;
    }

    private void highlightEnemyCard(Runnable after){
        Animator animation = AnimatorInflater.loadAnimator(getRoot().getContext(), R.animator.enemycard_highlight);
        animation.setInterpolator(new AnticipateOvershootInterpolator());
        animation.setTarget(getView(POI.ENEMY_CONTAINER_WITH_EFFECT));
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                after.run();
            }
        });
        animation.start();
    }

    private void unhighlightEnemyCard(Runnable after){
        Animator animation = AnimatorInflater.loadAnimator(getRoot().getContext(), R.animator.enemycard_highlight);
        animation.setInterpolator(new AnticipateOvershootInterpolator(){
            @Override
            public float getInterpolation(float input) {
                return Math.abs(super.getInterpolation(input) - 1f);
            }
        });
        animation.setTarget(getView(POI.ENEMY_CONTAINER_WITH_EFFECT));
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                after.run();
            }
        });
        animation.start();
    }

    private void shakeEnemyCard(){
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

    private void handleActionEvent(EntityPerformedActionEvent event){
        if(event.getAction() instanceof Action.HasEffectAnimation){
            getEventFreezer().emitEvent(new FlowFreezer.FreezeFlow(this));
            Action.HasEffectAnimation effect = ((Action.HasEffectAnimation)event.getAction());

            if(event.getAction().getTarget() instanceof PlayerCharacter)
                playEffect(
                    POI.PC_EFFECT,
                    effect.getEffectResId(),
                    effect.getEffectDuration(),
                    effect.getEffectScale(),
                    this::unfreezeFlow
                );
            else{
                stopModelUpdates = true;
                highlightEnemyCard(()-> {
                            playEffect(
                                    POI.ENEMY_EFFECT,
                                    effect.getEffectResId(),
                                    effect.getEffectDuration(),
                                    effect.getEffectScale(),
                                    () ->
                                            unhighlightEnemyCard(this::unfreezeFlow)
                            );
                            shakeEnemyCard();
                            forceViewUpdate();
                        }
                );
            }
        }
    }

    // endregion //         Private methods
    //================================================================================

    public BattlegroundLayoutHelper updateInfoBoxVisibility(){
        LinearLayoutManager manager = ((LinearLayoutManager)findRecycler().getLayoutManager());
        assert manager != null;

        RecyclerView.ViewHolder firstHolder =
                findRecycler().findViewHolderForLayoutPosition(
                        manager.findFirstVisibleItemPosition());
        RecyclerView.ViewHolder lastHolder =
                findRecycler().findViewHolderForLayoutPosition(
                        manager.findLastVisibleItemPosition());

        if(firstHolder != null && lastHolder != null)
            changeInfoBoxVisibility(firstHolder, lastHolder);

        return this;
    }

    public BattlegroundLayoutHelper updateView(BattlegroundViewModel model){
        setModel(model);
        return this;
    }

    public BattlegroundLayoutHelper(View rootView,
                                    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener,
                                    OnRecyclerItemClicked onRecyclerItemClicked,
                                    RecyclerView.OnScrollListener onScrollListener){
        super(rootView);
        findRecycler().setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        findRecycler().setAdapter(new BattlegroundActionAdapter(onRecyclerItemClicked));
        findRecycler().addOnScrollListener(onScrollListener);
        (new LinearSnapHelper()).attachToRecyclerView(findRecycler());

        ((BottomNavigationView)getView(BattlegroundLayoutHelper.POI.BEHAVIOR_NAVBAR))
                .setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    public void onEventReceived(Event event) {
        stopModelUpdates = false;
        if(event.getClass() == EntityPerformedActionEvent.class){
            handleActionEvent((EntityPerformedActionEvent)event);
        }
        updateViewDataWithModel();
        updateInfoBoxVisibility();
        stopModelUpdates = true;
    }

    @Override
    public void setEventListener(EventController.EventListener eventListener) {
        this.setEventFreezer(eventListener);
    }
}
