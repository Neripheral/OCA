package com.nerpage.oca.classes.fighting.behaviors;

import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.Fighter;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class FightingBehavior {
    public abstract void promptAction(Fighter host, List<Fighter> others, BiConsumer<Fighter, Action> actionSelectedNotifier);

    public FightingBehavior(){}
}
