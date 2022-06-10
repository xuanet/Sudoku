import java.util.*;

/* An rcb is any row, column, or block associated with the sudoku. 
Before a number is placed in an empty cell, the number is checked 
to see if it's already in the included set of relevant rcbs*/

public class rcb {

    HashSet<Integer> included; // numbers currently in rcb

    public rcb(HashSet<Integer> hs) {
        included = new HashSet();
        included.addAll(hs);
        included.remove(0);
    }

    public void printSet() {
        System.out.println(included); // prints numbers in rcb for debugging
    }
}
