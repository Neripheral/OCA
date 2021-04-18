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
import android.widget.ImageView;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.classes.Event;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.ledger.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.helpers.AnimationHelper;
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
    //TODO: get rid of BiConsumer and replace it with new listener type
    private BiConsumer<Fighter, Action> actionSelectedNotifier = null;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public BattlegroundLayoutHelper getLayout() {
        return this.layout;
    }

    public BattlegroundFragment setLayout(BattlegroundLayoutHelper layout) {
        this.layout = layout;
        return this;
    }

    public FightManager getFightManager() {
        return this.fightManager;
    }

    public BattlegroundFragment setFightManager(FightManager fightManager) {
        this.fightManager = fightManager;
        return this;
    }

    public BiConsumer<Fighter, Action> getActionSelectedNotifier() {
        return actionSelectedNotifier;
    }

    public BattlegroundFragment setActionSelectedNotifier(BiConsumer<Fighter, Action> actionSelectedNotifier) {
        this.actionSelectedNotifier = actionSelectedNotifier;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private Methods

    private void onPlayersTurn(Fighter host, List<Fighter> others, BiConsumer<Fighter, Action> actionSelectedNotifier){
        //TODO: what to do when it's players turn
        setActionSelectedNotifier(actionSelectedNotifier);
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
        if(getActionSelectedNotifier() != null) {
            Action actionToPerform = getPlayerCharacter().getPossibleActions().get(position);
            actionToPerform.setSource(getPlayerCharacter());
            actionToPerform.setTarget(getFightManager().getParticipantsExceptForPc().get(0).getEntity());
            getActionSelectedNotifier().accept(getFightManager().getPcFighter(), actionToPerform);
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

    public void onProgressRegistered(Event data){
        Log.e("Ledger", data.toString(getContext()));

        if(data.getClass() == EntityPerformedActionEvent.class){
            EntityPerformedActionEvent actionEvent = (EntityPerformedActionEvent) data;

            if(actionEvent.getAction() instanceof Action.HasEffectAnimation){
                Action.HasEffectAnimation action = (Action.HasEffectAnimation)actionEvent.getAction();

                if(actionEvent.getAction().getTarget() != getPlayerCharacter()){
                    getLayout().playEnemyEffect(action.getEffectResId(), action.getEffectDuration(), action.getEffectScale());
                } else{
                    getLayout().playPcEffect(action.getEffectResId(), action.getEffectDuration(), action.getEffectScale());
                }
            }
        }

        refreshFragmentData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFightManager(new FightManager());
        getFightManager().addObserver(this::onProgressRegistered);
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
