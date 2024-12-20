public class Route {
    private final City startCity;
    private final City endCity;
    private final double time;

    public Route(City startCity, City endCity, int time) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.time = time;
    }

    public City getStartCity() {
        return startCity;
    }

    public City getEndCity() {
        return endCity;
    }

    public double getTime() {
        return time;
    }
}
