import java.io.IOException;
import java.util.*;

public class NearestNeighborSolver {
    private double[][] distances;
    private Map<Integer, String> indexToCity;
    private List<Integer> tour;
    private double cost;

    public NearestNeighborSolver() throws IOException {
        CityDistanceReader reader = new CityDistanceReader("cities.txt");
        this.distances = reader.getDistanceMatrix();
        this.indexToCity = reader.getIndexToCityMap();
    }

    public List<Integer> solve() {
        int n = distances.length;
        boolean[] visited = new boolean[n];
        tour = new ArrayList<>();

        int current = 0;
        tour.add(current);
        visited[current] = true;

        for (int step = 1; step < n; step++) {
            int nearest = -1;
            double minDistance = Double.POSITIVE_INFINITY;

            for (int i = 0; i < n; i++) {
                if (!visited[i] && distances[current][i] < minDistance) {
                    nearest = i;
                    minDistance = distances[current][i];
                }
            }

            current = nearest;
            tour.add(current);
            visited[current] = true;
        }

        // Return to start
        tour.add(tour.get(0));
        computeCost();
        return tour;
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
        System.out.println(indexToCity.get(tour.get(0))); // complete the cycle
    }
}