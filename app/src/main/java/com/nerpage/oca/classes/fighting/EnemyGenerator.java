package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.entities.TrainingDummy;

public class EnemyGenerator {
    public static Entity generate(){
        return new TrainingDummy();
    }
}
