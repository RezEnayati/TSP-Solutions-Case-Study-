import java.io.IOException;
import java.util.*;

public class TwoOptSolver {
    private double[][] distances;
    private Map<Integer, String> indexToCity;
    private List<Integer> tour;
    private double cost;

    public TwoOptSolver() throws IOException {
        CityDistanceReader reader = new CityDistanceReader("cities.txt");
        this.distances = reader.getDistanceMatrix();
        this.indexToCity = reader.getIndexToCityMap();
    }

    public List<Integer> solve() throws IOException {
        int n = distances.length;

        // Start with nearest neighbor tour
        NearestNeighborSolver nnSolver = new NearestNeighborSolver();
        tour = nnSolver.solve(); // already includes return to start

        boolean improved = true;
        int improvements = 0;

        while (improved) {
            improved = false;

            for (int i = 1; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    double before = distances[tour.get(i - 1)][tour.get(i)] +
                            distances[tour.get(j)][tour.get(j + 1)];

                    double after = distances[tour.get(i - 1)][tour.get(j)] +
                            distances[tour.get(i)][tour.get(j + 1)];

                    if (after < before) {
                        tour = twoOptSwap(tour, i, j);
                        improved = true;
                        improvements++;
                        break; // restart after first improvement
                    }
                }
                if (improved) break;
            }
        }

        computeCost();
        return tour;
    }

    private List<Integer> twoOptSwap(List<Integer> tour, int i, int j) {
        List<Integer> newTour = new ArrayList<>();
        newTour.addAll(tour.subList(0, i));

        List<Integer> reversed = new ArrayList<>(tour.subList(i, j + 1));
        Collections.reverse(reversed);
        newTour.addAll(reversed);

        newTour.addAll(tour.subList(j + 1, tour.size()));
        return newTour;
    }

    private void computeCost() {
        cost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            cost += distances[tour.get(i)][tour.get(i + 1)];
        }
    }

    public double getCost() {
        return cost;
    }

    public void printTour() {
        for (int i = 0; i < tour.size() - 1; i++) {
            System.out.print(indexToCity.get(tour.get(i)) + " → ");
        }
        System.out.println(indexToCity.get(tour.get(0)));
    }
}