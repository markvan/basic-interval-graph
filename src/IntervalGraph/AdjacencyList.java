package IntervalGraph;

import java.util.*;

/** The AdjacencyList class implements an interval graph<br><br>
 *
 *In a more sophisticated approach where one could choose between implementations
 * IntervalGraph might be an (abstract) interface with AdjacencyList as one of the implementations
*/

public class AdjacencyList {
    final boolean debugMessages = false;
    final boolean consistencyCheckMessages = false;

    private HashMap<Interval, List<Interval>> adjacencyLists = new HashMap<>();

    /**
     * constructor for an empty graph, works because of instance variable assignment
     */
    public AdjacencyList () { }

    /**
     * constructor for an AdjacencyList
     * @param intervalList  a list of intervals forming the interval graph, may be null
     */
    public AdjacencyList(List<Interval> intervalList) {
        if (intervalList != null) {// make one adjacency lists for each interval in the Hashmap adjacencyLists
            for(Interval interval : intervalList) {
                // create an adjacency list
                List<Interval> intersectIntervals = new ArrayList<>();
                // stick the adjacency list in the hashmap indexed by the interval itself
                adjacencyLists.put(interval, intersectIntervals);
                // now in that adjacency list add all the overlapping intervals
                for (Interval innerInterval : intervalList) {
                    // todo clean up this test
                    if (interval != innerInterval && intervalsOverlap(interval, innerInterval)) {
                        // making a duplicate interval here so if anyone messes with the original the
                        // AdjacencyList being instantiated is still OK
                        intersectIntervals.add(innerInterval);
                    }
                }
            }
        }
    }

     /**
     * add an interval to the graph, includes a check that the name is not already used in the graph
     * @param newInterval interval to add
     */
    public void addInterval(Interval newInterval) {
        //todo check interval is not in graph already

        // create a copy so no one can mess with the graph
        //Interval newInterval = new Interval(newInterval);

        if ( intervalNameIsDuplicate(newInterval) ) {
                System.err.println("**************** Error: addInterval is called  with duplicate interval name: " +
                        newInterval.toString() );
        } else {
            safelyAddInterval(newInterval);
        }
    }

    /**
     * add an interval to the graph after check that the name is not already used in the graph
     * @param newInterval  interval to add
     */
    private void safelyAddInterval(Interval newInterval) {
        // get all the intervals before the addition
        Set<Interval> preExistingIntervals = getIntervals();
        // create an overlap list for newInterval
        List<Interval> intersectIntervals = new ArrayList<>();
        // stick the adjacency list in the hashmap, indexed by the interval
        adjacencyLists.put(newInterval, intersectIntervals);
        // now in that adjacency list add all the overlapping intervals
        for(Interval oldInterval : preExistingIntervals) {
            if( /* (!(oldInterval.equals(newInterval))) && */ intervalsOverlap(newInterval, oldInterval)) {
                if (debugMessages) {
                    printTwoIntervals("Existing interval", oldInterval, "overlaps new interval", newInterval);
                }
                // making a duplicate interval here so if anyone messes with the original the
                // AdjacencyList being instantiated is still OK
                intersectIntervals.add(oldInterval);
                // add overlapping new interval to preexisting interval's overlapping intervals list
                adjacencyLists.get(oldInterval).add(newInterval);
            }
        }
    }

    /**
     * return the names of the intervals in the interval graph<br>
     * in lexicographic order
     *
     * @return a Set&lt;String&gt; set of interval names, sorted
     */
    public TreeSet<String> getIntervalNames() {
        TreeSet<String> retSet = new TreeSet<>();
        (adjacencyLists.keySet()).forEach((i) -> retSet.add(i.getName()));
        return retSet;
    }

    /**
     * return the intervals in the interval graph
     *
     * @return a Set&lt;Interval&gt; set of interval names, sorted
     */
    public Set<Interval> getIntervals() {
        TreeSet<Interval> retSet = new TreeSet<>();
        // copy the intervals is not needed here, they are pointed ot internally in Interval list
        (adjacencyLists.keySet()).forEach((i) -> retSet.add(i));
        return retSet;
    }

    /**
     * getOverlappingtervals returns a list of intervals that overlap the named interval
     * @param  name of the interval whose overlapping intervals are wanted - String
     * @return list of Intervals that overlap the named interval
     */
    public List<Interval> getOverlappingIntervals(String name)  {
        Set<Interval> intervals = getIntervals();
        Interval interval = null;
        for (Interval i : intervals) {
            if (Objects.equals(name, i.getName())) {
                interval = i;
            }
        }
        // did we find an interval for the supplied interval's name?
        if (interval == null) {
            System.err.println ("Non-existent interval name"+name+"  supplied to AdjacencyList getOverlappingtervals");
            return null;
        }
        //  supply a copy of a list of overlapping intervals for safety, maintains encapsulation
        // just in case a caller of this method messes with the returned the list
        List<Interval> overlappingIntervalsList = adjacencyLists.get(interval);
        List<Interval> retOverlappingIntervalsList = new ArrayList<Interval>();
        for (Interval i : overlappingIntervalsList) {
            retOverlappingIntervalsList.add( new Interval(i) );
        }
        // if there are no overlapping intervals retOverlappingIntervalsList == null
        // but want to return an empty list
        if (retOverlappingIntervalsList == null) {
            retOverlappingIntervalsList = new ArrayList<Interval>();
        }
        return retOverlappingIntervalsList;
    }

    /** intervalsOverlap returns a bool to indicate if two intervals overlap
     * @param interval1 first interval
     * @param interval2 second interval
     * @return true or false depending on if overlap is found
     */
    //not crazy about this being public but need to test it
    static public boolean intervalsOverlap(Interval interval1, Interval interval2) {
        return ( interval1.getEnd() > interval2.getStart() && interval1.getStart() < interval2.getEnd() );
    }

    /**
     * @param intervalName - name of the interval wanted
     * @return the interval with the name, null if there is no interval with that name
     */
    public Interval getIntervalFromName(String intervalName) {
        Set<Interval> intervals = getIntervals();
        for (Interval i : intervals) {
            if (intervalName.equals(i.getName())) {
                return i;
            }
        }
        return null;
    }

    /**
     * @return return the number of intervals in the graph
     */
    public int size() {
        return adjacencyLists.size();
    }

    /**
     * @param intervalForNameCheck the interval whose name must be checked for uniqueness in the graph
     * @return true if the name is not in the graph, false otherwise
     */
    public boolean intervalNameIsNotDuplicate(Interval intervalForNameCheck) {
        return ! intervalNameIsDuplicate(intervalForNameCheck);
    }

    /**
     * @param intervalForNameCheck the interval whose name must be checked for duplication in the graph
     * @return true if the name is duplicated in the graph, false otherwise
     */
    public boolean intervalNameIsDuplicate(Interval intervalForNameCheck) {
        TreeSet<String> intervalNames = getIntervalNames();
        String nameToCheck = intervalForNameCheck.getName();
        return intervalNames.contains(nameToCheck);
    }

    /**
     * Prints two intervals, each proceed by interval description
     * @param description1
     * @param interval1
     * @param description2
     * @param interval2
     */
    private void printTwoIntervals(String description1, Interval interval1, String description2, Interval interval2) {
        System.out.println(description1 +" " + interval1.toString() + " "+ description2 +" "+ interval2.toString() );
    }

    public boolean isConsistent () {
        for (Interval interval : adjacencyLists.keySet()) {
            if (consistencyCheckMessages) System.out.println("Processing keyset interval " + interval.toString() + "\n" );
            List<Interval> inList = adjacencyLists.get(interval);
            List<Interval> outList = new ArrayList<>();
            for (Interval i : getIntervals()) {
                if (!inList.contains(i))
                    outList.add(i);
            }
            // outlist will contain interval, this is good,
            // interval should not have interval on its overlapping interval list
            for (Interval i : inList) {
                if (consistencyCheckMessages) System.out.println("   processing inList interval " + i.toString() );
                if (!intervalsOverlap(i, interval)) return false;
                if (!adjacencyLists.get(i).contains(interval)) return false;
            }
            for (Interval i : outList) {
                if (consistencyCheckMessages) System.out.println("   processing outList interval " + i.toString() );

                if (i != interval && intervalsOverlap(i, interval)) return false;
                if (adjacencyLists.get(i).contains(interval)) return false;
            }
            if (consistencyCheckMessages) System.out.println("\n End keyset interval " + interval.toString() + "\n" );
        }
        return true;
    }

}