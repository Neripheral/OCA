package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

public interface DuelistAI {
    Action getNextAction(Entity opponent);
}
