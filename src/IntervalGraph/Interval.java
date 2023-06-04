package IntervalGraph;

import  org.apache.commons.lang3.builder.HashCodeBuilder;


// class for interval objects

public final class Interval implements Comparable<Interval> {
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

    // this override of Object.equals is needed to get the interval graph to work and for tests
    // to get the hamcrest equality comparisons to work
    @Override
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

    @Override
    public int hashCode() {
        // Hash code builder from apache language commons needs two randomly chosen prime numbers
        return new HashCodeBuilder(17, 31).
                // this is not a derivation so no need for 'appendSuper(super.hashCode()).'
                append(name).
                append(start).
                append(end).
                toHashCode();
    }

    @Override
    public int compareTo(Interval otherInterval) {
        return name.compareTo(otherInterval.name);
    }



    // getters
    public String getName()  { return name; }
    public int    getStart() { return start; }
    public int    getEnd()    { return end; }

    public String toString() {
        return "[ \"" + getName() + "\", " + getStart() + ", " + getEnd() +" ]";
    }

}
