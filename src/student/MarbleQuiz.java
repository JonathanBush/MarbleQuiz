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

    private QuestionSet quiz;

    private int current;
    private long startTime;

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
        //(new Thread(servo)).start();
        initialize();
    }

    private void initialize() {
        this.current = 0;
        if (panel != null)
            frame.remove(panel);
        frame.revalidate();
        int[] low = new int[4], high = new int[4];
        try {
            Scanner settings = new Scanner(new File("/media/pi/MARBLEQUIZ/bounds.txt"));
            for (int i = 0; i < 4; i++) {
                low[i] = settings.nextInt();
                high[i] = settings.nextInt();
            }
        } catch (Throwable e) {
            low = new int[]{2,2,1,1};
            high = new int[]{12,12,20,20};
        }
        //JOptionPane.showMessageDialog(null, "Start new round...");
        String[] possibleValues = { "Multiplication", "Division", "Addition", "Subtraction" };
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose type", "Start a new game",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
        switch ((String)selectedValue) {
            case "Multiplication":
                this.quiz = new MultiplicationMC(low[0], high[0], 10);
                break;
            case "Division":
                this.quiz = new DivisionMC(low[1], high[1], 10);;
                break;
            case "Addition":
                this.quiz = new AdditionMC(low[2], high[2], 10);;
                break;
            case "Subtraction":
                this.quiz = new SubtractionMC(low[3], high[3], 10);;
                break;
            default:
                this.quiz = new MultiplicationMC(low[0], high[0], 10);;
        }
        //this.quiz = new DivisionMC(low, high, 10);;
        this.startTime = System.currentTimeMillis();
        this.panel = quiz.getQuestionPanel(0).getQuestionPanel();
        frame.add(panel);
        frame.setVisible(true);
        this.current = 0;
        servo.enable(true);
        sleepMillis(100);
        servo.setDutyCycle(.07+ .055 *(current % 2));
        sleepMillis(200);
        servo.enable(false);
        this.update();
    }

    public void update() {
        if (current < 10 && quiz.getQuestionPanel(current).getCorrect() == 1) {
            servo.enable(true);
            sleepMillis(100);
            //frame.remove(panel);
            current++;
            servo.setDutyCycle(.07+ .055 *(current % 2));
            sleepMillis(900);
            servo.enable(false);
            frame.remove(panel);
            if (current < 10) {
                panel = quiz.getQuestionPanel(current).getQuestionPanel();
                frame.add(panel);
                frame.revalidate();
            }
        } else if (current < 10 && quiz.getQuestionPanel(current).getCorrect() == -1) {
            sleepMillis(750);
            quiz.getQuestionPanel(current).tryAgain();
        } else if (current == 10) {
            frame.remove(panel);
            this.panel = new JPanel();
            panel.setLayout(new GridLayout(5,1));
            //panel.setLayout(new GridLayout(5,1));
            JLabel finishText = new JLabel("You finished all of the questions!\n");
            finishText.setHorizontalAlignment(JLabel.CENTER);
            finishText.setFont(new Font("Arial", Font.PLAIN, 40));
            this.panel.add(finishText);
            long time = System.currentTimeMillis() - startTime;
            JLabel finishTime = new JLabel((time / 60000) + ":" + (((time/1000)%60) < 10 ? "0" : "") + ((time/1000)%60) + "." + (time % 1000));
            finishTime.setHorizontalAlignment(JLabel.CENTER);
            finishTime.setFont(new Font("Arial", Font.PLAIN, 36));
            this.panel.add(finishTime);
            frame.add(panel);
            servo.enable(true);
            sleepMillis(500);
            current++;
            servo.setDutyCycle(.07+ .055 *(current % 2));
            frame.revalidate();
            sleepMillis(5000);
            servo.enable(false);
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

    public void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
