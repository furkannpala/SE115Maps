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
        String[] errors = new String[10];
        int errorCount = 0;

        try {
            fileReader = new FileReader(inputFile);
            StringBuilder stringBuilder = new StringBuilder();
            int line;

            currentLine++;
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            int numberOfCities = 0;
            try {
                numberOfCities = Integer.parseInt(stringBuilder.toString().trim());
            } catch (NumberFormatException e) {
                addError(errors, "Invalid number format at line " + currentLine + ": Expected an integer. (Number of cities)", errorCount++);
            }

            if (numberOfCities > 0) {
                map = new CountryMap(numberOfCities);
            }

            currentLine++;
            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            String[] cityLabels = stringBuilder.toString().trim().split(" ");
            if (map != null && cityLabels.length != numberOfCities) {
                addError(errors, "Line " + currentLine + ": Expected " + numberOfCities + " city labels, but found " + cityLabels.length + ".", errorCount++);
            } else {
                for (String label : cityLabels) {
                    if (map != null) {
                        map.addCity(label, numberOfCities - 1);
                    }
                }
            }

            currentLine++;
            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            int numberOfRoutes = 0;
            try {
                numberOfRoutes = Integer.parseInt(stringBuilder.toString().trim());
            } catch (NumberFormatException e) {
                addError(errors, "Invalid number format at line " + currentLine + ": Expected an integer. (Number of routes) ", errorCount++);
            }

            for (int i = 0; i < numberOfRoutes; i++) {
                currentLine++;
                stringBuilder.setLength(0);
                while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                    stringBuilder.append((char) line);
                }
                String[] route = stringBuilder.toString().trim().split(" ");
                if (route.length != 3) {
                    addError(errors,"Line " + currentLine + ": Expected a route with 3 elements (City1, City2, Time).", errorCount++);
                } else {
                    try {
                        if (map != null) {
                            map.addRoute(route[0], route[1], Integer.parseInt(route[2]));
                        }
                    } catch (NumberFormatException e) {
                        addError(errors,"Invalid time format at line " + currentLine + ": Expected an integer for time.", errorCount++);
                    }
                }
            }

            currentLine++;
            stringBuilder.setLength(0);
            while ((line = fileReader.read()) != -1 && (char) line != '\n') {
                stringBuilder.append((char) line);
            }
            String[] startEnd = stringBuilder.toString().trim().split(" ");
            if (startEnd.length != 2) {
                addError(errors, "Line " + currentLine + ": Expected 2 elements for start and end cities.", errorCount++);
            }
            if (errorCount == 0) {
                String result = WayFinder.findFastestRoute(map, startEnd[0], startEnd[1]);
                try (FileWriter fileWriter = new FileWriter("FastestWay.txt")) {
                    fileWriter.write(result + "\n");
                    System.out.println("Result written to FastestWay.txt");
                }
            } else {
                System.err.println("The following errors were found:");
                for (int i = 0; i < errorCount; i++) {
                    System.err.println(errors[i]);
                }
            }

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
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
    private static void addError(String[] errors, String errorMessage, int errorIndex) {
        if (errorIndex >= errors.length) {
            String[] newErrors = new String[errors.length * 2];
            for (int i = 0; i < errors.length; i++) {
                newErrors[i] = errors[i];
            }
            errors = newErrors;
        }
        errors[errorIndex] = errorMessage;
    }
}
