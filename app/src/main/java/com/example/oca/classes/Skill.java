package com.example.oca.classes;

import android.content.Context;

import com.example.oca.R;

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
        int totalCounter = pc.MIN_SKILLLEVEL;

        totalCounter += 2 * pc.getAttribute(getParentAttribute());
        totalCounter += pc.getAttribute(getParentAttribute()) * getSpentPoints();

        return totalCounter;
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
