import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter input file name(If txt document is not in same directory with SE115Maps folder\n"
                    + "please enter path of your document):");
            String inputFile = scanner.nextLine();

            CountryMap map = null;
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                int numberOfCities = Integer.parseInt(br.readLine());
                map = new CountryMap(numberOfCities);

                String[] cityLabels = br.readLine().split(" ");
                for (String label : cityLabels) {
                    map.addCity(label, numberOfCities - 1);
                }

                int numberOfRoutes = Integer.parseInt(br.readLine());
                for (int i = 0; i < numberOfRoutes; i++) {
                    String[] route = br.readLine().split(" ");
                    map.addRoute(route[0], route[1], Integer.parseInt(route[2]));
                }

                String[] startEnd = br.readLine().split(" ");
                String result = WayFinder.findFastestRoute(map, startEnd[0], startEnd[1]);


                try (PrintWriter pw = new PrintWriter(new FileWriter("FastestWay.txt"))) {
                    pw.println(result);
                    System.out.println("Result written to FastestWay.txt");
                }
            } catch (Exception e) {
                System.err.println("Error reading input file: " + e.getMessage());
            }
        }
    }
}