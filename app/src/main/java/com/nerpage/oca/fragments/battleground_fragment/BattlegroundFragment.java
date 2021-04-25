package com.nerpage.oca.fragments.battleground_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.events.Event;
import com.nerpage.oca.classes.events.EventController;
import com.nerpage.oca.classes.events.FlowFreezer;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.phases.ActiveFighterAwaitingActionPhase;
import com.nerpage.oca.layouts.BattlegroundLayout;
import com.nerpage.oca.modelfactories.BattlegroundViewModelFactory;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.List;

public class BattlegroundFragment extends Fragment implements EventController.EventReceiver, EventController.EventEmitter {
    //================================================================================
    // region //            Fields

    private BattlegroundLayout layout;
    private FightManager fightManager;
    private Action nextAction = null;
    private boolean playerTurn = false;
    private EventController.EventListener flowFreezerListener;

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

    private EventController.EventListener getFlowFreezerListener() {
        return flowFreezerListener;
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
                getPlayerCharacter(),
                getFightManager().getParticipantsExceptForPc().get(0).getEntity());

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
            unfreezeFlow();
        }
    }

    private void onRecyclerScrolled(){
        getLayout().updateInfoBoxVisibility();
    }

    private void initView(View rootView){
        BattlegroundLayout newLayout = new BattlegroundLayout(
                rootView,
                BehaviorHelper::onBehaviorItemSelected,
                this::onActionItemClicked,
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        onRecyclerScrolled();
                    }
                }
        );
        setLayout(newLayout);
    }

    private void unfreezeFlow(){
        getFlowFreezerListener().emitEvent(new FlowFreezer.ResumeFlow(this));
    }

    private void freezeFlow(){
        getFlowFreezerListener().emitEvent(new FlowFreezer.FreezeFlow(this));
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
        initView(inflater.inflate(R.layout.fragment_battleground, container, false));

        getFightManager().addEventListener(getLayout());
        getFightManager().addFlowFreezer(getLayout());

        getFightManager().start();

        updateLayoutsModel();

        return getLayout().getRoot();
    }

    @Override
    public void onEventReceived(Event data) {
        Log.e("Ledger", data.toString(getContext()));

        if(data.getClass() == ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent.class) {
            ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent event =
                    (ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent) data;

            if (event.getFighter().getEntity() == getPlayerCharacter()) {
                setPlayerTurn(true);
                freezeFlow();
            }
        }
        updateLayoutsModel();
    }

    @Override
    public void setEventListener(EventController.EventListener flowFreezerListener) {
        this.flowFreezerListener = flowFreezerListener;
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
