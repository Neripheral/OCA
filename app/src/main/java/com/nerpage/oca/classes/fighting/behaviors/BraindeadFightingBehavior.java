package com.nerpage.oca.classes.fighting.behaviors;

import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.Fighter;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BraindeadFightingBehavior implements FightingBehavior {
    @Override
    public void promptAction(Fighter host, List<Fighter> others, BiConsumer<Fighter, Action> onActionSelectedNotifier) {
        Random rand = new Random();

        // picking one random action to perform
        List<Action> possibleActions = host.getEntity().getPossibleActions();
        Action actionToPerform = possibleActions.get(rand.nextInt(possibleActions.size())).clone();

        // picking one random enemy
        Fighter pickedOpponent = others.get(rand.nextInt(others.size()));

        actionToPerform.setSource(host.getEntity());
        actionToPerform.setTarget(pickedOpponent.getEntity());

        onActionSelectedNotifier.accept(host, actionToPerform);
    }
}
