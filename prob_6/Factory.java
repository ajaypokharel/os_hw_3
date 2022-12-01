package prob_6;

/*
 * Code taken from Dr. Qian's Solution to the TA problem
 * 
 */

public class Factory {
    public static void main(String[] args) {
        HelpRoom hr = new HelpRoom();
        new Thread(new Ta(hr)).start();
        for (int i = 0; i < 3; i++) { // Create 3 students. We can create more students.
            new Thread(new Student(i, hr)).start();
        }

    }
}