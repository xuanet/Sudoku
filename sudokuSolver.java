import java.util.*;

public class sudokuSolver {

    List<Integer> board;
    List<Integer> zeroes;
    HashMap<Integer, rcb[]> map;
    int highestUnknown;

    public void initialize(int[] r1, int[] r2, int[] r3, int[] r4, int[] r5, int[] r6, int[] r7, int[] r8, int[] r9, HashMap<Integer, rcb[]> r) throws IllegalArgumentException { // makes 1D array of board
        board = new ArrayList();
        zeroes = new ArrayList();
        map = new HashMap();
        map = r;

        List<int[]> temp = new ArrayList();
        temp.add(r1);
        temp.add(r2);
        temp.add(r3);
        temp.add(r4);
        temp.add(r5);
        temp.add(r6);
        temp.add(r7);
        temp.add(r8);
        temp.add(r9);

        int currentRow = 0;

        while (currentRow < 9) {
            for (int i = 0; i < 9; i++) {
                board.add(temp.get(currentRow)[i]);
            }
            currentRow++;
        }

        for (int k = 0; k < 81; k++) {
            if (board.get(k) == 0) zeroes.add(k);
        }

        if (Collections.max(board) > 9 || Collections.min(board) < 0) {
            throw new IllegalArgumentException("Clues must be in range 1-9");
        }

        highestUnknown = Collections.max(zeroes);
    }

    public void update(int square, int num) { // sets square as num
        board.set(square, num);

    }

    public boolean recurse(int j) {

        if (board.get(highestUnknown) != 0) return true;
        if (j >= zeroes.size()) return true;

        Queue<Integer> candidates = new LinkedList();

        for (int k = 1; k <= 9; k++) {
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
        System.out.println(board.subList(0, 9));
        System.out.println(board.subList(9, 18));
        System.out.println(board.subList(18, 27));
        System.out.println(board.subList(27, 36));
        System.out.println(board.subList(36, 45));
        System.out.println(board.subList(45, 54));
        System.out.println(board.subList(54, 63));
        System.out.println(board.subList(63, 72));
        System.out.println(board.subList(72, 81));
        System.out.println();
    }


    public static void main(String[] args) throws Exception {

        /*int[] row1 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row2 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row3 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row4 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row5 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row6 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row7 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row8 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row9 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};*/

        int[] row1 = new int[] {0, 0, 0, 8, 7, 0, 0, 0, 3};
        int[] row2 = new int[] {3, 0, 0, 0, 0, 0, 1, 0, 7};
        int[] row3 = new int[] {0, 8, 0, 4, 0, 0, 0, 9, 0};
        int[] row4 = new int[] {0, 3, 0, 7, 0, 5, 0, 0, 0};
        int[] row5 = new int[] {8, 0, 0, 0, 0, 0, 0, 0, 9};
        int[] row6 = new int[] {0, 0, 0, 9, 0, 2, 0, 4, 0};
        int[] row7 = new int[] {0, 2, 0, 0, 0, 4, 0, 6, 0};
        int[] row8 = new int[] {4, 0, 6, 0, 0, 0, 0, 0, 5};
        int[] row9 = new int[] {1, 0, 0, 0, 5, 7, 0, 0, 0};
        
        
        if (row1.length != 9 || row2.length != 9 || row3.length != 9 || row4.length != 9 || row5.length != 9 || row6.length != 9 || row7.length != 9 || row8.length != 9 || row9.length != 9) {
            throw new IllegalArgumentException("All arrays must be length 9");
        }

        int[][] rows = new int[][] {row1, row2, row3, row4, row5, row5, row7, row8, row9};

        HashSet<Integer> temp = new HashSet(); // initializes rcb

        // initializating row rcb

        for (int i = 0; i < 9; i++) {
            temp.add(row1[i]);
        }

        rcb r1 = new rcb(temp);
        temp.clear();

        for (int i = 9; i < 18; i++) {
            temp.add(row2[i-9]);
        }

        rcb r2 = new rcb(temp);
        temp.clear();

        for (int i = 18; i < 27; i++) {
            temp.add(row3[i-18]);
        }

        rcb r3 = new rcb(temp);
        temp.clear();

        for (int i = 27; i < 36; i++) {
            temp.add(row4[i-27]);
        }

        rcb r4 = new rcb(temp);
        temp.clear();

        for (int i = 36; i < 45; i++) {
            temp.add(row5[i-36]);
        }

        rcb r5 = new rcb(temp);
        temp.clear();

        for (int i = 45; i < 54; i++) {
            temp.add(row6[i-45]);
        }

        rcb r6 = new rcb(temp);
        temp.clear();

        for (int i = 54; i < 63; i++) {
            temp.add(row7[i-54]);
        }

        rcb r7 = new rcb(temp);
        temp.clear();

        for (int i = 63; i < 72; i++) {
            temp.add(row8[i-63]);
        }

        rcb r8 = new rcb(temp);
        temp.clear();

        for (int i = 72; i < 81; i++) {
            temp.add(row9[i-72]);
        }

        rcb r9 = new rcb(temp);
        temp.clear();

        // initialize column rcb

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][0]);
        }

        rcb c1 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][1]);
        }

        rcb c2 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][2]);
        }

        rcb c3 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][3]);
        }

        rcb c4 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][4]);
        }

        rcb c5 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][5]);
        }

        rcb c6 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][6]);
        }

        rcb c7 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][7]);
        }

        rcb c8 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 9; i++) {
            temp.add(rows[i][8]);
        }

        rcb c9 = new rcb(temp);
        temp.clear();

        // initializing block rcb

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b1 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 6; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b2 = new rcb(temp);
        temp.clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 6; j < 9; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b3 = new rcb(temp);
        temp.clear();

        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b4 = new rcb(temp);
        temp.clear();

        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b5 = new rcb(temp);
        temp.clear();

        for (int i = 3; i < 6; i++) {
            for (int j = 6; j < 9; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b6 = new rcb(temp);
        temp.clear();

        for (int i = 6; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b7 = new rcb(temp);
        temp.clear();

        for (int i = 6; i < 9; i++) {
            for (int j = 3; j < 6; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b8 = new rcb(temp);
        temp.clear();

        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                temp.add(rows[i][j]);
            }
        }

        rcb b9 = new rcb(temp);
        temp.clear();

        rcb[] rcbRows = new rcb[] {r1, r2, r3, r4, r5, r6, r7, r8, r9};
        rcb[] rcbCols = new rcb[] {c1, c2, c3, c4, c5, c6, c7, c8, c9};
        rcb[] rcbBlocks = new rcb[] {b1, b2, b3, b4, b5, b6, b7, b8, b9};


        HashMap<Integer, rcb[]> rcbMap = new HashMap();

        for (int i = 0; i < 81; i++) {
            rcbMap.put(i, new rcb[] {rcbRows[i/9], rcbCols[i%9], null});
        }

        rcbMap.get(0)[2] = b1;
        rcbMap.get(1)[2] = b1;
        rcbMap.get(2)[2] = b1;
        rcbMap.get(3)[2] = b2;
        rcbMap.get(4)[2] = b2;
        rcbMap.get(5)[2] = b2;
        rcbMap.get(6)[2] = b3;
        rcbMap.get(7)[2] = b3;
        rcbMap.get(8)[2] = b3;
        rcbMap.get(9)[2] = b1;

        rcbMap.get(10)[2] = b1;
        rcbMap.get(11)[2] = b1;
        rcbMap.get(12)[2] = b2;
        rcbMap.get(13)[2] = b2;
        rcbMap.get(14)[2] = b2;
        rcbMap.get(15)[2] = b3;
        rcbMap.get(16)[2] = b3;
        rcbMap.get(17)[2] = b3;
        rcbMap.get(18)[2] = b1;

        rcbMap.get(19)[2] = b1;
        rcbMap.get(20)[2] = b1;
        rcbMap.get(21)[2] = b2;
        rcbMap.get(22)[2] = b2;
        rcbMap.get(23)[2] = b2;
        rcbMap.get(24)[2] = b3;
        rcbMap.get(25)[2] = b3;
        rcbMap.get(26)[2] = b3;
        rcbMap.get(27)[2] = b4;

        rcbMap.get(28)[2] = b4;
        rcbMap.get(29)[2] = b4;
        rcbMap.get(30)[2] = b5;
        rcbMap.get(31)[2] = b5;
        rcbMap.get(32)[2] = b5;
        rcbMap.get(33)[2] = b6;
        rcbMap.get(34)[2] = b6;
        rcbMap.get(35)[2] = b6;
        rcbMap.get(36)[2] = b4;

        rcbMap.get(37)[2] = b4;
        rcbMap.get(38)[2] = b4;
        rcbMap.get(39)[2] = b5;
        rcbMap.get(40)[2] = b5;
        rcbMap.get(41)[2] = b5;
        rcbMap.get(42)[2] = b6;
        rcbMap.get(43)[2] = b6;
        rcbMap.get(44)[2] = b6;
        rcbMap.get(45)[2] = b4;

        rcbMap.get(46)[2] = b4;
        rcbMap.get(47)[2] = b4;
        rcbMap.get(48)[2] = b5;
        rcbMap.get(49)[2] = b5;
        rcbMap.get(50)[2] = b5;
        rcbMap.get(51)[2] = b6;
        rcbMap.get(52)[2] = b6;
        rcbMap.get(53)[2] = b6;
        rcbMap.get(54)[2] = b7;

        rcbMap.get(55)[2] = b7;
        rcbMap.get(56)[2] = b7;
        rcbMap.get(57)[2] = b8;
        rcbMap.get(58)[2] = b8;
        rcbMap.get(59)[2] = b8;
        rcbMap.get(60)[2] = b9;
        rcbMap.get(61)[2] = b9;
        rcbMap.get(62)[2] = b9;
        rcbMap.get(63)[2] = b7;

        rcbMap.get(64)[2] = b7;
        rcbMap.get(65)[2] = b7;
        rcbMap.get(66)[2] = b8;
        rcbMap.get(67)[2] = b8;
        rcbMap.get(68)[2] = b8;
        rcbMap.get(69)[2] = b9;
        rcbMap.get(70)[2] = b9;
        rcbMap.get(71)[2] = b9;
        rcbMap.get(72)[2] = b7;

        rcbMap.get(73)[2] = b7;
        rcbMap.get(74)[2] = b7;
        rcbMap.get(75)[2] = b8;
        rcbMap.get(76)[2] = b8;
        rcbMap.get(77)[2] = b8;
        rcbMap.get(78)[2] = b9;
        rcbMap.get(79)[2] = b9;
        rcbMap.get(80)[2] = b9;

        sudokuSolver game = new sudokuSolver();

        game.initialize(row1, row2, row3, row4, row5, row6, row7, row8, row9, rcbMap);

        game.print();

        game.recurse(0);
        game.printSudoku();
        
        if (game.isSolved()) {
            System.out.println("Solved!");
        }
        else System.out.println("No solution found");
    }
}