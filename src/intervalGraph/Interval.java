package intervalGraph;

// class for interval objects

public class Interval {
    final String name;
    final int start;
    final int end;

    public Interval(String n, int s, int e){
        name = n;
        start = s;
        end = e;
    }

    public Interval(Interval i) {
        name = i.getName();
        start = i.getStart();
        end = i.getEnd();
    }

    // this override of Object.equals is needed to get hamcrest equality comparisons to work
    @Override
    public boolean equals(Object otherObject) {
        // check if the two objects are the same object
        if (this == otherObject) return true;
        // check arguyment is an object, and that the classes of it and this don't differ
        if (otherObject == null || getClass() != otherObject.getClass()) return false;
        // type coersion to use getters in the next statement
        Interval otherInterval = (Interval) otherObject;
        return name.equals(otherInterval.name) && start == otherInterval.start && end == otherInterval.end;
    }

    // getters
    public String getName()  { return name; }
    public int    getStart() { return start; }
    public int    getEnd()    { return end; }

}
