package IntervalGraph;

// class for interval objects

public class Interval {
    final private String name;
    final private int start;
    final private int end;

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
    public boolean equals (Object otherObject) {
        // check if the two objects are the same object, can never check null==null
        if (this == otherObject) return true;
        // includes check for otherObject null, null not instanceOf Interval
        if (!(otherObject instanceof Interval)) return false;
        Interval otherInterval = (Interval) otherObject;
        // not going to use Apache EqualsBuilder because simple types to test
        return name.equals(otherInterval.name) &&
                start == otherInterval.start && end == otherInterval.end;
    }

    // getters
    public String getName()  { return name; }
    public int    getStart() { return start; }
    public int    getEnd()    { return end; }

}
