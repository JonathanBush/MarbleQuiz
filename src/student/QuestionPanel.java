package student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jebush2 on 11/17/2016.
 */
public class QuestionPanel implements ActionListener{

    private String question;
    private String[] responses;
    private int answer;

    private JPanel questionPanel;
    private JLabel questionLabel;
    private JButton[] responseButtons;

    private int correct;

    public QuestionPanel(String question, String[] responses, int answer, int width, int height) {
        this.correct = 0;
        this.question = new String(question);
        this.answer = answer;
        this.responses = new String[responses.length];
        System.arraycopy(responses, 0, this.responses, 0, responses.length);
        questionPanel = new JPanel();
        LayoutManager layout = new GridLayout(5,1);
        questionPanel.setLayout(layout);
        this.responseButtons = new JButton[this.responses.length];
        this.questionLabel = new JLabel(this.question);
        this.questionLabel.setHorizontalAlignment(JLabel.CENTER);
        this.questionLabel.setFont(new Font("Arial", Font.PLAIN, 44));
        questionPanel.add(this.questionLabel);
        //JPanel sub = new JPanel();
        for (int i = 0; i < this.responses.length; i++) {
            this.responseButtons[i] = new JButton(this.responses[i]);
            this.responseButtons[i].setActionCommand(Integer.toString(i));
            this.responseButtons[i].addActionListener(this);
            this.responseButtons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            this.responseButtons[i].setPreferredSize(new Dimension(width, height/5));
            this.responseButtons[i].setBackground(Color.lightGray);
            this.responseButtons[i].setOpaque(true);
            questionPanel.add(this.responseButtons[i]);

        }
    }

    public void actionPerformed(ActionEvent e) {
        int response = Integer.parseInt(e.getActionCommand());
        if(response == this.answer) {
            correct = 1;
            System.out.println("Correct");
            this.responseButtons[response].setBackground(Color.green);
        } else {
            correct = -1;
            System.out.println("Incorrect");
            this.responseButtons[response].setBackground(Color.red);
        }
    }

    public JPanel getQuestionPanel() {
        return this.questionPanel;
    }

    public int getCorrect() {
        return correct;
    }

    public void tryAgain() {
        correct = 0;
        for (JButton r : responseButtons) {
            r.setBackground(Color.lightGray);
        }
    }

}
