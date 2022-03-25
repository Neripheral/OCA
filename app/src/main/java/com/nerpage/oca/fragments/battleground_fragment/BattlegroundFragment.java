package com.nerpage.oca.fragments.battleground_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.adapters.BattlegroundActionAdapter;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.events.Event;
import com.nerpage.oca.classes.events.EventController;
import com.nerpage.oca.classes.events.FlowController;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.phases.ActiveFighterAwaitingActionPhase;
import com.nerpage.oca.fragments.FighterCardFragment;
import com.nerpage.oca.fragments.PACFragment;
import com.nerpage.oca.fragments.models.BattlegroundModel;
import com.nerpage.oca.fragments.presenters.BattlegroundPresenter;
import com.nerpage.oca.interfaces.listeners.OnRecyclerItemClicked;
import com.nerpage.oca.layouts.BattlegroundLayout;
import com.nerpage.oca.modelfactories.BattlegroundViewModelFactory;
import com.nerpage.oca.layouts.models.BattlegroundViewModel;

import java.util.List;

public class BattlegroundFragment extends PACFragment<BattlegroundModel, BattlegroundPresenter> implements EventController.EventReceiver, EventController.EventEmitter {
    //================================================================================
    // region //            Fields

    private FighterCardFragment fighterCardFragment;

    private BattlegroundLayout layout;
    private FightManager fightManager;
    private Action nextAction = null;
    private boolean playerTurn = false;
    private FlowController.FlowHelper flowHelper;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private BattlegroundLayout getLayout() {
        return this.layout;
    }

    private BattlegroundFragment setLayout(BattlegroundLayout layout) {
        this.layout = layout;
        return this;
    }

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

    private void updateLayoutsModel(){
        BattlegroundViewModel model = BattlegroundViewModelFactory.generateFreshModel(
                requireContext(),
                getFightManager().getPcFighter(),
                getFightManager().getParticipantsExceptForPc().get(0));

        getLayout().setModel(model);
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

    private void onActionItemClicked(View v, int position){
        Action actionToPerform = getPlayerCharacter().getPossibleActions().get(position);
        actionToPerform.setSource(getPlayerCharacter());
        actionToPerform.setTarget(getFightManager().getParticipantsExceptForPc().get(0).getEntity());

        setNextAction(actionToPerform);
        if(isPlayerTurn()){
            setPlayerTurn(false);
            flowHelper.startFlow();
        }
    }

    private void onRecyclerScrolled(){
        p.updateInfoBoxVisibility();
    }

    private void initRecycler(){
        p.getRecycler().setLayoutManager(new LinearLayoutManager(p.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
        p.getRecycler().setAdapter(new BattlegroundActionAdapter(this::onActionItemClicked));
        p.getRecycler().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                onRecyclerScrolled();
            }
        });
        (new LinearSnapHelper()).attachToRecyclerView(p.getRecycler());
    }

    private void initView(){
        //TODO: when POIs are available remove referencing by R
        fighterCardFragment = (FighterCardFragment) getChildFragmentManager().findFragmentById(R.id.enemy_include);
        initRecycler();

        BattlegroundLayout newLayout = new BattlegroundLayout(
                p,
                BehaviorHelper::onBehaviorItemSelected,
                fighterCardFragment
        );
        setLayout(newLayout);
    }

    // endregion //         Private Methods
    //================================================================================
    //================================================================================
    // region //            Public Methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFightManager(new FightManager());
        getFightManager().addFlowFreezer(this);
        getFightManager().addEventListener(this);

        enrollFighters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        p.setRoot(inflater.inflate(R.layout.fragment_battleground, container, false));
        initView();

        getFightManager().addEventListener(getLayout());
        getFightManager().addFlowFreezer(getLayout());



        updateLayoutsModel();

        return getLayout().getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        getFightManager().start();
    }

    @Override
    public void onEventReceived(Event data) {
        Log.e("Ledger", data.toString(getContext()));

        if(data.getClass() == ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent.class) {
            ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent event =
                    (ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent) data;

            if (event.getFighter().getEntity() == getPlayerCharacter()) {
                setPlayerTurn(true);
                flowHelper.stopFlow();
            }
        }
        updateLayoutsModel();
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
