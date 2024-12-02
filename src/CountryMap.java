public class CountryMap {
    private City[] cities;
    private int cityCount;

    public CountryMap(int maxCities) {
        this.cities = new City[maxCities];
        this.cityCount = 0;
    }

    public void addCity(String label, int maxRoutes) {
        if (findCity(label) == null) {
            cities[cityCount] = new City(label, maxRoutes);
            cityCount++;
        }
    }

    public void addRoute(String fromLabel, String toLabel, int time) {
        City fromCity = findCity(fromLabel);
        City toCity = findCity(toLabel);

        if (fromCity != null && toCity != null) {
            fromCity.addNeighbor(toLabel, time);
            toCity.addNeighbor(fromLabel, time);
        }
    }

    public City findCity(String label) {
        for (int i = 0; i < cityCount; i++) {
            if (cities[i].getLabel().equals(label)) {
                return cities[i];
            }
        }
        return null;
    }

    public City[] getCities() {
        City[] cityCopy = new City[cityCount];
        for (int i = 0; i < cityCount; i++) {
            cityCopy[i] = cities[i];
        }
        return cityCopy;
    }

    public int getCityIndex(String label) {
        for (int i = 0; i < cityCount; i++) {
            if (cities[i].getLabel().equals(label)) {
                return i;
            }
        }
        return -1;
    }
}
