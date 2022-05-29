import java.util.*;

public class rcb {

    HashSet<Integer> included; // numbers already in the rcb
    //HashMap<Integer, Integer> state; // current placement of numbers in rcb

    public rcb(HashSet<Integer> hs) {

        included = new HashSet();
        included.addAll(hs);
        included.remove(0);
        //state = new HashMap();
        

        /*state.putAll(hs);
        for (int i : hs.values()) {
            if (i != 0) included.add(i);
        }*/
    }

    public void printSet() {
        System.out.println(included);
    }

    /*public void printMap() {
        System.out.println(state);
    }*/
    
}
