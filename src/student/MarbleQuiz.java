package student;

import javax.swing.*;

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
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        //frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //frame.setSize(WIDTH, HEIGHT);
        frame.setBounds(0,0,WIDTH,HEIGHT);
        frame.setResizable(false);
        this.quiz = new MultiplicationMC(2, 12, 10);;
        this.panel = quiz.getQuestionPanel(0).getQuestionPanel();
        frame.add(panel);
        frame.setVisible(true);
        this.current = 0;
        this.update();
        this.servo = new ServoController(90);
    }

    public void update() {
        if (current < 10 && quiz.getQuestionPanel(current).answeredCorrectly()) {
            frame.remove(panel);
            current++;
            frame.remove(panel);
            if (current < 10) {
                panel = quiz.getQuestionPanel(current).getQuestionPanel();
                frame.add(panel);
                frame.revalidate();
            }
        } else if (current == 10) {
            frame.remove(panel);
            this.panel = new JPanel();
            this.panel.add(new JLabel("You finished all of the questions!"));
            frame.add(panel);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Ignore
            }
            frame.revalidate();
        }
        servo.setPosition(80 + 20*(current % 2));
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
