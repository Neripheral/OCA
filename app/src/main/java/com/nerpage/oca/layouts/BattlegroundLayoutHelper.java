package com.nerpage.oca.layouts;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nerpage.oca.R;
import com.nerpage.oca.adapters.BattlegroundActionAdapter;
import com.nerpage.oca.classes.LayoutHelper;
import com.nerpage.oca.interfaces.listeners.OnRecyclerItemClicked;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.ArrayList;

public class BattlegroundLayoutHelper extends LayoutHelper {
    //================================================================================
    // region //            POI

    public enum POI implements LayoutHelper.POI {
        ENEMY_TITLE(R.id.enemy_title),
        ENEMY_CURRENT_BLOOD(R.id.enemy_currentBlood),
        ENEMY_MAX_BLOOD(R.id.enemy_maxBlood),
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

    // endregion //         POI
    //================================================================================
    //================================================================================
    // region //            Private methods

    private RecyclerView findRecycler(){
        return (RecyclerView)getView(POI.ACTIONS_RECYCLER);
    }

    private void changeInfoBoxVisibility(RecyclerView.ViewHolder firstHolder, RecyclerView.ViewHolder lastHolder) {
        if(firstHolder == lastHolder){
            firstHolder.itemView.findViewById(R.id.action_info).setVisibility(View.VISIBLE);
        }else{
            firstHolder.itemView.findViewById(R.id.action_info).setVisibility(View.GONE);
            lastHolder.itemView.findViewById(R.id.action_info).setVisibility(View.GONE);
        }
    }

    private void updateViewDataWithModel(BattlegroundViewModel model){
        updateText( POI.ENEMY_TITLE,            model.getEnemyTitle());
        updateText( POI.ENEMY_CURRENT_BLOOD,    String.valueOf(model.getEnemyCurrentBlood()));
        updateText( POI.ENEMY_MAX_BLOOD,        String.valueOf(model.getEnemyMaxBlood()));
        updateText( POI.PC_CURRENT_BLOOD,       String.valueOf(model.getPcCurrentBlood()));
        updateText( POI.PC_MAX_BLOOD,           String.valueOf(model.getPcMaxBlood()));

        BattlegroundActionAdapter adapter = ((BattlegroundActionAdapter) findRecycler().getAdapter());
        assert adapter != null;
        adapter.setDataset(new ArrayList<>(model.getPossibleActions()));
        adapter.notifyDataSetChanged();
    }

    // endregion //         Private methods
    //================================================================================

    public BattlegroundLayoutHelper updateInfoBoxVisibility(){
        LinearLayoutManager manager = ((LinearLayoutManager)findRecycler().getLayoutManager());
        assert manager != null;

        RecyclerView.ViewHolder firstHolder =
                findRecycler().findViewHolderForLayoutPosition(
                        manager.findFirstVisibleItemPosition());
        RecyclerView.ViewHolder lastHolder =
                findRecycler().findViewHolderForLayoutPosition(
                        manager.findLastVisibleItemPosition());

        if(firstHolder != null && lastHolder != null)
            changeInfoBoxVisibility(firstHolder, lastHolder);

        return this;
    }

    public BattlegroundLayoutHelper updateView(BattlegroundViewModel model){
        updateViewDataWithModel(model);
        updateInfoBoxVisibility();
        return this;
    }

    public BattlegroundLayoutHelper(View rootView,
                                    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener,
                                    OnRecyclerItemClicked onRecyclerItemClicked,
                                    RecyclerView.OnScrollListener onScrollListener){
        super(rootView);
        findRecycler().setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        findRecycler().setAdapter(new BattlegroundActionAdapter(onRecyclerItemClicked));
        findRecycler().addOnScrollListener(onScrollListener);
        (new LinearSnapHelper()).attachToRecyclerView(findRecycler());

        ((BottomNavigationView)getView(BattlegroundLayoutHelper.POI.BEHAVIOR_NAVBAR))
                .setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }
}
