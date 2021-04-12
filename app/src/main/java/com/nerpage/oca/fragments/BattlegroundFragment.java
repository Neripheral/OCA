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
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.actions.Punch;
import com.nerpage.oca.layouts.BattlegroundLayoutHelper;
import com.nerpage.oca.models.BattlegroundViewModel;

public class BattlegroundFragment extends Fragment {
    //================================================================================
    // region //            Fields

    private View rootView;
    private BattlegroundLayoutHelper layout;
    private FightManager fightManager;
    private BattlegroundViewModel model;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public BattlegroundFragment setRootView(View rootView) {
        this.rootView = rootView;
        return this;
    }

    public BattlegroundLayoutHelper getLayout() {
        return layout;
    }

    public BattlegroundFragment setLayout(BattlegroundLayoutHelper layout) {
        this.layout = layout;
        return this;
    }

    public FightManager getFightManager() {
        return fightManager;
    }

    public BattlegroundFragment setFightManager(FightManager fightManager) {
        this.fightManager = fightManager;
        return this;
    }

    public BattlegroundViewModel getModel() {
        return model;
    }

    public BattlegroundFragment setModel(BattlegroundViewModel model) {
        this.model = model;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private Methods

    private BattlegroundLayoutHelper refreshFragmentData(){
        this.updateModel();
        this.getLayout().updateViewUsing(this.getModel());
        return layout;
    }

    private void submitPlayerActionChoice(){
        Punch action = new Punch(20);
        action.setSource(this.getFightManager().getPlayerFighter().getEntity());
        action.setTarget(this.getFightManager().getFightersWithout(this.getFightManager().getPlayerFighter()).get(0).getEntity());
        this.getFightManager().registerSelectedAction(this.getFightManager().getPlayerFighter(), action);
        this.getFightManager().continueFight();
        this.refreshFragmentData();
    }

    private PlayerCharacter getPlayerCharacter(){
        return ((CharacterEditorActivity)this.requireActivity()).getPc();
    }

    private void enrollFighters(){
        // enroll player
        this.getFightManager().enrollPlayer(this.getPlayerCharacter());

        // enroll enemy
        this.getFightManager().enrollFighter(EnemyGenerator.generateRandomEnemy());
    }

    private void updateModel(){
        Entity pc = ((CharacterEditorActivity) this.requireActivity()).getPc();
        this.getModel().setPcCurrentBlood(pc.getBlood());
        this.getModel().setPcMaxBlood(pc.getMaxBlood());

        Entity enemy = this.getFightManager().getFighters().get(1).getEntity();
        this.getModel().setEnemyTitle(enemy.getName(getContext()));
        this.getModel().setEnemyCurrentBlood(enemy.getBlood());
        this.getModel().setEnemyMaxBlood(enemy.getMaxBlood());
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
            this.onBehaviorSurrenderSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundLayoutHelper.POI.BEHAVIOR_ATTACK_BUTTON.getId()) {
            this.onBehaviorAttackSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundLayoutHelper.POI.BEHAVIOR_DEFEND_BUTTON.getId()) {
            this.onBehaviorDefendSelected();
            return true;
        }
        return false;
    }

    private void initView(View rootView){
        this.setLayout(new BattlegroundLayoutHelper(rootView));
        this.setModel(new ViewModelProvider(requireActivity()).get(BattlegroundViewModel.class));

        //init behavior bar
        ((BottomNavigationView)layout.getView(BattlegroundLayoutHelper.POI.BEHAVIOR_NAVBAR))
                .setOnNavigationItemSelectedListener(this::onBehaviorItemSelected);

        /*//init actions recycler
        RecyclerView recycler = (RecyclerView) this.getLayout().getView(BattlegroundLayoutHelper.POI.ACTIONS_RECYCLER);
        recycler.setAdapter(new BattlegroundActionAdapter());*/
        refreshFragmentData();
    }

    // endregion //         Private Methods
    //================================================================================
    //================================================================================
    // region //            Public Methods

    public void onAttackButtonPressed(){
        this.submitPlayerActionChoice();
    }

    public void onProgressRegistered(Ledger.Row data){
        Log.e("Ledger", data.toString(this.getContext()));
        this.refreshFragmentData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setFightManager(new FightManager());
        this.getFightManager().addProgressListener(this::onProgressRegistered);
        this.getFightManager().setGoal(FightManager.Goal.DEATH);
        this.enrollFighters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.initView(inflater.inflate(R.layout.fragment_battleground, container, false));

        this.getFightManager().startFight();
        this.getFightManager().continueFight();

        return this.getLayout().getRoot();
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
