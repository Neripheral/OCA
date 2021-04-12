package com.nerpage.oca.layouts;

import android.view.View;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.LayoutHelper;
import com.nerpage.oca.models.BattlegroundViewModel;

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

    public BattlegroundLayoutHelper updateViewUsing(BattlegroundViewModel model){
        this.updateText( POI.ENEMY_TITLE,            model.getEnemyTitle());
        this.updateText( POI.ENEMY_CURRENT_BLOOD,    String.valueOf(model.getEnemyCurrentBlood()));
        this.updateText( POI.ENEMY_MAX_BLOOD,        String.valueOf(model.getEnemyMaxBlood()));
        this.updateText( POI.PC_CURRENT_BLOOD,       String.valueOf(model.getPcCurrentBlood()));
        this.updateText( POI.PC_MAX_BLOOD,           String.valueOf(model.getPcMaxBlood()));
        return this;
    }

    public BattlegroundLayoutHelper(View rootView){
        super(rootView);
    }
}
