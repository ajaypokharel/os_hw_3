package prob_6;

public class Ta implements Runnable {
    private HelpRoom hr;

    public Ta(HelpRoom hr) {
        this.hr = hr;
    }

    @Override
    public void run() {
        while (true) {
            Student student = hr.getStudent();
            try {
                student.getLock().lock();
                System.out.println("Tutoring student " + student.getID());
                Utility.nap();
                System.out.println("Finish tutoring student " + student.getID());
                student.getCondition().signal(); // notify student thread that finish turtoring has finished.
            } finally {
                student.getLock().unlock();
            }
        }
    }

}