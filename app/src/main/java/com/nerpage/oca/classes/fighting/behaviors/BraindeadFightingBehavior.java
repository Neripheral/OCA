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
        List<Class<? extends Action>> possibleActions = host.getEntity().getPossibleActions();
        Class<? extends Action> actionToPerform = possibleActions.get(rand.nextInt(possibleActions.size()));

        // picking one random enemy
        Fighter pickedOpponent = others.get(rand.nextInt(others.size()));

        // Get Action object
        Action toReturn = null;
        try {
            toReturn = actionToPerform.getDeclaredConstructor().newInstance();
            toReturn.setSource(host.getEntity());
            toReturn.setTarget(pickedOpponent.getEntity());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
