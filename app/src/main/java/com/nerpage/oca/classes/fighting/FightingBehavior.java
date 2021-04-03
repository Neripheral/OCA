package com.nerpage.oca.classes.fighting;

import java.util.List;
import java.util.function.BiFunction;

public class FightingBehavior {
    private BiFunction<Fighter, List<Fighter>, Action> logic;

    public Action promptAction(Fighter host, List<Fighter> others){
        return this.logic.apply(host, others);
    }

    public FightingBehavior(){
        this.logic = ((host, others) -> null);
    }
}
