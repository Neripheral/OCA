package com.nerpage.oca.models;

import com.nerpage.oca.R;

public class AttributeModel{
    public final int MISSING_BALANCE = 100;

    private int counter;
    private String title;
    private int imageId;
    private String description;
    private String counterDescription;
    private int parentImageId;
    private int balance;
    private boolean committed;



    private int balanceColor;


    public int getCounter() {
        return counter;
    }

    public AttributeModel setCounter(int counter) {
        this.counter = counter;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AttributeModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getImageId() {
        return imageId;
    }

    public AttributeModel setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    public String getDescription(){ return description; }

    public AttributeModel setDescription(String description){
        this.description = description;
        return this;
    }

    public String getCounterDescription() {
        return counterDescription;
    }

    public AttributeModel setCounterDescription(String counterDescription) {
        this.counterDescription = counterDescription;
        return this;
    }

    public int getParentImageId(){ return parentImageId; }

    public AttributeModel setParentImageId(int parentImageId){
        this.parentImageId = parentImageId;
        return this;
    }

    public int getBalance(){
        return balance;
    }

    public AttributeModel setBalance(int balance){
        this.balance = balance;
        return this;
    }

    public int getBalanceColor() {
        return balanceColor;
    }

    public AttributeModel setBalanceColor(int balanceColor) {
        this.balanceColor = balanceColor;
        return this;
    }

    public boolean isCommitted() {
        return committed;
    }

    public AttributeModel setCommitted(boolean committed) {
        this.committed = committed;
        return this;
    }

    public AttributeModel(){
        this(0, "", 0, "", "", 0, 0, 0, false);
        setBalance(MISSING_BALANCE);
        setBalanceColor(R.color.mainFont);
    }

    public AttributeModel(int counter, String title, int imageId, String description, String counterDescription, int parentImageId, int balance, int balanceColor, boolean commited){
        this.setCounter(counter);
        this.setTitle(title);
        this.setImageId(imageId);
        this.setDescription(description);
        this.setCounterDescription(counterDescription);
        this.setParentImageId(parentImageId);
        this.setBalance(balance);
        this.setBalanceColor(balanceColor);
        this.setCommitted(commited);
    }
}