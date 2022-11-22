package prob_5;

import java.io.IOException;

/**
 * 
 * A testing harness for the Barrier class.
 *
 * 
 * 
 * Proper output is that we should see is that all threads
 * 
 * output an 'A' before reaching the barrier and then a 'B'
 * 
 * after proceeding through the barrier. Therefore, output
 * 
 * should appear as a series of 'A's followed by an equal count
 * 
 * of 'B's. (There should not be an intermingling of 'A's and 'B's.
 * 
 */

public class TestBarrier {

    public static final int THREAD_COUNT = 10;

    public static void main(String args[]) throws IOException {

        Barrier barrier = new Barrier(THREAD_COUNT);

        Thread[] workers = new Thread[THREAD_COUNT];

        for (int i = 0; i < workers.length; i++) {

            workers[i] = new Thread(new Worker(barrier));

            workers[i].start();
        }

        try {
            for (int i = 0; i < workers.length; i++)
                workers[i].join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        barrier = new Barrier(THREAD_COUNT + 1);

        workers = new Thread[THREAD_COUNT];

        for (int i = 0; i < workers.length; i++) {

            workers[i] = new Thread(new Worker(barrier));

            workers[i].start();

        }

        try {
            Thread.sleep(3000);
        }

        catch (InterruptedException ie) {
        }

        barrier.freeAll();

    }

}

class Worker implements Runnable

{

    private Barrier bar;

    public Worker(Barrier bar) {

        this.bar = bar;

    }

    public void run() {

        System.out.println("A");

        try {
            bar.waitForOthers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("B");

    }

}