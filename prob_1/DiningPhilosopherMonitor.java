package prob_1;

// import java.lang.Thread.State;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.*;

public class DiningPhilosopherMonitor {

    enum State {
        THINKING, HUNGRY, EATING
    };

    State[] state = new State[5];

    Condition[] self = new Condition[5];
    Lock lock = new ReentrantLock();

    public DiningPhilosopherMonitor() {
        for (int i = 0; i < 5; i++) {
            self[i] = lock.newCondition();
        }

        for (int i = 0; i < 5; i++) {
            state[i] = State.THINKING;
            System.out.println("Philosopher " + i + " is thinking");
        }
    }

    public void takeForks(int i) {
        lock.lock();
        try {
            state[i] = State.HUNGRY;
            System.out.println("Philosopher " + i + " is hungry.");
            test(i);
            while (state[i] != State.EATING) {
                System.out.println("Philosopher " + i + " waiting for the fork...");
                self[i].await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }

    }

    public void returnForks(int i) {
        lock.lock();
        try {
            state[i] = State.THINKING;
            test((i + 4) % 5);
            test((i + 1) % 5);
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public void test(int i) {
        if ((state[(i + 4) % 5] != State.EATING) && (state[i] == State.HUNGRY)
                && (state[(i + 1) % 5] != State.EATING)) {
            state[i] = State.EATING;
            self[i].signal();
        }
    }

}
