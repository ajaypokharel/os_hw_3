package prob_1;

public class Philosopher implements Runnable {
    private int philosoperId;
    private DiningPhilosopherMonitor monitor;
    private Thread th;

    Philosopher(int id, DiningPhilosopherMonitor m) {
        this.philosoperId = id;
        this.monitor = m;
        th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        int i = 0;
        while (i <= 10) {
            monitor.takeForks(philosoperId);

            // eat
            System.out.println("Philosopher " + philosoperId + " is eating");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monitor.returnForks(philosoperId);
            i++;
        }
    }

    public static void main(String[] args) {
        DiningPhilosopherMonitor monitor = new DiningPhilosopherMonitor();
        Philosopher[] phil = new Philosopher[5];

        for (int i = 0; i < 5; i++) {
            phil[i] = new Philosopher(i, monitor);
        }

        for (int i = 0; i < 5; i++) {
            try {
                phil[i].th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
