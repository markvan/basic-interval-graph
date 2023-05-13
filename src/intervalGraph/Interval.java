package intervalGraph;

// class to contain an interval used for input from file

public class Interval {
    final String name;
    final int start;
    final int end;

    public Interval(String n, int s, int e){
        name = n;
        start = s;
        end = e;
    }


    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Interval that = (Interval) other;
        return name.equals(that.name) && start == that.start && end == that.end;
    }

}
