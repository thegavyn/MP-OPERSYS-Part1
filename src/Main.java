/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Main {

    public static void main(String[] args) {
        Rail rails[] = new Rail[16];
        rails[0].prevRail = rails[15];
        rails[0].nextRail = rails[1];
        rails[15].prevRail = rails[14];
        rails[15].nextRail = rails[0];

        for (int x = 1; x < 15; x++) {
            rails[x].prevRail = rails[x-1];
            rails[x].nextRail = rails[x+1];
        }
    }
}
