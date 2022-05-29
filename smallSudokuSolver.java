import java.util.*;

public class smallSudokuSolver {

    List<Integer> board;
    List<Integer> zeroes;
    HashMap<Integer, rcb[]> map;
    int highestUnknown;

    public void initialize(int[] r1, int[] r2, int[] r3, int[] r4, HashMap<Integer, rcb[]> r) throws IllegalArgumentException { // makes 1D array of board
        board = new ArrayList();
        zeroes = new ArrayList();
        map = new HashMap();
        map = r;

        for (int i = 0; i < 4; i++) {
            board.add(r1[i]);
        }
        for (int i = 0; i < 4; i++) {
            board.add(r2[i]);
        }
        for (int i = 0; i < 4; i++) {
            board.add(r3[i]);
        }
        for (int i = 0; i < 4; i++) {
            board.add(r4[i]);
        }

        for (int k = 0; k < 16; k++) {
            if (board.get(k) == 0) zeroes.add(k);
        }

        if (Collections.max(board) > 4 || Collections.min(board) < 0) {
            throw new IllegalArgumentException("Clues must be in range 1-4");
        }

        highestUnknown = Collections.max(zeroes);
    }

    public void update(int square, int num) { // sets square as num
        board.set(square, num);

    }

    public boolean recurse(int j) {

        //System.out.println(j);

        if (board.get(highestUnknown) != 0) return true;
        if (j >= zeroes.size()) return true;

        Queue<Integer> candidates = new LinkedList();

        for (int k = 1; k <= 4; k++) {
            HashSet<Integer> surround = new HashSet();
            for (rcb x : map.get(zeroes.get(j))) {
                surround.addAll(x.included);
            }
            if (!surround.contains(k)) candidates.add(k);
        }

        if (candidates.isEmpty()) return false;

        boolean works = true;

        while (!candidates.isEmpty()) {
            works = true;
            int current = candidates.remove();
            update(zeroes.get(j), current);
            for (rcb x : map.get(zeroes.get(j))) {
                x.included.add(current);
            }

            if (recurse(j+1) == false) {
                update(zeroes.get(j), 0);
                for (rcb x : map.get(zeroes.get(j))) {
                    x.included.remove(current);
                }
                works = false;
                continue;
            }
            break;
        }

        if (works == false) return false;
        return true;
    }

    public boolean isSolved() {
        return board.get(highestUnknown) != 0;
    }

    public void print() {
        System.out.println(board);
    }

    public void printSudoku() {
        System.out.println();
        System.out.println(board.subList(0, 4));
        System.out.println(board.subList(4, 8));
        System.out.println(board.subList(8, 12));
        System.out.println(board.subList(12, 16));
        System.out.println();
    }


    public static void main(String[] args) throws Exception {

        int[] row1 = new int[] {0, 1, 0, 0};
        int[] row2 = new int[] {0, 0, 2, 0};
        int[] row3 = new int[] {0, 0, 0, 3};
        int[] row4 = new int[] {4, 0, 0, 0};
        
        if (row1.length != 4 || row2.length != 4 || row3.length != 4 || row4.length != 4) {
            throw new IllegalArgumentException("All arrays must be length 4");
        }

        HashSet<Integer> temp = new HashSet(); // initializes rcb

        for (int i = 0; i < 4; i++) {
            temp.add(row1[i]);
        }

        rcb r1 = new rcb(temp);
        temp.clear();

        for (int i = 4; i < 8; i++) {
            temp.add(row2[i-4]);
        }

        rcb r2 = new rcb(temp);
        temp.clear();

        for (int i = 8; i < 12; i++) {
            temp.add(row3[i-8]);
        }

        rcb r3 = new rcb(temp);
        temp.clear();

        for (int i = 12; i < 16; i++) {
            temp.add(row4[i-12]);
        }

        rcb r4 = new rcb(temp);
        temp.clear();

        // initialize column rcb

        temp.add(row1[0]);
        temp.add(row2[0]);
        temp.add(row3[0]);
        temp.add(row4[0]);

        rcb c1 = new rcb(temp);
        temp.clear();

        temp.add(row1[1]);
        temp.add(row2[1]);
        temp.add(row3[1]);
        temp.add(row4[1]);

        rcb c2 = new rcb(temp);
        temp.clear();

        temp.add(row1[2]);
        temp.add(row2[2]);
        temp.add(row3[2]);
        temp.add(row4[2]);

        rcb c3 = new rcb(temp);
        temp.clear();

        temp.add(row1[3]);
        temp.add(row2[3]);
        temp.add(row3[3]);
        temp.add(row4[3]);

        rcb c4 = new rcb(temp);
        temp.clear();

        // initialize block rcb

        temp.add(row1[0]);
        temp.add(row1[1]);
        temp.add(row2[0]);
        temp.add(row2[1]);

        rcb b1 = new rcb(temp);
        temp.clear();

        temp.add(row1[2]);
        temp.add(row1[3]);
        temp.add(row2[2]);
        temp.add(row2[3]);

        rcb b2 = new rcb(temp);
        temp.clear();

        temp.add(row3[0]);
        temp.add(row3[1]);
        temp.add(row4[0]);
        temp.add(row4[1]);

        rcb b3 = new rcb(temp);
        temp.clear();

        temp.add(row3[2]);
        temp.add(row3[3]);
        temp.add(row4[2]);
        temp.add(row4[3]);

        rcb b4 = new rcb(temp);
        temp.clear();

        rcb[] allrcb = new rcb[] {r1, r2, r3, r4, c1, c2, c3, c4, b1, b2, b3, b4};

        HashMap<Integer, rcb[]> rcbMap = new HashMap();

        rcbMap.put(0, new rcb[] {r1, c1, b1});
        rcbMap.put(1, new rcb[] {r1, c2, b1});
        rcbMap.put(2, new rcb[] {r1, c3, b2});
        rcbMap.put(3, new rcb[] {r1, c4, b2});
        rcbMap.put(4, new rcb[] {r2, c1, b1});
        rcbMap.put(5, new rcb[] {r2, c2, b1});
        rcbMap.put(6, new rcb[] {r2, c3, b2});
        rcbMap.put(7, new rcb[] {r2, c4, b2});
        rcbMap.put(8, new rcb[] {r3, c1, b3});
        rcbMap.put(9, new rcb[] {r3, c2, b3});
        rcbMap.put(10, new rcb[] {r3, c3, b4});
        rcbMap.put(11, new rcb[] {r3, c4, b4});
        rcbMap.put(12, new rcb[] {r4, c1, b3});
        rcbMap.put(13, new rcb[] {r4, c2, b3});
        rcbMap.put(14, new rcb[] {r4, c3, b4});
        rcbMap.put(15, new rcb[] {r4, c4, b4});

        smallSudokuSolver game = new smallSudokuSolver();

        game.initialize(row1, row2, row3, row4, rcbMap);
        
        for (rcb x : allrcb) {
            x.printSet();
        }
        

    
        game.recurse(0);
        game.printSudoku();
        
        if (game.isSolved()) {
            System.out.println("Solved!");
        }
        else System.out.println("No solution found");
    }
}