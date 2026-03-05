package com.guelph.engg1420finalprojectjavafx;

public class Concert extends Event {
    private int ageRestriction;

    //    constructor
    public Concert(String title, String location, int capacity, int ageRestriction) {
        super(title, location, capacity);
        setAgeRestriction((ageRestriction));
    }

    //    setter
    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    //    getter
    public int getAgeRestriction() {
        return this.ageRestriction;
    }
}