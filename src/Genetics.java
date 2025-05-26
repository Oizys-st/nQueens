import java.util.*;

public class Genetics {

    public Genetics() {
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
    public static void printBoard(int[] queens) {
        int n = queens.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                System.out.print(queens[row] == col ? "Q " : ". ");
            }
            System.out.println();
        }
    }

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

}
