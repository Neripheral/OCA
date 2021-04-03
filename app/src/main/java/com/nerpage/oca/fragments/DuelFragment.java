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
import com.nerpage.oca.classes.NPCGenerator;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.FightManager;
import com.nerpage.oca.classes.fighting.DuelistAI;
import com.nerpage.oca.models.BattlegroundViewModel;

public class DuelFragment extends Fragment {
    //================================================================================
    // Inner class
    //================================================================================

    public static class DuelLayoutHelper extends LayoutHelper{
        public enum DuelPOI implements LayoutHelper.POI{
            ENEMY_TITLE(R.id.battlground_enemy_title),
            ENEMY_CURRENT_BLOOD(R.id.battlground_enemy_currentBlood),
            ENEMY_MAX_BLOOD(R.id.battlground_enemy_maxBlood),
            PC_CURRENT_BLOOD(R.id.battlground_pc_currentBlood),
            PC_MAX_BLOOD(R.id.battlground_pc_maxBlood),
            ATTACK_BUTTON(R.id.battlground_attackbtn);

            int id;

            @Override
            public int getId() {
                return id;
            }

            DuelPOI(int id){
                this.id = id;
            }
        }

        public void updateViewUsing(BattlegroundViewModel model){
            this.updateText( DuelPOI.ENEMY_TITLE,            model.getEnemyTitle());
            this.updateText( DuelPOI.ENEMY_CURRENT_BLOOD,    String.valueOf(model.getEnemyCurrentBlood()));
            this.updateText( DuelPOI.ENEMY_MAX_BLOOD,        String.valueOf(model.getEnemyMaxBlood()));
            this.updateText( DuelPOI.PC_CURRENT_BLOOD,       String.valueOf(model.getPcCurrentBlood()));
            this.updateText( DuelPOI.PC_MAX_BLOOD,           String.valueOf(model.getPcMaxBlood()));
        }

        public void bindListener(View.OnClickListener listener){
            this.getView(DuelPOI.ATTACK_BUTTON).setOnClickListener(listener);
        }

        public DuelLayoutHelper(View rootView){
            super(rootView);
        }
    }

    //================================================================================
    // Fields
    //================================================================================

    private View rootView;
    private FightManager duelManager;
    private BattlegroundViewModel model;

    //================================================================================
    // Accessors
    //================================================================================

    public View getRootView() {
        return rootView;
    }

    public DuelFragment setRootView(View rootView) {
        this.rootView = rootView;
        return this;
    }

    public FightManager getDuelManager() {
        return duelManager;
    }

    public DuelFragment setDuelManager(FightManager duelManager) {
        this.duelManager = duelManager;
        return this;
    }

    public BattlegroundViewModel getModel() {
        return model;
    }

    public DuelFragment setModel(BattlegroundViewModel model) {
        this.model = model;
        return this;
    }

    //================================================================================
    // Methods
    //================================================================================

    public void onAttackButtonPressed(){
        //TODO: add what to do when the button is pressed
    }

    public Action onPlayerTurn(Entity enemy){
        //TODO: add what to do on player's turn
        return null;
    }

    private void enrollDuelists(){
        PlayerCharacter pc = ((CharacterEditorActivity) requireActivity()).getPc();
        this.getDuelManager().enrollFighter(pc, this::onPlayerTurn);
        DuelistAI enemy = NPCGenerator.generateEnemy();
        this.getDuelManager().enrollAI(enemy);
    }

    private void updateModel(){
        Entity pc = ((CharacterEditorActivity) this.requireActivity()).getPc();
        this.getModel().setPcCurrentBlood(pc.getBlood());
        this.getModel().setPcMaxBlood(pc.getMaxBlood());

        Entity enemy = this.getDuelManager().getFighters().get(1).getEntity();
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

        this.setDuelManager(new FightManager());
        this.enrollDuelists();

        this.setModel(new ViewModelProvider(requireActivity()).get(BattlegroundViewModel.class));
        this.updateModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setRootView(inflater.inflate(R.layout.fragment_battleground, container, false));

        DuelLayoutHelper layout = new DuelLayoutHelper(this.getRootView());
        layout.updateViewUsing(this.getModel());
        layout.bindListener(v->onAttackButtonPressed());

        return this.getRootView();
    }

    //================================================================================
    // Constructors
    //================================================================================

    public DuelFragment() {
        // Required empty public constructor
    }
}
