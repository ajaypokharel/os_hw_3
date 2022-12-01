package prob_6;

import java.util.Random;

public class Utility {
    private static Random rand = new Random();

    public static void nap() {
        nap(10);
    }

    public static void nap(int t) {
        // the program will sleep up to 0.t second.
        try {
            Thread.sleep(rand.nextInt(t * 100));
        } catch (InterruptedException e) {
        }
    }

}