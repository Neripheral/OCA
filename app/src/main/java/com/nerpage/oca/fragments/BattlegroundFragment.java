package com.nerpage.oca.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.classes.Event;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;
import com.nerpage.oca.classes.fighting.phases.ActiveFighterAwaitingActionPhase;
import com.nerpage.oca.layouts.BattlegroundLayoutHelper;
import com.nerpage.oca.modelfactories.BattlegroundViewModelFactory;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.List;
import java.util.function.BiConsumer;

public class BattlegroundFragment extends Fragment {
    //================================================================================
    // region //            Fields

    private BattlegroundLayoutHelper layout;
    private FightManager fightManager;
    private Action nextAction = null;
    private boolean playerTurn = false;
    private Runnable onObserverReadyListener = null;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private BattlegroundLayoutHelper getLayout() {
        return this.layout;
    }

    private BattlegroundFragment setLayout(BattlegroundLayoutHelper layout) {
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

    private Runnable getOnObserverReadyListener() {
        return onObserverReadyListener;
    }

    private BattlegroundFragment setOnObserverReadyListener(Runnable onObserverReadyListener) {
        this.onObserverReadyListener = onObserverReadyListener;
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

    private void refreshFragmentData(){
        BattlegroundViewModel model = BattlegroundViewModelFactory.generateFreshModel(
                requireContext(),
                getPlayerCharacter(),
                getFightManager().getParticipantsExceptForPc().get(0).getEntity());

        getLayout().updateView(model);
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

    private void onBehaviorSurrenderSelected(){
        //TODO: On surrender
    }

    private void onBehaviorAttackSelected(){
        //TODO: On attack
    }

    private void onBehaviorDefendSelected(){
        //TODO: On defend
    }

    private boolean onBehaviorItemSelected(MenuItem itemId){
        if(itemId.getItemId() == BattlegroundLayoutHelper.POI.BEHAVIOR_SURRENDER_BUTTON.getId()) {
            onBehaviorSurrenderSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundLayoutHelper.POI.BEHAVIOR_ATTACK_BUTTON.getId()) {
            onBehaviorAttackSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundLayoutHelper.POI.BEHAVIOR_DEFEND_BUTTON.getId()) {
            onBehaviorDefendSelected();
            return true;
        }
        return false;
    }

    private void onActionItemClicked(View v, int position){
        Action actionToPerform = getPlayerCharacter().getPossibleActions().get(position);
        actionToPerform.setSource(getPlayerCharacter());
        actionToPerform.setTarget(getFightManager().getParticipantsExceptForPc().get(0).getEntity());

        setNextAction(actionToPerform);
        if(isPlayerTurn()){
            setPlayerTurn(false);
            getOnObserverReadyListener().run();
        }
        refreshFragmentData();
    }


    private void onRecyclerScrolled(){
        getLayout().updateInfoBoxVisibility();
    }

    private void initView(View rootView){
        BattlegroundLayoutHelper newLayout = new BattlegroundLayoutHelper(
                rootView,
                this::onBehaviorItemSelected,
                this::onActionItemClicked,
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        onRecyclerScrolled();
                    }
                }
        );
        setLayout(newLayout);

        refreshFragmentData();
    }

    // endregion //         Private Methods
    //================================================================================
    //================================================================================
    // region //            Public Methods

    public void onProgressRegistered(FightEvent data){
        Log.e("Ledger", data.toString(getContext()));

        if(data.getClass() == ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent.class) {
            ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent event =
                    (ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent) data;

            if (event.getFighter().getEntity() == getPlayerCharacter()) {
                setPlayerTurn(true);
                return;
            }
        }
        getOnObserverReadyListener().run();
        refreshFragmentData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFightManager(new FightManager());
        setOnObserverReadyListener(
                getFightManager().addObserver(this::onProgressRegistered)
        );
        enrollFighters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView(inflater.inflate(R.layout.fragment_battleground, container, false));

        getFightManager().start();

        return getLayout().getRoot();
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
