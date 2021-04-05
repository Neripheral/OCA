package com.nerpage.oca.classes.fighting.behaviors;

import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.FightingBehavior;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

public class BraindeadFightingBehavior extends FightingBehavior {
    @Override
    public Action promptAction(Fighter host, List<Fighter> others) {
        Random rand = new Random();

        // picking one random action to perform
        List<Action> possibleActions = host.getEntity().getPossibleActions();
        Action actionToPerform = possibleActions.get(rand.nextInt(possibleActions.size())).clone();

        // picking one random enemy
        Fighter pickedOpponent = others.get(rand.nextInt(others.size()));

        actionToPerform.setSource(host.getEntity());
        actionToPerform.setTarget(pickedOpponent.getEntity());

        return actionToPerform;
    }
}
