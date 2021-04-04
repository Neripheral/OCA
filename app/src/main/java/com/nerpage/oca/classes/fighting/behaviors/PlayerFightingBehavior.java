package com.nerpage.oca.classes.fighting.behaviors;

import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.FightingBehavior;

import java.util.List;
import java.util.function.BiFunction;

public class PlayerFightingBehavior extends FightingBehavior {

    private BiFunction<Fighter, List<Fighter>, Action> listener;

    @Override
    public Action promptAction(Fighter host, List<Fighter> others) {
        return this.listener.apply(host, others);
    }

    public PlayerFightingBehavior(BiFunction<Fighter, List<Fighter>, Action> listener){
        this.listener = listener;
    }
}
