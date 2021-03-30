package com.nerpage.oca.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
import com.nerpage.oca.classes.fighting.DuelManager;
import com.nerpage.oca.classes.fighting.EnemyGenerator;
import com.nerpage.oca.models.DuelViewModel;

import java.util.Objects;

public class DuelFragment extends Fragment {
    //================================================================================
    // Inner class
    //================================================================================

    public static class DuelLayoutHelper extends LayoutHelper{
        public enum DuelPOI implements LayoutHelper.POI{
            ENEMY_TITLE(R.id.duel_enemy_title),
            ENEMY_CURRENT_BLOOD(R.id.duel_enemy_currentBlood),
            ENEMY_MAX_BLOOD(R.id.duel_enemy_maxBlood),
            PC_CURRENT_BLOOD(R.id.duel_pc_currentBlood),
            PC_MAX_BLOOD(R.id.duel_pc_maxBlood);

            int id;

            @Override
            public int getId() {
                return id;
            }

            DuelPOI(int id){
                this.id = id;
            }
        }
    }

    //================================================================================
    // Fields
    //================================================================================

    private View rootView;

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

    //================================================================================
    // Methods
    //================================================================================

    public Action onPlayerTurn(Entity enemy){
        //TODO: add what to do on player's turn
        return null;
    }

    private void enrollDuelists(DuelManager duelManager){
        PlayerCharacter pc = ((CharacterEditorActivity) Objects.requireNonNull(getActivity())).getPc();
        duelManager.enrollDuelist(pc, this::onPlayerTurn);
        Entity enemy = EnemyGenerator.generate();
        duelManager.enrollAI(enemy);
    }

    private void updateModel(DuelViewModel model, DuelManager duelManager){

    }

    //================================================================================
    // Fragment overrides
    //================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init duel
        DuelManager duelManager = new DuelManager();
        this.enrollDuelists(duelManager);

        // init model
        DuelViewModel model = new ViewModelProvider(requireActivity()).get(DuelViewModel.class);
        //TODO: continue tomorrow
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_duel, container, false);

        return this.rootView;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public DuelFragment() {
        // Required empty public constructor
    }
}
