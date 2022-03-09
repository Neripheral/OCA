package com.nerpage.oca.fragments.battleground_fragment;

import android.view.MenuItem;

import com.nerpage.oca.fragments.presenters.BattlegroundPresenter;
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
        //TODO: Remove flow splitting from here. Presenter should be responsible for recognizing which MenuItem was clicked.
        if(itemId.getItemId() == BattlegroundPresenter.POI.BEHAVIOR_SURRENDER_BUTTON.getId()) {
            onBehaviorSurrenderSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundPresenter.POI.BEHAVIOR_ATTACK_BUTTON.getId()) {
            onBehaviorAttackSelected();
            return true;
        }else if(itemId.getItemId() == BattlegroundPresenter.POI.BEHAVIOR_DEFEND_BUTTON.getId()) {
            onBehaviorDefendSelected();
            return true;
        }
        return false;
    }
}
