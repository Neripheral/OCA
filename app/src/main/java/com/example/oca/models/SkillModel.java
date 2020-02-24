package com.example.oca.models;

import com.example.oca.classes.Skill;

public class SkillModel implements Comparable<SkillModel>{
    private Skill skillData;
    private String title;

    public SkillModel(String id, int counter, String title){
        this(new Skill(id, counter), title);
    }

    public SkillModel(Skill skillData, String title) {
        this.setSkillData(skillData);
        this.setTitle(title);
    }

    private Skill getSkillData() {
        return skillData;
    }

    private SkillModel setSkillData(Skill skillData) {
        this.skillData = skillData;
        return this;
    }

    public String getId(){
        return getSkillData().getId();
    }

    public SkillModel setId(String id){
        this.getSkillData().setId(id);
        return this;
    }

    public int getCounter(){
        return getSkillData().getCounter();
    }

    public SkillModel setCounter(int counter){
        this.getSkillData().setCounter(counter);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SkillModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public int compareTo(SkillModel other){
        String myStr = this.getTitle();
        String otherStr = other.getTitle();
        return myStr.compareTo(otherStr);
    }
}
