import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CountryMap {
    private City[] cities;
    private Route[] routes;
    private City startCity;
    private City endCity;

    public boolean readDocs(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath());
            return false;
        }

        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 1;

            if (!scanner.hasNextLine()) {
                System.err.println("Error Line " + lineNumber + ": City count is missing or invalid.");
                return false;
            }

            String line = scanner.nextLine();
            int cityCount;
            try {
                cityCount = Integer.parseInt(line.trim());
                if (cityCount <= 0) {
                    System.err.println("Error Line " + lineNumber + ": City count must be a positive integer.");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error Line " + lineNumber + ": City count is not a valid integer.");
                return false;
            }
            System.out.println("City Count: " + cityCount);
            lineNumber++;

            if (!scanner.hasNextLine()) {
                System.err.println("Error Line " + lineNumber + ": City labels are missing.");
                return false;
            }

            line = scanner.nextLine();
            String[] txtCities = line.trim().split(" ");
            if (txtCities.length != cityCount) {
                System.err.println("Error Line " + lineNumber + ": Number of city labels does not match city count.");
                return false;
            }
            cities = new City[cityCount];
            System.out.print("Cities: ");
            for (int i = 0; i < txtCities.length; i++) {
                String txtCity = txtCities[i];
                System.out.print(txtCity + " ");
                cities[i] = new City(txtCity);
            }
            System.out.println();
            lineNumber++;

            if (!scanner.hasNextLine()) {
                System.err.println("Error Line " + lineNumber + ": Route count is missing or invalid.");
                return false;
            }

            line = scanner.nextLine();
            int routeCount;
            try {
                routeCount = Integer.parseInt(line.trim());
                if (routeCount < 0) {
                    System.err.println("Error Line " + lineNumber + ": Route count must be a non-negative integer.");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error Line " + lineNumber + ": Route count is not a valid integer.");
                return false;
            }
            System.out.println("Routes Count: " + routeCount);
            lineNumber++;

            routes = new Route[routeCount];
            for (int i = 0; i < routeCount; i++) {
                if (!scanner.hasNextLine()) {
                    System.err.println("Error Line " + lineNumber + ": Route information is missing.");
                    return false;
                }

                line = scanner.nextLine();
                String[] routeData = line.trim().split(" ");
                if (routeData.length != 3) {
                    System.err.println("Error Line " + lineNumber + ": Route must have exactly 3 elements (start city, end city, time).");
                    return false;
                }
                City from = findCity(routeData[0]);
                if (from == null) {
                    System.err.println("Error Line " + lineNumber + ": Start city " + routeData[0] + " is not valid.");
                    return false;
                }
                City to = findCity(routeData[1]);
                if (to == null) {
                    System.err.println("Error Line " + lineNumber + ": End city " + routeData[1] + " is not valid.");
                    return false;
                }
                int duration;
                try {
                    duration = Integer.parseInt(routeData[2]);
                    if (duration <= 0) {
                        System.err.println("Error Line " + lineNumber + ": Route duration must be a positive integer.");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error Line " + lineNumber + ": Route duration is not a valid integer.");
                    return false;
                }
                routes[i] = new Route(from, to, duration);
                lineNumber++;
            }

            System.out.println("Routes:");
            for (Route route : routes) {
                System.out.println("Starting: " + route.getStartCity().getLabel() +
                        ", Finish: " + route.getEndCity().getLabel() +
                        ", Duration: " + route.getTime());
            }

            if (!scanner.hasNextLine()) {
                System.err.println("Error Line " + lineNumber + ": Starting and ending cities are missing.");
                return false;
            }

            line = scanner.nextLine();
            String[] startEnd = line.trim().split(" ");
            if (startEnd.length != 2) {
                System.err.println("Error Line " + lineNumber + ": Starting and ending cities must be exactly 2 elements.");
                return false;
            }
            startCity = findCity(startEnd[0]);
            if (startCity == null) {
                System.err.println("Error Line " + lineNumber + ": Starting city " + startEnd[0] + " is not valid.");
                return false;
            }
            endCity = findCity(startEnd[1]);
            if (endCity == null) {
                System.err.println("Error Line " + lineNumber + ": Ending city " + startEnd[1] + " is not valid.");
                return false;
            }

            System.out.println("Starting City: " + startCity.getLabel() +
                    ", Finish City: " + endCity.getLabel());

            System.out.println("File read successfully!");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        return true;
    }

    private City findCity(String cityName) {
        for (City city : cities) {
            if (city.getLabel().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    public City[] getCities() {
        return cities;
    }

    public Route[] getRoutes() {
        return routes;
    }

    public City getStartCity() {
        return startCity;
    }

    public City getEndCity() {
        return endCity;
    }
}
