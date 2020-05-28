package com.nerpage.oca.classes;

import android.content.Context;

import com.nerpage.oca.R;

import java.util.ArrayList;
import java.util.List;

public class Skill {
    public static final int MIN_SPENTPOINTS = 0;
    public static final int MAX_SPENTPOINTS = 40;
    private static final int SKILLNAMES_ARRAY_ID = R.array.skillNames;
    private String id;
    private int spentPoints;
    private int parentAttribute;

    public String getId() {
        return this.id;
    }
    public Skill setId(String id) {
        this.id = id;
        return this;
    }
    public int getSpentPoints(){
        return this.spentPoints;
    }
    public Skill setSpentPoints(int spentPoints){
        if(spentPoints < MIN_SPENTPOINTS)
            spentPoints = MIN_SPENTPOINTS;
        if(spentPoints > MAX_SPENTPOINTS)
            spentPoints = MAX_SPENTPOINTS;
        this.spentPoints = spentPoints;
        return this;
    }
    public int getParentAttribute(){return parentAttribute;}
    public Skill setParentAttribute(int parentAttribute){
        this.parentAttribute = parentAttribute;
        return this;
    }

    public String getTitle(Context context){
        String[] titles = context.getResources().getStringArray(SKILLNAMES_ARRAY_ID);
        for(int i = 0; i < titles.length; i+=2){
            if(titles[i].equals(getId()))
                return titles[i+1];
        }
        return "";
    }
    public int getTotalCounter(final PlayerCharacter pc){
        int totalCounter = PlayerCharacter.MIN_SKILLLEVEL;

        totalCounter += 2 * pc.getAttribute(getParentAttribute());
        totalCounter += pc.getAttribute(getParentAttribute()) * getSpentPoints();

        return totalCounter;
    }

    public static final int CRITICAL_SUCCESS = 0;
    public static final int BIG_SUCCESS = 1;
    public static final int NORMAL_SUCCESS = 2;
    public static final int FLOW = 3;
    public static final int FAILURE = 4;
    public static final int CRITICAL_FAILURE = 5;
    public static final double CRITICAL_SUCCESS_FRACTION = 0.1;
    public static final double BIG_SUCCESS_FRACTION = 0.5;

    public List<List<Integer>> getSkillThrowResultBoundaries(PlayerCharacter pc){
        List<List<Integer>> boundaries = new ArrayList<>();
        int total = getTotalCounter(pc);
        for(int i = 0; i <= CRITICAL_FAILURE; i++){
            List<Integer> entry = new ArrayList<>();
            if(i == 0)
                entry.add(1);
            else
                entry.add(boundaries.get(i-1).get(1)+1);

            switch(i){
                case CRITICAL_SUCCESS:
                    entry.add((int)(total * CRITICAL_SUCCESS_FRACTION));
                    break;
                case BIG_SUCCESS:
                    entry.add((int)(total * BIG_SUCCESS_FRACTION));
                    break;
                case NORMAL_SUCCESS:
                    entry.add(total-1);
                    break;
                case FLOW:
                    entry.add(total);
                    break;
                case FAILURE:
                    entry.add(99);
                    break;
                case CRITICAL_FAILURE:
                    entry.add(100);
                    break;
                default:
                    return null;
            }
            boundaries.add(entry);
        }
        return boundaries;
    }

    public Skill(){
        this("", PlayerCharacter.MISSING_ATTRIBUTE);
    }

    public Skill(String id, int parentAttribute){
        this(id, parentAttribute, MIN_SPENTPOINTS);
    }

    public Skill(String id, int parentAttribute, int spentPoints){
        this.setId(id);
        this.setSpentPoints(spentPoints);
        this.setParentAttribute(parentAttribute);
    }
}
