package prob_5;

public class Barrier {

    private int counter;

    public Barrier(int pThreadNumber) {
        this.counter = pThreadNumber;
    }

    public synchronized void waitForOthers() throws InterruptedException {
        counter--;
        while (counter > 0) {
            wait();
        }
    }

    public synchronized void freeAll() {
        counter++;
        if (counter == 0)
            notifyAll();
    }
}