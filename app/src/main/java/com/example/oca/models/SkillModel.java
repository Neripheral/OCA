package com.example.oca.models;

public class SkillModel implements Comparable<SkillModel>{
    private int totalCounter;
    private int spentPointsCounter;
    private String id;
    private String title;
    private int parentAttributeImageId;
    private boolean detailsVisible;

    public int getTotalCounter() {
        return totalCounter;
    }
    public SkillModel setTotalCounter(int totalCounter){
            this.totalCounter = totalCounter;
            return this;
    }
    public int getSpentPointsCounter(){
        return spentPointsCounter;
    }
    public SkillModel setSpentPointsCounter(int spentPointsCounter){
        this.spentPointsCounter = spentPointsCounter;
        return this;
    }
    public String getId(){
        return id;
    }
    public SkillModel setId(String id){
        this.id = id;
        return this;
    }
    public String getTitle() {
        return title;
    }
    public SkillModel setTitle(String title) {
        this.title = title;
        return this;
    }
    public boolean areDetailsVisible() {
        return detailsVisible;
    }
    public SkillModel setDetailsVisible(boolean detailsVisible) {
        this.detailsVisible = detailsVisible;
        return this;
    }
    public int getParentAttributeImageId() {
        return parentAttributeImageId;
    }
    public SkillModel setParentAttributeImageId(int parentAttributeImageId) {
        this.parentAttributeImageId = parentAttributeImageId;
        return this;
    }

    @Override
    public int compareTo(SkillModel other){
        String myStr = this.getTitle();
        String otherStr = other.getTitle();
        return myStr.compareTo(otherStr);
    }

    public SkillModel(int totalCounter, int spentPointsCounter, String id, String title, int parentSkillImageId){
        setTotalCounter(totalCounter);
        setSpentPointsCounter(spentPointsCounter);
        setId(id);
        setTitle(title);
        setDetailsVisible(false);
        setParentAttributeImageId(parentSkillImageId);
    }
}
