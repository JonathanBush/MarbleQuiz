package student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by jon on 12/7/2016.
 */
public class DivisionMC implements QuestionSet{

    private int lower;
    private int upper;

    private ArrayList<QuestionPanel> questions;

    public DivisionMC(int lower, int upper, int numQs) {
        Random rand = new Random();
        this.lower = lower;
        this.upper = upper;
        questions = new ArrayList<QuestionPanel>(numQs);
        for (int ct = 0; ct < numQs; ct++) {
            int x = lower + rand.nextInt(upper - lower + 1);
            int y = lower + rand.nextInt(upper - lower + 1);
            int[] distractors = new int[] {x, y+1, y-1, x+1, x-1, (x-1)*(y-1), lower + rand.nextInt(upper - lower + 1), rand.nextInt(upper + 1)};
            ArrayList<Integer> badChoices = new ArrayList<Integer>(8);
            for (int d : distractors) {
                badChoices.add(d);
            }
            ArrayList<String> choices = new ArrayList<String>(4);
            while (choices.size() < 3 && badChoices.size() > 0) {   // Add 3 distractors to the choices
                int temp = badChoices.remove(rand.nextInt(badChoices.size()));
                boolean contains = false;
                for (String ans : choices) {
                    if (Integer.parseInt(ans) == temp) {
                        contains = true;
                        break;
                    }
                }
                if (temp >= 0 && !contains && temp !=x*y) {
                    choices.add(Integer.toString(temp));
                }
            }
            while (choices.size() < 3) {    // If there aren't enough, add random ones
                int temp = lower + rand.nextInt(upper - lower);
                if (y != temp) {
                    choices.add(Integer.toString(temp));
                }
            }
            int answer = rand.nextInt(4);
            choices.add(answer, Integer.toString(y)); // Add the correct answer
            String[] responses = new String[4];
            for (int i = 0; i < 4; i++) {
                responses[i] = new String(choices.get(i));
            }
            System.out.println(Arrays.toString(responses));
            String question = new String(x*y + " ÷ " + x + " = ?");
            questions.add(new QuestionPanel(question, responses, answer, 800, 480));
        }
    }

    public QuestionPanel getQuestionPanel(int n) {
        return questions.get(n);
    }
}
