package com.nerpage.oca.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.LayoutHelper;
import com.nerpage.oca.classes.Ledger;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.actions.Punch;
import com.nerpage.oca.models.BattlegroundViewModel;

public class BattlegroundFragment extends Fragment {
    //================================================================================
    // Inner class
    //================================================================================

    public static class BattlegroundLayoutHelper extends LayoutHelper{
        public enum BattlegroundPOI implements LayoutHelper.POI{
            ENEMY_TITLE(R.id.battleground_enemy_title),
            ENEMY_CURRENT_BLOOD(R.id.battleground_enemy_currentBlood),
            ENEMY_MAX_BLOOD(R.id.battleground_enemy_maxBlood),
            PC_CURRENT_BLOOD(R.id.battleground_pc_currentBlood),
            PC_MAX_BLOOD(R.id.battleground_pc_maxBlood),
            ATTACK_BUTTON(R.id.battleground_attackbtn),
            BEHAVIOR_NAVBAR(R.id.battleground_behavior_navbar),
            BEHAVIOR_SURRENDER_BUTTON(R.id.battleground_behavior_surrenderbtn),
            BEHAVIOR_ATTACK_BUTTON(R.id.battleground_behavior_attackbtn),
            BEHAVIOR_DEFEND_BUTTON(R.id.battleground_behavior_defendbtn);

            int id;

            @Override
            public int getId() {
                return id;
            }

            BattlegroundPOI(int id){
                this.id = id;
            }
        }

        public BattlegroundLayoutHelper updateViewUsing(BattlegroundViewModel model){
            this.updateText( BattlegroundPOI.ENEMY_TITLE,            model.getEnemyTitle());
            this.updateText( BattlegroundPOI.ENEMY_CURRENT_BLOOD,    String.valueOf(model.getEnemyCurrentBlood()));
            this.updateText( BattlegroundPOI.ENEMY_MAX_BLOOD,        String.valueOf(model.getEnemyMaxBlood()));
            this.updateText( BattlegroundPOI.PC_CURRENT_BLOOD,       String.valueOf(model.getPcCurrentBlood()));
            this.updateText( BattlegroundPOI.PC_MAX_BLOOD,           String.valueOf(model.getPcMaxBlood()));
            return this;
        }

        public BattlegroundLayoutHelper bindClickListener(View.OnClickListener listener){
            this.getView(BattlegroundPOI.ATTACK_BUTTON).setOnClickListener(listener);
            return this;
        }

        public BattlegroundLayoutHelper(View rootView){
            super(rootView);
        }
    }

    //================================================================================
    // Fields
    //================================================================================

    private View rootView;
    private FightManager fightManager;
    private BattlegroundViewModel model;

    //================================================================================
    // Accessors
    //================================================================================

    public View getRootView() {
        return rootView;
    }

    public BattlegroundFragment setRootView(View rootView) {
        this.rootView = rootView;
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

    //================================================================================
    // Methods
    //================================================================================

    private BattlegroundLayoutHelper refreshFragmentData(){
        this.updateModel();
        BattlegroundLayoutHelper layout = new BattlegroundLayoutHelper(this.getRootView());
        layout.updateViewUsing(this.getModel());
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

    public void onAttackButtonPressed(){
        this.submitPlayerActionChoice();
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

    public void onProgressRegistered(Ledger.Row data){
        Log.e("Ledger", data.toString(this.getContext()));
        this.refreshFragmentData();
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
        if(itemId.getItemId() == BattlegroundLayoutHelper.BattlegroundPOI.BEHAVIOR_SURRENDER_BUTTON.getId()) {
            this.onBehaviorSurrenderSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundLayoutHelper.BattlegroundPOI.BEHAVIOR_ATTACK_BUTTON.getId()) {
            this.onBehaviorAttackSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundLayoutHelper.BattlegroundPOI.BEHAVIOR_DEFEND_BUTTON.getId()) {
            this.onBehaviorDefendSelected();
            return true;
        }
        return false;
    }

    private void initView(){
        BattlegroundLayoutHelper layout = this.refreshFragmentData()
                .bindClickListener(v->onAttackButtonPressed());

        ((BottomNavigationView)layout.getView(BattlegroundLayoutHelper.BattlegroundPOI.BEHAVIOR_NAVBAR))
                .setOnNavigationItemSelectedListener(this::onBehaviorItemSelected);

    }

    //================================================================================
    // Fragment overrides
    //================================================================================

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
        this.setRootView(inflater.inflate(R.layout.fragment_battleground, container, false));
        this.setModel(new ViewModelProvider(requireActivity()).get(BattlegroundViewModel.class));

        this.getFightManager().startFight();
        this.getFightManager().continueFight();

        this.initView();

        return this.getRootView();
    }

    //================================================================================
    // Constructors
    //================================================================================

    public BattlegroundFragment() {
        // Required empty public constructor
    }
}
