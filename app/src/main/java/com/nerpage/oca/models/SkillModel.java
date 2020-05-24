package com.nerpage.oca.models;

public class SkillModel implements Comparable<SkillModel>{
    private int totalCounter;
    private int spentPointsCounter;
    private String id;
    private String title;
    private int parentAttributeImageId;
    private boolean detailsVisible;

    private String criticalSuccessBoundaries;

    private String bigSuccessBoundaries;
    private String normalSuccessBoundaries;
    private String flowBoundaries;
    private String failureBoundaries;
    private String criticalFailureBoundaries;

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
    public String getCriticalSuccessBoundaries() {
        return criticalSuccessBoundaries;
    }
    public SkillModel setCriticalSuccessBoundaries(String criticalSuccessBoundaries) {
        this.criticalSuccessBoundaries = criticalSuccessBoundaries;
        return this;
    }
    public String getBigSuccessBoundaries() {
        return bigSuccessBoundaries;
    }
    public SkillModel setBigSuccessBoundaries(String bigSuccessBoundaries) {
        this.bigSuccessBoundaries = bigSuccessBoundaries;
        return this;
    }
    public String getNormalSuccessBoundaries() {
        return normalSuccessBoundaries;
    }
    public SkillModel setNormalSuccessBoundaries(String normalSuccessBoundaries) {
        this.normalSuccessBoundaries = normalSuccessBoundaries;
        return this;
    }
    public String getFlowBoundaries() {
        return flowBoundaries;
    }
    public SkillModel setFlowBoundaries(String flowBoundaries) {
        this.flowBoundaries = flowBoundaries;
        return this;
    }
    public String getFailureBoundaries() {
        return failureBoundaries;
    }
    public SkillModel setFailureBoundaries(String failureBoundaries) {
        this.failureBoundaries = failureBoundaries;
        return this;
    }
    public String getCriticalFailureBoundaries() {
        return criticalFailureBoundaries;
    }
    public SkillModel setCriticalFailureBoundaries(String criticalFailureBoundaries) {
        this.criticalFailureBoundaries = criticalFailureBoundaries;
        return this;
    }

    @Override
    public int compareTo(SkillModel other){
        String myStr = this.getTitle();
        String otherStr = other.getTitle();
        return myStr.compareTo(otherStr);
    }

    public SkillModel(int totalCounter, int spentPointsCounter, String id, String title, int parentSkillImageId, String criticalSuccessBoundaries, String bigSuccessBoundaries, String normalSuccessBoundaries, String flowBoundaries, String failureBoundaries, String criticalFailureBoundaries){
        setTotalCounter(totalCounter);
        setSpentPointsCounter(spentPointsCounter);
        setId(id);
        setTitle(title);
        setDetailsVisible(false);
        setParentAttributeImageId(parentSkillImageId);
        setCriticalSuccessBoundaries(criticalSuccessBoundaries);
        setBigSuccessBoundaries(bigSuccessBoundaries);
        setNormalSuccessBoundaries(normalSuccessBoundaries);
        setFlowBoundaries(flowBoundaries);
        setFailureBoundaries(failureBoundaries);
        setCriticalFailureBoundaries(criticalFailureBoundaries);
    }
}
