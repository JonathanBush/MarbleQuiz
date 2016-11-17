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

    private boolean correct;

    public QuestionPanel(String question, String[] responses, int answer) {
        this.correct = false;
        this.question = new String(question);
        this.answer = answer;
        this.responses = new String[responses.length];
        System.arraycopy(responses, 0, this.responses, 0, responses.length);
        questionPanel = new JPanel();
        LayoutManager layout = new BoxLayout(questionPanel, BoxLayout.PAGE_AXIS);
        questionPanel.setLayout(layout);
        this.responseButtons = new JButton[this.responses.length];
        this.questionLabel = new JLabel(this.question);
        questionPanel.add(this.questionLabel);
        for (int i = 0; i < this.responses.length; i++) {
            this.responseButtons[i] = new JButton(this.responses[i]);
            this.responseButtons[i].setActionCommand(Integer.toString(i));
            this.responseButtons[i].addActionListener(this);
            questionPanel.add(this.responseButtons[i]);

        }
    }

    public void actionPerformed(ActionEvent e) {
        int response = Integer.parseInt(e.getActionCommand());
        if(response == this.answer) {
            correct = true;
            System.out.println("Correct");
        } else {
            System.out.println("Incorrect");
        }
    }

    public JPanel getQuestionPanel() {
        return this.questionPanel;
    }

}
