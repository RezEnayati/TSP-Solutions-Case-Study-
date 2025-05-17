public class Main {
    public static void main(String[] args) {
        try {
            // Run Nearest Neighbor Solver
            System.out.println("Nearest Neighbor Solver");
            NearestNeighborSolver nnSolver = new NearestNeighborSolver();
            nnSolver.solve();
            nnSolver.printTour();
            System.out.println("Total cost: " + nnSolver.getCost());

            System.out.println();

            // Run 2-Opt Solver
            System.out.println("2-Opt Solver");
            TwoOptSolver twoOptSolver = new TwoOptSolver();
            twoOptSolver.solve();
            twoOptSolver.printTour();
            System.out.println("Total cost: " + twoOptSolver.getCost());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}