public class City {
    private String label;
    private String[] neighbors;
    private int[] times;
    private int neighborCount;

    public City(String label, int maxRoutes) {
        this.label = label;
        this.neighbors = new String[maxRoutes];
        this.times = new int[maxRoutes];
        this.neighborCount = 0;
    }

    public String getLabel() {
        return label;
    }

    public String[] getNeighbors() {
        String[] neighborCopy = new String[neighborCount];
        for (int i = 0; i < neighborCount; i++) {
            neighborCopy[i] = neighbors[i];
        }
        return neighborCopy;
    }

    public int[] getTimes() {
        int[] timesCopy = new int[neighborCount];
        for (int i = 0; i < neighborCount; i++) {
            timesCopy[i] = times[i];
        }
        return timesCopy;
    }

    public void addNeighbor(String neighborLabel, int time) {
        neighbors[neighborCount] = neighborLabel;
        times[neighborCount] = time;
        neighborCount++;
    }
}