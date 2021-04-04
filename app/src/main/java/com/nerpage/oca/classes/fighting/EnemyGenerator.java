package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.entities.TrainingDummy;
import com.nerpage.oca.classes.fighting.behaviors.BraindeadFightingBehavior;

public class EnemyGenerator {
    public static Fighter generateRandomEnemy(){
        return new Fighter(
                new TrainingDummy(),
                new BraindeadFightingBehavior()
        );
    }
}
