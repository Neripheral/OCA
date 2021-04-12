package com.nerpage.oca.layouts;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.adapters.BattlegroundActionAdapter;
import com.nerpage.oca.classes.LayoutHelper;
import com.nerpage.oca.models.ActionCardModel;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.ArrayList;

public class BattlegroundLayoutHelper extends LayoutHelper {
    public enum POI implements LayoutHelper.POI {
        ENEMY_TITLE(R.id.battleground_enemy_title),
        ENEMY_CURRENT_BLOOD(R.id.battleground_enemy_currentBlood),
        ENEMY_MAX_BLOOD(R.id.battleground_enemy_maxBlood),
        PC_CURRENT_BLOOD(R.id.battleground_pc_currentBlood),
        PC_MAX_BLOOD(R.id.battleground_pc_maxBlood),
        BEHAVIOR_NAVBAR(R.id.battleground_behavior_navbar),
        BEHAVIOR_SURRENDER_BUTTON(R.id.battleground_behavior_surrenderbtn),
        BEHAVIOR_ATTACK_BUTTON(R.id.battleground_behavior_attackbtn),
        BEHAVIOR_DEFEND_BUTTON(R.id.battleground_behavior_defendbtn),
        ACTIONS_RECYCLER(R.id.battleground_actions_recycler);

        int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id){
            this.id = id;
        }
    }

    private RecyclerView findRecycler(){
        return (RecyclerView)getView(POI.ACTIONS_RECYCLER);
    }

    public BattlegroundLayoutHelper updateViewUsing(BattlegroundViewModel model){
        updateText( POI.ENEMY_TITLE,            model.getEnemyTitle());
        updateText( POI.ENEMY_CURRENT_BLOOD,    String.valueOf(model.getEnemyCurrentBlood()));
        updateText( POI.ENEMY_MAX_BLOOD,        String.valueOf(model.getEnemyMaxBlood()));
        updateText( POI.PC_CURRENT_BLOOD,       String.valueOf(model.getPcCurrentBlood()));
        updateText( POI.PC_MAX_BLOOD,           String.valueOf(model.getPcMaxBlood()));

        ((BattlegroundActionAdapter) findRecycler().getAdapter())
                .setDataset(new ArrayList<>(model.getPossibleActions()));
        findRecycler().getAdapter().notifyDataSetChanged();

        return this;
    }

    public BattlegroundLayoutHelper(View rootView, BottomNavigationView.OnNavigationItemSelectedListener listener){
        super(rootView);
        findRecycler().setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        findRecycler().setAdapter(new BattlegroundActionAdapter());

        ((BottomNavigationView)getView(BattlegroundLayoutHelper.POI.BEHAVIOR_NAVBAR))
                .setOnNavigationItemSelectedListener(listener);
    }
}
