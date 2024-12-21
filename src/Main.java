import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <inputFileName>");
            return;
        }

        String inputFileName = args[0]; // Kullanıcının verdiği dosya adı

        CountryMap map = new CountryMap();
        boolean readSuccessful = map.readDocs(inputFileName);
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
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("fastestRoute.txt");
            fileWriter.write(text);
            System.out.println("File written to 'fastestRoute.txt'");
        } catch (IOException e) {
            System.err.println("Error writing file with FileWriter: " + e.getMessage());
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.err.println("Error closing FileWriter: " + e.getMessage());
                }
            }
        }
    }
}