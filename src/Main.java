import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CountryMap map = new CountryMap();
        boolean readSuccessful = map.readDocs("map1.txt");
        if (!readSuccessful) {
            System.out.println("Exiting...");
            return;
        }

        City[] cities = map.getCities();
        Route[] routes = map.getRoutes();
        City startCity = map.getStartCity();
        City endCity = map.getEndCity();

        String result = WayFinder.findShortestPath(routes, cities, startCity, endCity);
        writeFile(result);
        System.out.println(result);
    }

    public static void writeFile(String text) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("fastestRoute.txt"))) {
            bufferedWriter.write(text);
            System.out.println("File written to 'fastestRoute.txt'");
        } catch (IOException e) {
            System.err.println("Error writing file with BufferedWriter: " + e.getMessage());
        }
    }
}
