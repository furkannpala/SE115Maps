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

        try {
            fileReader = new FileReader(inputFile);
            StringBuilder stringBuilder = new StringBuilder();
            int line;

            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            int numberOfCities = Integer.parseInt(stringBuilder.toString().trim());
            map = new CountryMap(numberOfCities);

            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            String[] cityLabels = stringBuilder.toString().trim().split(" ");
            for (String label : cityLabels) {
                map.addCity(label, numberOfCities - 1);
            }

            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            int numberOfRoutes = Integer.parseInt(stringBuilder.toString().trim());

            for (int i = 0; i < numberOfRoutes; i++) {
                stringBuilder.setLength(0);
                while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                    stringBuilder.append((char) line);
                }
                String[] route = stringBuilder.toString().trim().split(" ");
                map.addRoute(route[0], route[1], Integer.parseInt(route[2]));
            }

            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            String[] startEnd = stringBuilder.toString().trim().split(" ");

            String result = WayFinder.findFastestRoute(map, startEnd[0], startEnd[1]);

            FileWriter fileWriter = new FileWriter("FastestWay.txt");
            fileWriter.write(result + "\n");
            fileWriter.close();
            System.out.println("Result written to FastestWay.txt");

        } catch (Exception e) {
            System.err.println("Error reading input file: " + e.getMessage());
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
