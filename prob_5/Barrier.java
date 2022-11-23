package prob_5;

public class Barrier {

    private int counter;
    private int pThreadNumber;

    public Barrier(int pThreadNumber) {
        this.pThreadNumber = pThreadNumber;
        this.counter = 0;
    }

    public synchronized void waitForOthers() throws InterruptedException {
        this.counter++;

        if (this.counter == this.pThreadNumber) {
            this.counter = 0;
            notifyAll();
        } else {
            while (this.counter > 0) {
                wait();
        }
    }
}

public synchronized void freeAll() throws InterruptedException {
    this.waitForOthers();
    }
}