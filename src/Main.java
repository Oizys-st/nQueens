import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.*;

public class Main extends Application{

    int n;
    char[][] resultGrid;
    @Override
    public void start(Stage primaryStage) {
        // VBox as the root
        VBox root = new VBox();
        root.setPrefSize(599, 750);

        // AnchorPane inside VBox
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(599, 719);

        // GridPane (3x3 as in FXML)
        GridPane gridPane_n = new GridPane();
        gridPane_n.setPrefSize(530, 530);
        gridPane_n.setLayoutX(27);
        gridPane_n.setLayoutY(148);

        // Column constraints (3 columns)
        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setMinWidth(10.0);
            cc.setPrefWidth(100.0);
            cc.setHgrow(Priority.SOMETIMES);
            gridPane_n.getColumnConstraints().add(cc);
        }

        // Row constraints (3 rows)
        for (int i = 0; i < 3; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setMinHeight(10.0);
            rc.setPrefHeight(30.0);
            rc.setVgrow(Priority.SOMETIMES);
            gridPane_n.getRowConstraints().add(rc);
        }

        // TextField
        TextField textField_n = new TextField();
        textField_n.setLayoutX(198);
        textField_n.setLayoutY(49);

        // Button: Genetics
        Button button_Genetics = new Button("Genetics");
        button_Genetics.setLayoutX(385);
        button_Genetics.setLayoutY(49);
        button_Genetics.setOnAction(e -> {
            n = Integer.parseInt(textField_n.getText());
            int[] result = runGenetics(n);
            resultGrid = new char[n][n];
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(result[i] == j) resultGrid[i][j] = '♛';
                    else resultGrid[i][j] = ' ';
                }
            }

            // 1. Clear existing grid content and constraints
            gridPane_n.getChildren().clear();
            gridPane_n.getColumnConstraints().clear();
            gridPane_n.getRowConstraints().clear();

            // 2. Add new column and row constraints
            for (int i = 0; i < n; i++) {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(100.0 / n); // To make cells fit evenly
                gridPane_n.getColumnConstraints().add(cc);

                RowConstraints rc = new RowConstraints();
                rc.setPercentHeight(100.0 / n);
                gridPane_n.getRowConstraints().add(rc);
            }

            // 3. Add labels for each value in the array
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    Label label = new Label(String.valueOf(resultGrid[row][col]));
                    label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Make label fill the cell
                    label.setStyle("-fx-alignment: center; -fx-border-color: gray; -fx-font-size: 20;");
                    gridPane_n.add(label, col, row);
                }
            }
        });

        // Button: BackTracking
        Button button_BackTracking = new Button("BackTracking");
        button_BackTracking.setLayoutX(471);
        button_BackTracking.setLayoutY(49);
        button_BackTracking.setOnAction(e -> {
            n = Integer.parseInt(textField_n.getText());
            int[] result = runBackTracking(n);
            resultGrid = new char[n][n];
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(result[i] == j) resultGrid[i][j] = '♕';
                    else resultGrid[i][j] = ' ';
                }
            }

            // 1. Clear existing grid content and constraints
            gridPane_n.getChildren().clear();
            gridPane_n.getColumnConstraints().clear();
            gridPane_n.getRowConstraints().clear();

            // 2. Add new column and row constraints
            for (int i = 0; i < n; i++) {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(100.0 / n); // To make cells fit evenly
                gridPane_n.getColumnConstraints().add(cc);

                RowConstraints rc = new RowConstraints();
                rc.setPercentHeight(100.0 / n);
                gridPane_n.getRowConstraints().add(rc);
            }

            // 3. Add labels for each value in the array
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    Label label = new Label(String.valueOf(resultGrid[row][col]));
                    label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Make label fill the cell
                    label.setStyle("-fx-alignment: center; -fx-border-color: gray; -fx-font-size: 20;");
                    gridPane_n.add(label, col, row);
                }
            }
        });

        // Label
        Label label_NoQ = new Label("Number of Queens :");
        label_NoQ.setLayoutX(42);
        label_NoQ.setLayoutY(51);
        label_NoQ.setFont(new Font(14));

        // Add all nodes to anchorPane
        anchorPane.getChildren().addAll(
                gridPane_n,
                button_Genetics,
                button_BackTracking,
                textField_n,
                label_NoQ
        );

        // Add anchorPane to VBox root
        root.getChildren().add(anchorPane);

        // Set scene and show stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("N Queens");
        primaryStage.show();
    }

    public int[] runGenetics(int n){

        int[] solution = solveGenetics(n, 1000, 5000);
        if (solution != null) {
            System.out.println("Solution found:");
            System.out.println(Arrays.toString(solution));
            printBoard(solution);
        } else {
            System.out.println("No solution found. Try again.");
        }

        return solution;
    }



    // Print the board
//    public static void printBoard(int[] queens) {
//        int n = queens.length;
//        for (int row = 0; row < n; row++) {
//            for (int col = 0; col < n; col++) {
//                System.out.print(queens[row] == col ? "Q " : ". ");
//            }
//            System.out.println();
//        }
//    }

    static Random rand = new Random();


    // Main solve method
    public int[] solveGenetics(int n, int populationSize, int maxGenerations) {
        List<int[]> population = generatePopulation(n, populationSize);

        for (int generation = 0; generation < maxGenerations; generation++) {
            population.sort(Comparator.comparingInt(this::fitness).reversed());

            // Check for solution
            if (fitness(population.get(0)) == maxFitness(n)) {
                return population.get(0);
            }

            // Select top individuals
            List<int[]> newPopulation = new ArrayList<>();

            // Elitism: Keep top 2
            newPopulation.add(population.get(0));
            newPopulation.add(population.get(1));

            // Generate rest by crossover and mutation
            while (newPopulation.size() < populationSize) {
                int[] parent1 = select(population);
                int[] parent2 = select(population);
                int[] child = crossover(parent1, parent2);
                mutate(child);
                newPopulation.add(child);
            }

            population = newPopulation;
        }
        return null; // No solution found
    }

    // Generate initial population (random permutations)
    private List<int[]> generatePopulation(int n, int size) {
        List<int[]> pop = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int[] individual = new int[n];
            for (int j = 0; j < n; j++) individual[j] = j;
            shuffle(individual);
            pop.add(individual);
        }
        return pop;
    }

    // Fisher–Yates shuffle for permutation
    private void shuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int t = array[i];
            array[i] = array[j];
            array[j] = t;
        }
    }

    // Fitness function: maximum pairs not attacking each other
    private int fitness(int[] board) {
        int n = board.length;
        int nonAttacking = n * (n - 1) / 2;
        int conflicts = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(board[i] - board[j]) == Math.abs(i - j)) conflicts++;
            }
        }
        return nonAttacking - conflicts;
    }

    // Max fitness is when there are 0 conflicts
    private int maxFitness(int n) {
        return n * (n - 1) / 2;
    }

    // Tournament selection
    private int[] select(List<int[]> population) {
        int k = 5;
        int[] best = population.get(rand.nextInt(population.size()));
        for (int i = 1; i < k; i++) {
            int[] ind = population.get(rand.nextInt(population.size()));
            if (fitness(ind) > fitness(best)) best = ind;
        }
        return Arrays.copyOf(best, best.length);
    }

    // Crossover: Order 1 crossover
    private int[] crossover(int[] parent1, int[] parent2) {
        int n = parent1.length;
        int[] child = new int[n];
        Arrays.fill(child, -1);

        int start = rand.nextInt(n);
        int end = rand.nextInt(n);

        if (start > end) {
            int t = start;
            start = end;
            end = t;
        }
        // Copy a slice from parent1
        for (int i = start; i <= end; i++) {
            child[i] = parent1[i];
        }

        // Fill from parent2
        int c = 0;
        for (int i = 0; i < n; i++) {
            int val = parent2[i];
            if (!contains(child, val)) {
                while (child[c] != -1) c++;
                child[c] = val;
            }
        }
        return child;
    }

    private boolean contains(int[] arr, int val) {
        for (int x : arr) if (x == val) return true;
        return false;
    }

    // Mutation: swap two positions with a small probability
    private void mutate(int[] individual) {
        double mutationRate = 0.1;
        if (rand.nextDouble() < mutationRate) {
            int i = rand.nextInt(individual.length);
            int j = rand.nextInt(individual.length);
            int t = individual[i];
            individual[i] = individual[j];
            individual[j] = t;
        }
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

    public static void main(String[] args) {
        launch(args);
    }
}
