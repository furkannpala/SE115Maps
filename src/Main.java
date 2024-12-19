import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Main <inputFileName>");
            return;
        }

        String inputFile = args[0];
        CountryMap map = null;
        FileReader fileReader = null;
        int currentLine = 0;

        try {
            fileReader = new FileReader(inputFile);
            StringBuilder stringBuilder = new StringBuilder();
            int line;

            currentLine++;
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            int numberOfCities;
            try {
                numberOfCities = Integer.parseInt(stringBuilder.toString().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Invalid number format at line " + currentLine + ": Expected an integer. (Number of cities)");
            }
            map = new CountryMap(numberOfCities);

            currentLine++;
            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            String[] cityLabels = stringBuilder.toString().trim().split(" ");
            if (cityLabels.length != numberOfCities) {
                throw new Exception("Line " + currentLine + ": Expected " + numberOfCities + " city labels, but found " + cityLabels.length + ".");
            }
            for (String label : cityLabels) {
                map.addCity(label, numberOfCities - 1);
            }

            currentLine++;
            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            int numberOfRoutes;
            try {
                numberOfRoutes = Integer.parseInt(stringBuilder.toString().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Invalid number format at line " + currentLine + ": Expected an integer. (Number of routes) ");
            }

            for (int i = 0; i < numberOfRoutes; i++) {
                currentLine++;
                stringBuilder.setLength(0);
                while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                    stringBuilder.append((char) line);
                }
                String[] route = stringBuilder.toString().trim().split(" ");
                if (route.length != 3) {
                    throw new Exception("Line " + currentLine + ": Expected a route with 3 elements (City1, City2, Time).");
                }
                try {
                    map.addRoute(route[0], route[1], Integer.parseInt(route[2]));
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid time format at line " + currentLine + ": Expected an integer for time.");
                }
            }

            currentLine++;
            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            String[] startEnd = stringBuilder.toString().trim().split(" ");
            if (startEnd.length != 2) {
                throw new Exception("Line " + currentLine + ": Expected 2 elements for start and end cities.");
            }

            String result = WayFinder.findFastestRoute(map, startEnd[0], startEnd[1]);

            try (FileWriter fileWriter = new FileWriter("FastestWay.txt")) {
                fileWriter.write(result + "\n");
                System.out.println("Result written to FastestWay.txt");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (Exception e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
    }
}
