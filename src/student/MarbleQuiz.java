package student;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by jon on 11/16/2016.
 */
public class MarbleQuiz {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;

    private JFrame frame;
    private JPanel panel;

    private MultiplicationMC quiz;

    private int current;

    ServoController servo;

    public MarbleQuiz() {
        this.frame = new JFrame();
        frame.setTitle("MarbleQuiz");
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //frame.setSize(WIDTH, HEIGHT);
        frame.setBounds(0,0,WIDTH,HEIGHT);
        frame.setResizable(false);
        this.servo = new ServoController(90);
        (new Thread(servo)).start();
        initialize();
    }

    private void initialize() {
        if (panel != null)
            frame.remove(panel);
        frame.revalidate();
        int low, high;
        try {
            Scanner settings = new Scanner(new File("/media/pi/MARBLEQUIZ/bounds.txt"));
            low = settings.nextInt();
            high = settings.nextInt();
        } catch (FileNotFoundException | InputMismatchException e) {
            low = 2;
            high = 12;
        }
        JOptionPane.showMessageDialog(null, "Start new round...");
        this.quiz = new MultiplicationMC(low, high, 10);;
        this.panel = quiz.getQuestionPanel(0).getQuestionPanel();
        frame.add(panel);
        frame.setVisible(true);
        this.current = 0;

        this.update();
    }

    public void update() {
        if (current < 10 && quiz.getQuestionPanel(current).getCorrect() == 1) {
            frame.remove(panel);
            current++;
            servo.setDutyCycle(.07+ .055 *(current % 2));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignore
            }
            frame.remove(panel);
            if (current < 10) {
                panel = quiz.getQuestionPanel(current).getQuestionPanel();
                frame.add(panel);
                frame.revalidate();
            }
        } else if (current < 10 && quiz.getQuestionPanel(current).getCorrect() == -1) {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                // Ignore
            }
            quiz.getQuestionPanel(current).tryAgain();
        } else if (current == 10) {
            frame.remove(panel);
            this.panel = new JPanel();
            JLabel finishText = new JLabel("You finished all of the questions!");
            finishText.setFont(new Font("Arial", Font.PLAIN, 40));
            this.panel.add(finishText);
            frame.add(panel);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Ignore
            }
            current++;
            servo.setDutyCycle(.07+ .055 *(current % 2));
            frame.revalidate();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Ignore
            }
            initialize();
            frame.revalidate();
        }





    }


    public static void main(String[] argv) {
        MarbleQuiz mq = new MarbleQuiz();
        while(true) {
            mq.update();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

}
