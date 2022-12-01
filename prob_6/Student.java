package prob_6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Student implements Runnable {
    private int id;
    private HelpRoom hr;
    private Lock lock;
    private Condition cond;

    public Student(int id, HelpRoom hr) {
        this.hr = hr;
        this.id = id;
        lock = new ReentrantLock();
        cond = lock.newCondition();
    }

    public int getID() {
        return id;
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getCondition() {
        return cond;
    }

    public void run() {
        while (true) {
            System.out.println("Student " + id + " is studying");
            Utility.nap(50);
            System.out.println("Student " + id + " is seeking for help");
            if (hr.getTutor(this)) { // will get help. Wait until finished tutoring.
                try {
                    lock.lock();
                    cond.await();
                } catch (InterruptedException e) {
                } finally {
                    lock.unlock();
                }
            } else { // Did not get help
                System.out.println("Student " + id + " did not get help");
            }
        }

    }
}
