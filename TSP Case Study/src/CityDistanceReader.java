import java.io.*;
import java.util.*;

public class CityDistanceReader {
    private final Map<String, Integer> cityToIndex = new HashMap<>();
    private final Map<Integer, String> indexToCity = new HashMap<>();
    private double[][] distanceMatrix;

    public CityDistanceReader(String filename) throws IOException {
        parseFile(filename);
    }

    private void parseFile(String filename) throws IOException {
        List<String[]> edges = new ArrayList<>();
        int cityIndex = 0;

        // First pass: assign indices to city names
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 3) continue;

                String city1 = parts[0];
                String city2 = parts[1];
                double dist = Double.parseDouble(parts[2]);

                if (!cityToIndex.containsKey(city1)) {
                    cityToIndex.put(city1, cityIndex);
                    indexToCity.put(cityIndex, city1);
                    cityIndex++;
                }
                if (!cityToIndex.containsKey(city2)) {
                    cityToIndex.put(city2, cityIndex);
                    indexToCity.put(cityIndex, city2);
                    cityIndex++;
                }

                edges.add(parts);
            }
        }

        int n = cityToIndex.size();
        distanceMatrix = new double[n][n];

        // Initialize with infinity or 0 for diagonal
        for (int i = 0; i < n; i++) {
            Arrays.fill(distanceMatrix[i], Double.POSITIVE_INFINITY);
            distanceMatrix[i][i] = 0;
        }

        // Fill the matrix with distances
        for (String[] edge : edges) {
            int i = cityToIndex.get(edge[0]);
            int j = cityToIndex.get(edge[1]);
            double dist = Double.parseDouble(edge[2]);

            distanceMatrix[i][j] = dist;
            distanceMatrix[j][i] = dist; // undirected
        }
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public Map<Integer, String> getIndexToCityMap() {
        return indexToCity;
    }

    public Map<String, Integer> getCityToIndexMap() {
        return cityToIndex;
    }
}