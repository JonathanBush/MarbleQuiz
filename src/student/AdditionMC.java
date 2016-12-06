package student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by jon on 11/27/2016.
 */
public class AdditionMC implements QuestionSet{

    private int lower;
    private int upper;

    private ArrayList<QuestionPanel> questions;

    public AdditionMC(int lower, int upper, int numQs) {
        Random rand = new Random();
        this.lower = lower;
        this.upper = upper;
        questions = new ArrayList<QuestionPanel>(numQs);
        for (int ct = 0; ct < numQs; ct++) {
            int x = lower + rand.nextInt(upper - lower + 1);
            int y = lower + rand.nextInt(upper - lower + 1);
            ArrayList<String> choices = new ArrayList<String>(4);
            while (choices.size() < 3) {    // If there aren't enough, add random ones
                int temp = lower+lower + rand.nextInt(upper+upper - lower+lower);
                if (x+y != temp) {
                    choices.add(Integer.toString(temp));
                }
            }
            int answer = rand.nextInt(4);
            choices.add(answer, Integer.toString(x+y)); // Add the correct answer
            String[] responses = new String[4];
            for (int i = 0; i < 4; i++) {
                responses[i] = new String(choices.get(i));
            }
            System.out.println(Arrays.toString(responses));
            String question = new String(x + " + " + y + " = ?");
            questions.add(new QuestionPanel(question, responses, answer, 800, 480));
        }
    }

    public QuestionPanel getQuestionPanel(int n) {
        return questions.get(n);
    }
}
