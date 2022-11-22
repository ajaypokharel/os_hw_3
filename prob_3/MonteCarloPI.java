package prob_3;

import java.util.*;

/*
 * Write a multithreaded version of this algorithm to speed up this process so that you can take the advantage of 
 * multiple-core CPUs. You need to create 10 child threads. Each child thread generates 100 random points and counts 
 * the number of points that occur within the circle and passes the number to the main thread using a bounded buffer
 * that has 5 spaces. In other words, each of these child threads is a producer and the main thread is the (only)
 * consumer. Each child thread calculates and sends 100 numbers to the main thread and the main thread cumulates all 
 * these numbers. The main thread prints the estimation of π each time when it received 100 numbers from its children. 
 * 
 */

public class MonteCarloPI {

    public static int MaxBuffSize = 5;
    private static int BufferStart = 0;
    private static int BufferEnd = -1;
    private static int BufferSize = 0;
    public static int[] buffer = new int[10];

    public static void main(String[] args) throws InterruptedException {

        Producer[] prod = new Producer[10];

        for (int i = 0; i <= 10; i++) {
            prod[i] = new Producer();
        }

        calculatePi();

        for (int i = 0; i <= 10; i++) {
            try {
                prod[i].th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void calculatePi() {
        try {
            while (BufferSize == 0) {
                wait();
            }
            int numberInCircle = buffer[BufferStart];

            double myPI = 4.0 * numberInCircle / 100;
            System.out.println("My PI: " + myPI);

            BufferStart = (BufferStart + 1) % MaxBuffSize;
            BufferSize--;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class Producer implements Runnable {

        private Thread th;

        Producer() {
            th = new Thread(this);
            th.start();
        }

        @Override
        public void run() {
            int numberInCircle = 0;

            for (int i = 0; i < 100; i++) {
                double x = Math.random();
                double y = Math.random();
                double distance = Math.sqrt(x * x + y * y);
                if (distance <= 1)
                    numberInCircle++;
            }
            insert(numberInCircle);
        }
    }

    public static void insert(int n) {
        try {
            while (BufferSize == MaxBuffSize) {
                wait();
            }
            BufferEnd = (BufferEnd + 1) % MaxBuffSize;
            buffer[BufferEnd] = n;
            BufferSize++;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}