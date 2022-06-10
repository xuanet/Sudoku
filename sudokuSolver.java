import java.util.*;
import java.text.*;

/* Takes a 9x9 sudoku puzzle and solves it */

public class sudokuSolver {

    List<Integer> board;            // List representation of sudoku
    List<Integer> zeroes;           // Indices where there are no clues; indices where recurse() is called
    HashMap<Integer, rcb[]> map;    // Associates each of the 81 cells to revelant rcbs
    int highestUnknown;             // Index of the highest clueless cell, used to terminate recurse()

    public void initialize(int[] r1, int[] r2, int[] r3, int[] r4, int[] r5, int[] r6, int[] r7, int[] r8, int[] r9) throws IllegalArgumentException { // makes 1D array of board
        int[][] rows = new int[][] {r1, r2, r3, r4, r5, r6, r7, r8, r9};
        board = new ArrayList();
        zeroes = new ArrayList();
        map = new HashMap();

        rcb[] rcbRows = new rcb[9];
        rcb[] rcbCols = new rcb[9];
        rcb[] rcbBlocks = new rcb[9];

        for (int i = 0; i < 9; i++) {
            HashSet<Integer> rcbCluesRow = new HashSet();
            HashSet<Integer> rcbCluesCol = new HashSet();
            for (int j = 0; j < 9; j++) {
                int num = rows[i][j];
                rcbCluesRow.add(num);
                rcbCluesCol.add(rows[j][i]);
                board.add(num);
                if (num == 0) zeroes.add(9*i + j);
            }
            rcbRows[i] = new rcb(rcbCluesRow);
            rcbCols[i] = new rcb(rcbCluesCol);
        }

        if (Collections.max(board) > 9 || Collections.min(board) < 0) {
            throw new IllegalArgumentException("Clues must be in range 1-9");
        }

        highestUnknown = Collections.max(zeroes);

        HashSet<Integer> rcbCluesBlock = new HashSet();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[0] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 6; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[1] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 6; j < 9; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[2] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[3] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[4] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        for (int i = 3; i < 6; i++) {
            for (int j = 6; j < 9; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[5] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        for (int i = 6; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[6] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        for (int i = 6; i < 9; i++) {
            for (int j = 3; j < 6; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[7] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                rcbCluesBlock.add(rows[i][j]);
            }
        }
        rcbBlocks[8] = new rcb(rcbCluesBlock);
        rcbCluesBlock.clear();

        HashMap<Integer, rcb[]> rcbMap = new HashMap();

        for (int i = 0; i < 81; i++) {
            rcbMap.put(i, new rcb[] {rcbRows[i/9], rcbCols[i%9], null});
        }

        rcbMap.get(0)[2] = rcbBlocks[0];
        rcbMap.get(1)[2] = rcbBlocks[0];
        rcbMap.get(2)[2] = rcbBlocks[0];
        rcbMap.get(3)[2] = rcbBlocks[1];
        rcbMap.get(4)[2] = rcbBlocks[1];
        rcbMap.get(5)[2] = rcbBlocks[1];
        rcbMap.get(6)[2] = rcbBlocks[2];
        rcbMap.get(7)[2] = rcbBlocks[2];
        rcbMap.get(8)[2] = rcbBlocks[2];
        
        rcbMap.get(9)[2] = rcbBlocks[0];
        rcbMap.get(10)[2] = rcbBlocks[0];
        rcbMap.get(11)[2] = rcbBlocks[0];
        rcbMap.get(12)[2] = rcbBlocks[1];
        rcbMap.get(13)[2] = rcbBlocks[1];
        rcbMap.get(14)[2] = rcbBlocks[1];
        rcbMap.get(15)[2] = rcbBlocks[2];
        rcbMap.get(16)[2] = rcbBlocks[2];
        rcbMap.get(17)[2] = rcbBlocks[2];
        
        rcbMap.get(18)[2] = rcbBlocks[0];
        rcbMap.get(19)[2] = rcbBlocks[0];
        rcbMap.get(20)[2] = rcbBlocks[0];
        rcbMap.get(21)[2] = rcbBlocks[1];
        rcbMap.get(22)[2] = rcbBlocks[1];
        rcbMap.get(23)[2] = rcbBlocks[1];
        rcbMap.get(24)[2] = rcbBlocks[2];
        rcbMap.get(25)[2] = rcbBlocks[2];
        rcbMap.get(26)[2] = rcbBlocks[2];
        
        rcbMap.get(27)[2] = rcbBlocks[3];
        rcbMap.get(28)[2] = rcbBlocks[3];
        rcbMap.get(29)[2] = rcbBlocks[3];
        rcbMap.get(30)[2] = rcbBlocks[4];
        rcbMap.get(31)[2] = rcbBlocks[4];
        rcbMap.get(32)[2] = rcbBlocks[4];
        rcbMap.get(33)[2] = rcbBlocks[5];
        rcbMap.get(34)[2] = rcbBlocks[5];
        rcbMap.get(35)[2] = rcbBlocks[5];
        
        rcbMap.get(36)[2] = rcbBlocks[3];
        rcbMap.get(37)[2] = rcbBlocks[3];
        rcbMap.get(38)[2] = rcbBlocks[3];
        rcbMap.get(39)[2] = rcbBlocks[4];
        rcbMap.get(40)[2] = rcbBlocks[4];
        rcbMap.get(41)[2] = rcbBlocks[4];
        rcbMap.get(42)[2] = rcbBlocks[5];
        rcbMap.get(43)[2] = rcbBlocks[5];
        rcbMap.get(44)[2] = rcbBlocks[5];
        
        rcbMap.get(45)[2] = rcbBlocks[3];
        rcbMap.get(46)[2] = rcbBlocks[3];
        rcbMap.get(47)[2] = rcbBlocks[3];
        rcbMap.get(48)[2] = rcbBlocks[4];
        rcbMap.get(49)[2] = rcbBlocks[4];
        rcbMap.get(50)[2] = rcbBlocks[4];
        rcbMap.get(51)[2] = rcbBlocks[5];
        rcbMap.get(52)[2] = rcbBlocks[5];
        rcbMap.get(53)[2] = rcbBlocks[5];
        
        rcbMap.get(54)[2] = rcbBlocks[6];
        rcbMap.get(55)[2] = rcbBlocks[6];
        rcbMap.get(56)[2] = rcbBlocks[6];
        rcbMap.get(57)[2] = rcbBlocks[7];
        rcbMap.get(58)[2] = rcbBlocks[7];
        rcbMap.get(59)[2] = rcbBlocks[7];
        rcbMap.get(60)[2] = rcbBlocks[8];
        rcbMap.get(61)[2] = rcbBlocks[8];
        rcbMap.get(62)[2] = rcbBlocks[8];
        
        rcbMap.get(63)[2] = rcbBlocks[6];
        rcbMap.get(64)[2] = rcbBlocks[6];
        rcbMap.get(65)[2] = rcbBlocks[6];
        rcbMap.get(66)[2] = rcbBlocks[7];
        rcbMap.get(67)[2] = rcbBlocks[7];
        rcbMap.get(68)[2] = rcbBlocks[7];
        rcbMap.get(69)[2] = rcbBlocks[8];
        rcbMap.get(70)[2] = rcbBlocks[8];
        rcbMap.get(71)[2] = rcbBlocks[8];
        
        rcbMap.get(72)[2] = rcbBlocks[6];
        rcbMap.get(73)[2] = rcbBlocks[6];
        rcbMap.get(74)[2] = rcbBlocks[6];
        rcbMap.get(75)[2] = rcbBlocks[7];
        rcbMap.get(76)[2] = rcbBlocks[7];
        rcbMap.get(77)[2] = rcbBlocks[7];
        rcbMap.get(78)[2] = rcbBlocks[8];
        rcbMap.get(79)[2] = rcbBlocks[8];
        rcbMap.get(80)[2] = rcbBlocks[8];

        map = rcbMap;
    }

    public void update(int square, int num) { // sets square as num
        board.set(square, num);
    }

    public boolean recurse(int j) {
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

        while (!candidates.isEmpty()) {
            int current = candidates.remove();
            for (rcb x : map.get(zeroes.get(j))) {
                x.included.add(current);
            }

            if (recurse(j+1)) {
                update(zeroes.get(j), current);
                break;
            } 

            for (rcb x : map.get(zeroes.get(j))) x.included.remove(current);
            if (candidates.isEmpty()) return false;
        }

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
}

