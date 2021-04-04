package com.nerpage.oca.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.LayoutHelper;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Punch;
import com.nerpage.oca.classes.fighting.behaviors.PlayerFightingBehavior;
import com.nerpage.oca.classes.fighting.statuses.Bloodsuck;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.List;

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
            ATTACK_BUTTON(R.id.battleground_attackbtn);

            int id;

            @Override
            public int getId() {
                return id;
            }

            BattlegroundPOI(int id){
                this.id = id;
            }
        }

        public void updateViewUsing(BattlegroundViewModel model){
            this.updateText( BattlegroundPOI.ENEMY_TITLE,            model.getEnemyTitle());
            this.updateText( BattlegroundPOI.ENEMY_CURRENT_BLOOD,    String.valueOf(model.getEnemyCurrentBlood()));
            this.updateText( BattlegroundPOI.ENEMY_MAX_BLOOD,        String.valueOf(model.getEnemyMaxBlood()));
            this.updateText( BattlegroundPOI.PC_CURRENT_BLOOD,       String.valueOf(model.getPcCurrentBlood()));
            this.updateText( BattlegroundPOI.PC_MAX_BLOOD,           String.valueOf(model.getPcMaxBlood()));
        }

        public void bindListener(View.OnClickListener listener){
            this.getView(BattlegroundPOI.ATTACK_BUTTON).setOnClickListener(listener);
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

    public void onAttackButtonPressed(){
        //TODO: add what to do when the button is pressed
    }

    public Action onPlayerTurn(List<Fighter> otherFighters){
        //TODO: add what to do on player's turn

        return new Punch(20);
    }

    private PlayerCharacter getPlayerCharacter(){
        return ((CharacterEditorActivity)this.requireActivity()).getPc();
    }

    private void enrollFighters(){
        // enroll player
        this.getFightManager().enrollFighter(
                this.getPlayerCharacter(),
                new PlayerFightingBehavior(
                    (fighter, otherFighters) -> this.onPlayerTurn(otherFighters)
        ));

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

    //================================================================================
    // Fragment overrides
    //================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setFightManager(new FightManager());
        this.enrollFighters();

        this.setModel(new ViewModelProvider(requireActivity()).get(BattlegroundViewModel.class));
        this.updateModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setRootView(inflater.inflate(R.layout.fragment_battleground, container, false));

        BattlegroundLayoutHelper layout = new BattlegroundLayoutHelper(this.getRootView());
        layout.updateViewUsing(this.getModel());
        layout.bindListener(v->onAttackButtonPressed());

        return this.getRootView();
    }

    //================================================================================
    // Constructors
    //================================================================================

    public BattlegroundFragment() {
        // Required empty public constructor
    }
}
