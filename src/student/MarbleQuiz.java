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

    public MarbleQuiz() {
        this.frame = new JFrame();
        frame.setTitle("MarbleQuiz");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);

        this.panel = new JPanel();
        JLabel text = new JLabel("Test");
        panel.add(text);
        frame.add(panel);
        frame.setVisible(true);
    }


    public static void main(String[] argv) {
        new MarbleQuiz();
    }

}
