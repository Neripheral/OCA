package com.nerpage.oca.layouts;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.adapters.BattlegroundActionAdapter;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.events.Event;
import com.nerpage.oca.classes.events.EventController;
import com.nerpage.oca.classes.events.FlowController;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.events.EntityPerformedActionEvent;
import com.nerpage.oca.fragments.FighterCardFragment;
import com.nerpage.oca.fragments.presenters.BattlegroundPresenter;
import com.nerpage.oca.interfaces.listeners.OnRecyclerItemClicked;
import com.nerpage.oca.layouts.models.BattlegroundViewModel;

import java.util.ArrayList;

public class BattlegroundLayout extends Layout<BattlegroundViewModel> implements EventController.EventReceiver, EventController.EventEmitter {
    //================================================================================
    // region //            Fields

    private BattlegroundPresenter p;

    //TODO: FlowFreezer should be a child class of EventController with specific utility methods
    private EventController.EventListener eventFreezer;


    private FighterCardFragment fighterCardFragment;
    private boolean stopModelUpdates = true;

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

    @Override
    public Layout<BattlegroundViewModel> setModel(BattlegroundViewModel model) {
        fighterCardFragment.updateData(model.getEnemyCard());
        return super.setModel(model);
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private RecyclerView findRecycler(){
        return p.getRecycler();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void forceViewUpdate(){
        p.updatePCCurrentBlood(String.valueOf(getModel().getPcCurrentBlood()));
        p.updatePCMaxBlood(String.valueOf(getModel().getPcMaxBlood()));
        fighterCardFragment.updatePresentation();

        //TODO: adapter should be stored as class field!
        BattlegroundActionAdapter adapter = ((BattlegroundActionAdapter) p.getRecycler().getAdapter());
        assert adapter != null;
        adapter.setDataset(new ArrayList<>(getModel().getPossibleActions()));
        adapter.notifyDataSetChanged();
    }

    private void unfreezeFlow(){
        getEventFreezer().emitEvent(new FlowController.StartFlow(this));
    }

    private void freezeFlow(){
        getEventFreezer().emitEvent(new FlowController.StopFlow(this));
    }

    private void onRecyclerScrolled(){
        p.updateInfoBoxVisibility();
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
                p.highlightEnemyCard(()-> {
                            fighterCardFragment.playEffect(
                                    effect.getEffectResId(),
                                    effect.getEffectDuration(),
                                    effect.getEffectScale(),
                                    () ->
                                            p.unhighlightEnemyCard(this::unfreezeFlow)
                            );
                            p.shakeEnemyCard();
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

    public BattlegroundLayout(View rootView,
                              BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener,
                              OnRecyclerItemClicked onRecyclerItemClicked,
                              FighterCardFragment newFighterCardFragment){
        super(rootView);

        p = new BattlegroundPresenter();
        p.setRoot(rootView);

        fighterCardFragment = newFighterCardFragment;

        findRecycler().setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        findRecycler().setAdapter(new BattlegroundActionAdapter(onRecyclerItemClicked));
        findRecycler().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                onRecyclerScrolled();
            }
        });
        (new LinearSnapHelper()).attachToRecyclerView(findRecycler());

        p.getBehaviorNavbar().setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    public void onEventReceived(Event event) {
        stopModelUpdates = false;
        if(event.getClass() == EntityPerformedActionEvent.class){
            handleActionEvent((EntityPerformedActionEvent)event);
        }
        updateViewData();
        p.updateInfoBoxVisibility();
        stopModelUpdates = true;
    }

    @Override
    public void setEventListener(EventController.EventListener eventListener) {
        this.setEventFreezer(eventListener);
    }
}
