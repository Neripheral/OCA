package com.nerpage.oca.classes;

import com.nerpage.oca.classes.entities.TrainingDummy;
import com.nerpage.oca.classes.fighting.DuelistAI;

public class NPCGenerator {
    public static DuelistAI generateEnemy() {
        return new TrainingDummy();
    }
}
