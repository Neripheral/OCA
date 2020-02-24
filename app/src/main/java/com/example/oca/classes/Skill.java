package com.example.oca.classes;

public class Skill {
    private String id;
    private int counter;

    public String getId() {
        return this.id;
    }

    public Skill setId(String id) {
        this.id = id;
        return this;
    }

    public int getCounter() {
        return counter;
    }

    public Skill setCounter(int counter) {
        this.counter = counter;
        return this;
    }

    public Skill(){
        this("", 0);
    }

    public Skill(String id, int counter){
        this.setId(id);
        this.setCounter(counter);
    }
}
