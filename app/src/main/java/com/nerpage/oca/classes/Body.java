package com.nerpage.oca.classes;

import android.content.Context;

import java.util.List;

public class Body {
    //================================================================================
    // Fields
    //================================================================================

    private List<Status> statuses;
    private int nameId;
    private List<Body> subbodies;

    //================================================================================
    // Accessors
    //================================================================================

    public List<Status> getStatuses() {
        return statuses;
    }

    public Body setStatuses(List<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public int getNameId() {
        return nameId;
    }

    public Body setNameId(int nameId) {
        this.nameId = nameId;
        return this;
    }

    public List<Body> getSubbodies() {
        return subbodies;
    }

    public Body setSubbodies(List<Body> subbodies) {
        this.subbodies = subbodies;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Body(){}

    //================================================================================
    // Methods
    //================================================================================

    public String getName(Context context){
        return context.getResources().getString(this.getNameId());
    }

    public List<Status> getBatchStatuses(){
        List<Status> statusList = this.getStatuses();
        for(Body subbody : this.getSubbodies()){
            statusList.addAll(subbody.getBatchStatuses());
        }
        return statusList;
    }

    public void applyStatus(Status status){
        this.getStatuses().add(status);
    }
}
