package com.example.oca.models;

public class AttributeModel{
    private int counter;
    private String title;
    private int imageId;


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

    public AttributeModel(int counter, String title, int imageId){
        this.setCounter(counter);
        this.setTitle(title);
        this.setImageId(imageId);
    }
}