package com.nerpage.oca.classes.fighting.behaviors;

import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.Fighter;

import java.util.List;
import java.util.function.BiConsumer;

public interface FightingBehavior {
    interface ActionSelectedListener{
        void passSelectedAction(Fighter fighter, Action action);
    }

    void promptAction(Fighter host, List<Fighter> others, ActionSelectedListener listener);
}
