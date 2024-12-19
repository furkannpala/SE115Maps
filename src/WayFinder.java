public class WayFinder {
    public static String findFastestRoute(CountryMap map, String startLabel, String endLabel) {
        City[] cities = map.getCities();
        int cityCount = cities.length;

        int[] distances = new int[cityCount];
        boolean[] visited = new boolean[cityCount];
        String[] previous = new String[cityCount];

        for (int i = 0; i < cityCount; i++) {
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        int startIndex = map.getCityIndex(startLabel);
        int endIndex = map.getCityIndex(endLabel);

        if (startIndex == -1 || endIndex == -1) {
            return "Invalid start or end city.";
        }

        distances[startIndex] = 0;

        for (int i = 0; i < cityCount; i++) {
            int currentIndex = findClosestCity(distances, visited);
            if (currentIndex == -1) break;

            visited[currentIndex] = true;
            City currentCity = cities[currentIndex];

            String[] neighbors = currentCity.getNeighbors();
            int[] times = currentCity.getTimes();

            for (int j = 0; j < neighbors.length; j++) {
                int neighborIndex = map.getCityIndex(neighbors[j]);
                if (!visited[neighborIndex]) {
                    int newDist = distances[currentIndex] + times[j];
                    if (newDist < distances[neighborIndex]) {
                        distances[neighborIndex] = newDist;
                        previous[neighborIndex] = currentCity.getLabel();
                    }
                }
            }
        }

        if (distances[endIndex] == Integer.MAX_VALUE) {
            return "No route found.";
        }

        String[] path = reconstructPath(previous, cities[endIndex].getLabel(), map);
        return "Fastest Way: " + String.join(" -> ", path) +
                "\nTotal Time: " + distances[endIndex] + " min";
    }

    private static int findClosestCity(int[] distances, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static String[] reconstructPath(String[] previous, String endLabel, CountryMap map) {
        String[] tempPath = new String[previous.length];
        int count = 0;
        for (String at = endLabel; at != null; at = previous[map.getCityIndex(at)]) {
            tempPath[count++] = at;
        }

        String[] path = new String[count];
        for (int i = 0; i < count; i++) {
            path[i] = tempPath[count - i - 1];
        }
        return path;
    }
}