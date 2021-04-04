package com.nerpage.oca.classes.fighting;

import java.util.List;

public abstract class FightingBehavior {
    public abstract Action promptAction(Fighter host, List<Fighter> others);

    public FightingBehavior(){}
}
