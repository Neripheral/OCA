package com.nerpage.oca.classes;

public class Status {
    //================================================================================
    // Inner classes
    //================================================================================

    public enum Type{
        SCRATCH,
        WOUND,
        DEEP_WOUND;
    }

    //================================================================================
    // Fields
    //================================================================================

    public static final int UNDEFINED_TIME = -1;

    private Status.Type type;
    private int timeLeft;

    //================================================================================
    // Accessors
    //================================================================================

    public Type getType() {
        return type;
    }

    public Status setType(Type type) {
        this.type = type;
        return this;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public Status setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Status(Type type, int remainingTime){
        this.setType(type);
        this.setTimeLeft(remainingTime);
    }

    public Status(Type type){
        this(type, this.UNDEFINED_TIME);
    }

    //================================================================================
    // Methods
    //================================================================================


}
