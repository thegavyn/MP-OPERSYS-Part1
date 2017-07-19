/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Main {

    public static void main(String[] args) {
        // Initialize stations
        Station[] stationList = new Station[8];
        for (int x = 0; x < 8; x++) {
            stationList[x] = new Station(x);
            if (x == 7)
                stationList[x].setNextStop(stationList[0]);
        }

        // Create trains
        // Create passenger
    }
}
