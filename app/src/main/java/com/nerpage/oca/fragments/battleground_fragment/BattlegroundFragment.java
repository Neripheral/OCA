package com.nerpage.oca.fragments.battleground_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;
import com.nerpage.oca.classes.fighting.phases.ActiveFighterAwaitingActionPhase;
import com.nerpage.oca.classes.fighting.ledger.events.EntityPerformedActionEvent;
import com.nerpage.oca.layouts.BattlegroundLayoutHelper;
import com.nerpage.oca.modelfactories.BattlegroundViewModelFactory;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.List;

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

        refreshFragmentData();
    }

    // endregion //         Private Methods
    //================================================================================
    //================================================================================
    // region //            Public Methods

    public void onEventRegistered(FightEvent data){
        Log.e("Ledger", data.toString(getContext()));

        if(data.getClass() == ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent.class) {
            ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent event =
                    (ActiveFighterAwaitingActionPhase.AwaitingFighterActionEvent) data;

            if (event.getFighter().getEntity() == getPlayerCharacter()) {
                setPlayerTurn(true);
            } else{
                getOnObserverReadyListener().run();
            }
        } else if(data.getClass() == EntityPerformedActionEvent.class){
            EntityPerformedActionEvent actionEvent = (EntityPerformedActionEvent) data;

            if(actionEvent.getAction() instanceof Action.HasEffectAnimation){
                Action.HasEffectAnimation action = (Action.HasEffectAnimation)actionEvent.getAction();

                if(actionEvent.getAction().getTarget() != getPlayerCharacter()){
                    getLayout().playEnemyEffect(action.getEffectResId(), action.getEffectDuration(), action.getEffectScale());
                } else{
                    getLayout().playPcEffect(action.getEffectResId(), action.getEffectDuration(), action.getEffectScale());
                }
            }
        } else{
            getOnObserverReadyListener().run();
        }
        refreshFragmentData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFightManager(new FightManager());
        setOnObserverReadyListener(
                getFightManager().addObserver(this::onEventRegistered)
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