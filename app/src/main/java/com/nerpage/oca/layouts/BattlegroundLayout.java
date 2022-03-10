package com.nerpage.oca.layouts;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.adapters.BattlegroundActionAdapter;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.events.Event;
import com.nerpage.oca.classes.events.EventController;
import com.nerpage.oca.classes.events.FlowFreezer;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.helpers.AnimationHelper;
import com.nerpage.oca.fragments.presenters.BattlegroundPresenter;
import com.nerpage.oca.interfaces.listeners.OnRecyclerItemClicked;
import com.nerpage.oca.layouts.models.BattlegroundViewModel;

import java.util.ArrayList;

public class BattlegroundLayout extends Layout<BattlegroundViewModel> implements EventController.EventReceiver, EventController.EventEmitter {
    //================================================================================
    // region //            POI

    public enum POI implements Layout.POI {
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
    // region //            Fields

    private BattlegroundPresenter p;

    //TODO: FlowFreezer should be a child class of EventController with specific utility methods
    private EventController.EventListener eventFreezer;

    private boolean stopModelUpdates = true;
    private FighterCardLayout enemyLayout;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private EventController.EventListener getEventFreezer() {
        return eventFreezer;
    }

    private BattlegroundLayout setEventFreezer(EventController.EventListener eventFreezer) {
        this.eventFreezer = eventFreezer;
        return this;
    }

    private FighterCardLayout getEnemyLayout() {
        return enemyLayout;
    }

    private BattlegroundLayout setEnemyLayout(FighterCardLayout enemyLayout) {
        this.enemyLayout = enemyLayout;
        return this;
    }

    @Override
    public Layout<BattlegroundViewModel> setModel(BattlegroundViewModel model) {
        getEnemyLayout().setModel(model.getEnemyCard());
        return super.setModel(model);
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private RecyclerView findRecycler(){
        return p.getRecycler();
    }

    private void changeInfoBoxVisibility(RecyclerView.ViewHolder firstHolder, RecyclerView.ViewHolder lastHolder) {
        //TODO: ActionCardFragment should be responsible for this
        if(firstHolder == lastHolder){
            firstHolder.itemView.findViewById(R.id.action_info).setVisibility(View.VISIBLE);
        }else{
            firstHolder.itemView.findViewById(R.id.action_info).setVisibility(View.GONE);
            lastHolder.itemView.findViewById(R.id.action_info).setVisibility(View.GONE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void forceViewUpdate(){
        p.updatePCCurrentBlood(String.valueOf(getModel().getPcCurrentBlood()));
        p.updatePCMaxBlood(String.valueOf(getModel().getPcMaxBlood()));
        getEnemyLayout().updateViewData();

        //TODO: adapter should be stored as class field!
        BattlegroundActionAdapter adapter = ((BattlegroundActionAdapter) p.getRecycler().getAdapter());
        assert adapter != null;
        adapter.setDataset(new ArrayList<>(getModel().getPossibleActions()));
        adapter.notifyDataSetChanged();
    }

    private void unfreezeFlow(){
        getEventFreezer().emitEvent(new FlowFreezer.ResumeFlow(this));
    }

    private void freezeFlow(){
        getEventFreezer().emitEvent(new FlowFreezer.FreezeFlow(this));
    }

    private void highlightEnemyCard(Runnable after){
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

    private void unhighlightEnemyCard(Runnable after){
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
            freezeFlow();
            Action.HasEffectAnimation effect = ((Action.HasEffectAnimation)event.getAction());

            if(event.getAction().getTarget() instanceof PlayerCharacter)
                p.playEffectOnPC(
                    effect.getEffectResId(),
                    effect.getEffectDuration(),
                    effect.getEffectScale(),
                    this::unfreezeFlow
                );
            else{
                stopModelUpdates = true;
                highlightEnemyCard(()-> {
                            getEnemyLayout().playEffect(
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

    @Override
    public void updateViewData(){
        if(stopModelUpdates)
            return;

        forceViewUpdate();
    }

    public BattlegroundLayout updateInfoBoxVisibility(){
        //TODO: separate fragment should be responsible for handling recycler
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

    public BattlegroundLayout(View rootView,
                              BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener,
                              OnRecyclerItemClicked onRecyclerItemClicked,
                              RecyclerView.OnScrollListener onScrollListener){
        super(rootView);

        p = new BattlegroundPresenter();
        p.setRoot(rootView);

        setEnemyLayout(new FighterCardLayout(getView(POI.ENEMY_CONTAINER)));
        findRecycler().setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        findRecycler().setAdapter(new BattlegroundActionAdapter(onRecyclerItemClicked));
        findRecycler().addOnScrollListener(onScrollListener);
        (new LinearSnapHelper()).attachToRecyclerView(findRecycler());

        ((BottomNavigationView)getView(BattlegroundLayout.POI.BEHAVIOR_NAVBAR))
                .setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    public void onEventReceived(Event event) {
        stopModelUpdates = false;
        if(event.getClass() == EntityPerformedActionEvent.class){
            handleActionEvent((EntityPerformedActionEvent)event);
        }
        updateViewData();
        updateInfoBoxVisibility();
        stopModelUpdates = true;
    }

    @Override
    public void setEventListener(EventController.EventListener eventListener) {
        this.setEventFreezer(eventListener);
    }
}
