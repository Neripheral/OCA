package com.nerpage.oca.classes.fighting.ledger.events;

import android.content.Context;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Event;
import com.nerpage.oca.classes.fighting.Fighter;

import java.util.List;

public final class FightStartedEvent extends FightEvent {
    private final List<Fighter> fighters;

    public List<Fighter> getFighters() {
        return fighters;
    }

    @Override
    public String toString() {
        return "FightStartedEvent{" +
                "fighters=" + fighters +
                '}';
    }

    @Override
    public String toString(Context context) {
        String namesAsString = getFighters()
                .stream()
                .map(fighter -> (fighter.getEntity().getName(context) + ", "))
                .reduce("", String::concat);

        return context.getString(R.string.event_fight_started_with_blank, namesAsString);
    }

    public FightStartedEvent(List<Fighter> fighters){
        this.fighters = fighters;
    }
}
