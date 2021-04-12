package com.nerpage.oca.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.adapters.BattlegroundActionAdapter;
import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.Ledger;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.actions.Punch;
import com.nerpage.oca.layouts.BattlegroundLayoutHelper;
import com.nerpage.oca.modelfactories.BattlegroundViewModelFactory;
import com.nerpage.oca.models.BattlegroundViewModel;

public class BattlegroundFragment extends Fragment {
    //================================================================================
    // region //            Fields

    private BattlegroundLayoutHelper layout;
    private FightManager fightManager;

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

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private Methods

    private void refreshFragmentData(){
        BattlegroundViewModel model = BattlegroundViewModelFactory.generateFreshModel(
                requireContext(),
                getPlayerCharacter(),
                getFightManager().getFighters().get(1).getEntity());

        getLayout().updateViewUsing(model);
    }

    private void submitPlayerActionChoice(Action action){
        action.setSource(getFightManager().getPlayerFighter().getEntity());
        action.setTarget(getFightManager().getFightersWithout(getFightManager().getPlayerFighter()).get(0).getEntity());
        getFightManager().registerSelectedAction(getFightManager().getPlayerFighter(), action);
        getFightManager().continueFight();
        refreshFragmentData();
    }

    private PlayerCharacter getPlayerCharacter(){
        return ((CharacterEditorActivity)requireActivity()).getPc();
    }

    private void enrollFighters(){
        // enroll player
        getFightManager().enrollPlayer(getPlayerCharacter());

        // enroll enemy
        getFightManager().enrollFighter(EnemyGenerator.generateRandomEnemy());
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
        submitPlayerActionChoice(getPlayerCharacter().getPossibleActions().get(position));
    }

    private void initView(View rootView){
        BattlegroundLayoutHelper newLayout = new BattlegroundLayoutHelper(
                rootView,
                this::onBehaviorItemSelected,
                this::onActionItemClicked
        );
        setLayout(newLayout);

        refreshFragmentData();
    }

    // endregion //         Private Methods
    //================================================================================
    //================================================================================
    // region //            Public Methods

    public void onProgressRegistered(Ledger.Row data){
        Log.e("Ledger", data.toString(getContext()));
        refreshFragmentData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFightManager(new FightManager());
        getFightManager().addProgressListener(this::onProgressRegistered);
        getFightManager().setGoal(FightManager.Goal.DEATH);
        enrollFighters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView(inflater.inflate(R.layout.fragment_battleground, container, false));

        getFightManager().startFight();
        getFightManager().continueFight();

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
