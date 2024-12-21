public class WayFinder {

    public static String findShortestPath(Route[] routes, City[] cities, City startCity, City endCity) {
        int cityCount = cities.length;

        double[][] distance = new double[cityCount][cityCount];
        int[][] next = new int[cityCount][cityCount];

        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                distance[i][j] = (i == j) ? 0 : Double.MAX_VALUE;
                next[i][j] = -1;
            }
        }

        for (Route route : routes) {
            int from = findCityIndex(route.getStartCity(), cities);
            int to = findCityIndex(route.getEndCity(), cities);
            if (from != -1 && to != -1) {
                distance[from][to] = route.getTime();
                next[from][to] = to;
            }
        }

        // Floyd-Warshall algorithm
        for (int k = 0; k < cityCount; k++) {
            for (int i = 0; i < cityCount; i++) {
                for (int j = 0; j < cityCount; j++) {
                    if (distance[i][k] != Double.MAX_VALUE && distance[k][j] != Double.MAX_VALUE) {
                        if (distance[i][j] > distance[i][k] + distance[k][j]) {
                            distance[i][j] = distance[i][k] + distance[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }

        int startIndex = findCityIndex(startCity, cities);
        int endIndex = findCityIndex(endCity, cities);

        if (startIndex == -1 || endIndex == -1) {
            return "Invalid start or end city!";
        }

        if (distance[startIndex][endIndex] == Double.MAX_VALUE) {
            return "No path found!";
        }

        String[] path = reconstructPath(startIndex, endIndex, next, cities);
        StringBuilder pathStringBuilder = new StringBuilder();
        for (int i = 0; i < path.length; i++) {
            if (i > 0) {
                pathStringBuilder.append(" -> ");
            }
            pathStringBuilder.append(path[i]);
        }

        return "Fastest Way: " + pathStringBuilder + "\nTotal Time: " + distance[startIndex][endIndex] + " min";
    }

    private static String[] reconstructPath(int start, int end, int[][] next, City[] cities) {
        if (next[start][end] == -1) {
            return new String[0];
        }

        String[] tempPath = new String[cities.length];
        int index = 0;
        int current = start;

        while (current != -1 && current != end) {
            tempPath[index++] = cities[current].getLabel();
            current = next[current][end];
        }

        if (current == end) {
            tempPath[index++] = cities[end].getLabel();
        }

        String[] path = new String[index];
        System.arraycopy(tempPath, 0, path, 0, index);

        return path;
    }

    private static int findCityIndex(City city, City[] cities) {
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getLabel().equals(city.getLabel())) {
                return i;
            }
        }
        return -1;
    }
}
