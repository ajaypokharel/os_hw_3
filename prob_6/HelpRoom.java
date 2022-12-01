package prob_6;

public class HelpRoom {
    private final int nChairs = 3; // number of chairs
    private int nWait; // number of students who are waiting
    private Student[] chairs; // students on chair
    private int in; // the chair for next student
    private int out; // the student to be served

    public HelpRoom() {
        nWait = 0;
        in = 0;
        out = 0;
        chairs = new Student[nChairs];
    }

    public synchronized Boolean getTutor(Student student) {
        // Request a tutor. If the tutor is sleeping, wake him/her up
        // If the waiting room is full, leave for not getting help.
        // Return false if leave without getting help. Otherwise, return true.
        notify(); // If the tutor is sleeping, he/she will be waked.
        if (nWait >= nChairs)
            return false; // Waiting room is full. Not get help
        chairs[in] = student;
        System.out.println("Student " + student.getID() + " is waiting");
        in++;
        if (in == nChairs)
            in = 0;
        nWait++;
        return true;
    }

    public synchronized Student getStudent() {
        // Find the next student to tutor
        // Return value: the student to be tutored.
        while (nWait == 0) {
            try {
                System.out.println("TA is Sleeping");
                wait();
            } catch (InterruptedException e) {
            }
        }
        Student student = chairs[out];
        out++;
        if (out == nChairs)
            out = 0;
        nWait--;
        return student;
    }
}
