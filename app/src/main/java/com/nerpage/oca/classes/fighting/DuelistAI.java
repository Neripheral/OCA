package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.fighting.actions.Action;

public interface DuelistAI {
    Action getNextAction(Entity opponent);
}
