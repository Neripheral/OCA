package com.nerpage.oca.fragments.battleground_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.events.Event;
import com.nerpage.oca.classes.events.EventController;
import com.nerpage.oca.classes.events.FlowController;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.fighting.phases.ActiveFighterAwaitingActionPhase;
import com.nerpage.oca.fragments.ActionsRecyclerFragment;
import com.nerpage.oca.fragments.FighterCardFragment;
import com.nerpage.oca.fragments.PACFragment;
import com.nerpage.oca.fragments.models.BattlegroundModel;
import com.nerpage.oca.fragments.presenters.BattlegroundPresenter;

import java.util.List;

public class BattlegroundFragment extends PACFragment<BattlegroundModel, BattlegroundPresenter> implements EventController.EventReceiver, EventController.EventEmitter {
    //================================================================================
    // region //            Fields

    private FighterCardFragment fighterCardFragment;
    private ActionsRecyclerFragment actionsRecyclerFragment;
    private FightManager fightManager;
    private Action nextAction = null;
    private boolean playerTurn = false;
    private FlowController.FlowHelper flowHelper;

    //TODO: dirty af, clean asap
    private boolean stopModelUpdates = true;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private FightManager getFightManager() {
        return this.fightManager;
    }

    private BattlegroundFragment setFightManager(FightManager fightManager) {
        this.fightManager = fightManager;
        return this;
    }

    private Action getNextAction() {
        return nextAction;
    }

    private BattlegroundFragment setNextAction(Action nextAction) {
        this.nextAction = nextAction;
        return this;
    }

    private boolean isPlayerTurn() {
        return playerTurn;
    }

    private BattlegroundFragment setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private Methods

    private void onPlayersTurn(Fighter host, List<Fighter> others, FightingBehavior.ActionSelectedListener actionSelectedListener){
        //TODO: what to do when it's players turn
        actionSelectedListener.passSelectedAction(host, getNextAction());
    }

    private void updateModel(){
        m.pcCurrentBlood = getFightManager().getPcFighter().getEntity().getBlood();
        m.pcMaxBlood = getFightManager().getPcFighter().getEntity().getMaxBlood();
        actionsRecyclerFragment.updateModel(getFightManager().getPcFighter().getEntity().getPossibleActions());

        fighterCardFragment.updateModel(getFightManager().getParticipantsExceptForPc().get(0));
    }

    private PlayerCharacter getPlayerCharacter(){
        return ((CharacterEditorActivity)requireActivity()).getPc();
    }

    private void enrollFighters(){
        // enroll player
        getFightManager().enrollPlayer(getPlayerCharacter(), this::onPlayersTurn);

        // enroll enemy
        getFightManager().enrollFighter(EnemyGenerator.generateRandomEnemy(), 0);
    }

    private void onActionItemClicked(int position){
        Action actionToPerform = getPlayerCharacter().getPossibleActions().get(position);
        actionToPerform.setSource(getPlayerCharacter());
        actionToPerform.setTarget(getFightManager().getParticipantsExceptForPc().get(0).getEntity());

        setNextAction(actionToPerform);
        if(isPlayerTurn()){
            setPlayerTurn(false);
            flowHelper.startFlow();
        }
    }

    private void initView(){
        //TODO: when POIs are available remove referencing by R
        fighterCardFragment = (FighterCardFragment) getChildFragmentManager().findFragmentById(R.id.enemy_include);

        actionsRecyclerFragment.injectRoot(p.getActionsRecyclerFrame());
        actionsRecyclerFragment.registerCallback(new ActionsRecyclerFragment.Callback() {
            @Override
            public void tellActionItemWasClicked(int position) {
                onActionItemClicked(position);
            }
        });
        actionsRecyclerFragment.initRecyclerView();

        p.getBehaviorNavbar().setOnNavigationItemSelectedListener(BehaviorHelper::onBehaviorItemSelected);
    }

    //TODO: this whole method looks fishy and dirty
    @SuppressLint("NotifyDataSetChanged")
    private void forceViewUpdate(){
        p.updatePCCurrentBlood(String.valueOf(m.pcCurrentBlood));
        p.updatePCMaxBlood(String.valueOf(m.pcMaxBlood));
        fighterCardFragment.updatePresentation();
        actionsRecyclerFragment.updatePresentation();
    }

    public void updateView(){
        if(stopModelUpdates)
            return;

        forceViewUpdate();
    }

    private void handleActionEvent(EntityPerformedActionEvent event){
        if(event.getAction() instanceof Action.HasEffectAnimation){
            flowHelper.stopFlow();
            Action.HasEffectAnimation effect = ((Action.HasEffectAnimation)event.getAction());

            if(event.getAction().getTarget() instanceof PlayerCharacter)
                p.playEffectOnPC(
                        effect.getEffectResId(),
                        effect.getEffectDuration(),
                        effect.getEffectScale(),
                        flowHelper::startFlow
                );
            else{
                stopModelUpdates = true;
                p.highlightEnemyCard(()-> {
                            fighterCardFragment.playEffect(
                                    effect.getEffectResId(),
                                    effect.getEffectDuration(),
                                    effect.getEffectScale(),
                                    () ->
                                            p.unhighlightEnemyCard(flowHelper::startFlow)
                            );
                            p.shakeEnemyCard();
                            forceViewUpdate();
                        }
                );
            }
        }
    }

    // endregion //         Private Methods
    //================================================================================
    //================================================================================
    // region //            Public Methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionsRecyclerFragment = new ActionsRecyclerFragment();

        setFightManager(new FightManager());
        getFightManager().addFlowFreezer(this);
        getFightManager().addEventListener(this);

        enrollFighters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        p.setRoot(inflater.inflate(R.layout.fragment_battleground, container, false));
        initView();

        updateModel();
        return p.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        getFightManager().start();
    }

    @Override
    public void onEventReceived(Event event) {
        Log.e("Ledger", event.toString(getContext()));

        stopModelUpdates = false;
        if(event.getClass() == ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent.class) {
            ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent awaitingInputevent =
                    (ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent) event;

            if (awaitingInputevent.getFighter().getEntity() == getPlayerCharacter()) {
                setPlayerTurn(true);
                flowHelper.stopFlow();
            }
        } else if(event.getClass() == EntityPerformedActionEvent.class){
            handleActionEvent((EntityPerformedActionEvent)event);
        }
        updateModel();
        updateView();
        p.updateInfoBoxVisibility();
        stopModelUpdates = true;
    }

    @Override
    public void setEventListener(EventController.EventListener flowListener) {
        this.flowHelper = new FlowController.FlowHelper(flowListener);
    }

    @Override
    public void initPAC() {
        m = new BattlegroundModel();
        p = new BattlegroundPresenter();
    }

    // endregion //         Public Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public BattlegroundFragment() {
        // Required empty public constructor
    }

    // endregion //         Constructors
    //================================================================================
}
