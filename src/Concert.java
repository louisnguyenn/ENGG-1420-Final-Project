public class Concert extends Event {
    private int ageRestriction;

//    constructor
    public Concert(int ageRestriction) {
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