package com.nerpage.oca.fragments.battleground_fragment;

import android.view.MenuItem;

import com.nerpage.oca.layouts.BattlegroundLayout;

class BehaviorHelper {
    private static void onBehaviorSurrenderSelected(){
        //TODO: On surrender
    }

    private static void onBehaviorAttackSelected(){
        //TODO: On attack
    }

    private static void onBehaviorDefendSelected(){
        //TODO: On defend
    }

    public static boolean onBehaviorItemSelected(MenuItem itemId){
        if(itemId.getItemId() == BattlegroundLayout.POI.BEHAVIOR_SURRENDER_BUTTON.getId()) {
            onBehaviorSurrenderSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundLayout.POI.BEHAVIOR_ATTACK_BUTTON.getId()) {
            onBehaviorAttackSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundLayout.POI.BEHAVIOR_DEFEND_BUTTON.getId()) {
            onBehaviorDefendSelected();
            return true;
        }
        return false;
    }
}
