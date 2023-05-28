package IntervalGraph;

import java.util.*;

/** The AdjacencyList class implements an interval graph<br><br>
 *
 *In a more sophisticated approach where one could choose between implementations
 * IntervalGraph might be an (abstract) interface with AdjacencyList as one of the implementations
*/

public class AdjacencyList {
    HashMap<Interval, List<Interval>> adjacencyLists = new HashMap<>();

    /**
     * constructor for an AdjacencyList, which is an implementation of an interval graph
     * @param intervalList  a list of intervals forming the interval graph - List&lt;Interval&gt;
     */
    public AdjacencyList(List<Interval> intervalList) {
        // make one adjacency lists for each interval in the Hashmap adjacencyLists
        for(Interval interval : intervalList) {
            // create an adjacency list
            List<Interval> intersectIntervals = new ArrayList<>();
            // stick the adjacency list in the hashmap indexed by the interval itself
            adjacencyLists.put(interval, intersectIntervals);
            // now in that adjacency list add all the overlapping intervals
            for(Interval innerInterval : intervalList) {
                // todo clean up this test
                if(interval != innerInterval && intervalsOverlap(interval, innerInterval)) {
                    // making a duplicate interval here so if anyone messes with the original the
                    // AdjacencyList being instantiated is still OK
                    intersectIntervals.add(new Interval(innerInterval));
                }
            }
        }
        int i = 1;
    }

    public void addInterval(Interval newInterval) {
        // create a copy so no one can mess with the graph
        Interval intervalToAdd = new Interval(newInterval);
        // get all the existing intervals
        Set<Interval> preExistingIntervals = getIntervals();

        // create an overlapping list for intervalToAdd
        List<Interval> intersectIntervals = new ArrayList<>();
        // stick the adjacency list in the hashmap, indexed by the interval
        adjacencyLists.put(intervalToAdd, intersectIntervals);
        // get all the intervals including the interval being added

        // now in that adjacency list add all the overlapping intervals
        for(Interval preExistingIntvl : preExistingIntervals) {
            if( (!(preExistingIntvl.equals(newInterval))) && intervalsOverlap(newInterval, preExistingIntvl)) {
                System.out.println("preExistingIntvl [" + preExistingIntvl.getName() + " " + preExistingIntvl.getStart() + " " +preExistingIntvl.getEnd() +"]     " +

                        "newInterval [" + newInterval.getName() + " " + newInterval.getStart() + " " +newInterval.getEnd() +"]     ");
                // making a duplicate interval here so if anyone messes with the original the
                // AdjacencyList being instantiated is still OK
                intersectIntervals.add(new Interval(preExistingIntvl));
                // add overlapping new interval to preexisting interval's overlapping intervals list
                adjacencyLists.get(preExistingIntvl).add(new Interval(newInterval));
            }
        }

        // add
    }

    /**
     * getIntervalNames - get the names of the intervals in the interval graph<br>
     * because a TreeSet is used for strings names are returned in lexicographic order
     *
     * @return a Set&lt;String&gt; set of interval names, sorted
     */

    public TreeSet<String> getIntervalNames() {
        TreeSet<String> retSet = new TreeSet<>();
        (adjacencyLists.keySet()).forEach((i) -> retSet.add(i.getName()));
        return retSet;
    }

    /**
     * getIntervalNames - get the intervals in the interval graph
     *
     * @return a Set&lt;Interval&gt; set of interval names, sorted
     */
    public Set<Interval> getIntervals() {
        return adjacencyLists.keySet();
    }

    /**
     * getOverlappingtervals returns a list of intervals that overlap the named interval
     * @param  name of the interval whose overlapping intervals are wanted - String
     * @return list of Intervals that overlap the named interval
     * @throws Exception if the named interval is not found - todo create subclass of Exception for this
     */
    public List<Interval> getOverlappingIntervals(String name) throws Exception {
        // need care here, eg in a test or when used, may be supplied with a interval that has the same values
        // but is not at the same memory address as the 'matching' key... which probably would make various
        // list operations fail, so retrieve the matching key object first
        Set<Interval> intervalNames = getIntervals();
        Interval interval = null;
        for (Interval i : intervalNames) {
            if (Objects.equals(name, i.getName())) {
                interval = i;
            }
        }
        // did we find an interval for the supplied interval's name?
        if (interval == null) {
            throw new Exception("Bad interval name supplied to AdjacencyList getOverlappingtervals");
        }
        //  supply a copy of a list of overlapping intervals for safety, maintains encapsulation
        List<Interval> overlappingIntervalsList = adjacencyLists.get(interval);
        List<Interval> retOverlappingIntervalsList = new ArrayList<Interval>();
        for (Interval i : overlappingIntervalsList) {
            retOverlappingIntervalsList.add(new Interval( i ));
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

    private void sort() {

    }

    boolean consistent () {
        return true;

    }
}