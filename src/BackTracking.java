import java.util.Arrays;

public class BackTracking {

    public BackTracking() {
    }

    public int[] runBackTracking(int n){

        int[] result = solveBackTracking(n);
        if (result != null) {
            System.out.println("Solution found:");
            System.out.println(Arrays.toString(result));
            printBoard(result);
        } else {
            System.out.println("No solution found!");
        }

        return result;
    }


    // Entry method
    public static int[] solveBackTracking(int n) {
        int[] queens = new int[n]; // queens[row] = col
        if (placeQueen(queens, 0, n)) {
            return queens;
        }
        return null; // No solution
    }

    // Recursive backtracking method
    private static boolean placeQueen(int[] queens, int row, int n) {
        if (row == n) return true; // all queens placed
        for (int col = 0; col < n; col++) {
            if (isSafe(queens, row, col)) {
                queens[row] = col;
                if (placeQueen(queens, row + 1, n)) return true;
            }
        }
        return false; // backtrack
    }

    // Check if a queen can be placed
    private static boolean isSafe(int[] queens, int row, int col) {
        for (int prev = 0; prev < row; prev++) {
            int prevCol = queens[prev];
            if (prevCol == col) return false; // same column
            if (Math.abs(prevCol - col) == Math.abs(prev - row)) return false; // same diagonal
        }
        return true;
    }

    // Utility: print board
    public void printBoard(int[] solution) {
        for (int row = 0; row < solution.length; row++) {
            for (int col = 0; col < solution.length; col++) {
                if (solution[row] == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

}
