import java.text.*;

public class main {
    
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        /*int[] row1 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row2 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row3 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row4 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row5 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row6 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row7 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row8 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] row9 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};*/

        int[] row1 = new int[] {0, 0, 2, 0, 3, 0, 0, 0, 8};
        int[] row2 = new int[] {0, 0, 0, 0, 0, 8, 0, 0, 0};
        int[] row3 = new int[] {0, 3, 1, 0, 2, 0, 0, 0, 0};

        int[] row4 = new int[] {0, 6, 0, 0, 5, 0, 2, 7, 0};
        int[] row5 = new int[] {0, 1, 0, 0, 0, 0, 0, 5, 0};
        int[] row6 = new int[] {2, 0, 4, 0, 6, 0, 0, 3, 1};

        int[] row7 = new int[] {0, 0, 0, 0, 8, 0, 6, 0, 5};
        int[] row8 = new int[] {0, 0, 0, 0, 0, 0, 0, 1, 3};
        int[] row9 = new int[] {0, 0, 5, 3, 1, 0, 4, 0, 0};
        
        sudokuSolver game = new sudokuSolver();
        game.initialize(row1, row2, row3, row4, row5, row6, row7, row8, row9);
        game.print();
        game.recurse(0);
        game.printSudoku();
        
        if (game.isSolved()) {
            System.out.println("Solved!");
        }
        else System.out.println("No solution found");

        long endTime = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("Execution time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
    }
}
