package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TriviaState implements State{
    private Game game;
    private String[] questions;
    private String[][] choices;
    private String[] correctAnswers;
    private int currentQuestionIndex;
    private boolean showAnswer;
    private BufferedImage backgroundImage;

    public TriviaState(Game game){
        this.game = game;
        this.questions = new String[]{
                "Yeah",
        };
        this.choices = new String[][]{
                {"Prog1", "Prog2", "DSA", "Shift"}
        };
        this.correctAnswers = new String[]{
                "C. Shift"
        };
        this.currentQuestionIndex = 0;
        this.showAnswer = false;

        try{
            backgroundImage = ImageIO.read(new File("src/resources/images/Trivia.jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g, int width, int height){
        if(backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, width, height, null);
        }else{
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();

        String title = "Trivia Time!";
        int titleX = (width - fm.stringWidth(title)) / 2;
        g.drawString(title, titleX, 50);

        String question = "Question: " + questions[currentQuestionIndex];
        int questionX = (width - fm.stringWidth(question)) / 2;
        int questionY = 300;
        g.drawString(question, questionX, questionY);

        int choiceYStart = questionY + 195;
        int choiceSpacing = 120;
        int choiceColumnOffset1 = 1220;
        int choiceColumnOffset = 550;


        g.drawString(choices[currentQuestionIndex][0], width - choiceColumnOffset1 - fm.stringWidth(choices[currentQuestionIndex][1]), choiceYStart);
        g.drawString(choices[currentQuestionIndex][2], width - choiceColumnOffset1 - fm.stringWidth(choices[currentQuestionIndex][3]), choiceYStart + choiceSpacing);

        g.drawString(choices[currentQuestionIndex][1], width - choiceColumnOffset - fm.stringWidth(choices[currentQuestionIndex][1]), choiceYStart);
        g.drawString(choices[currentQuestionIndex][3], width - choiceColumnOffset - fm.stringWidth(choices[currentQuestionIndex][3]), choiceYStart + choiceSpacing);

        if(showAnswer){
            String answer = "Answer: " + correctAnswers[currentQuestionIndex];
            int answerX = (width - fm.stringWidth(answer)) / 2;
            g.drawString(answer, answerX, choiceYStart + 2 * choiceSpacing + 50);
        }else{
            String prompt = "Press Enter to reveal the answer";
            int promptX = (width - fm.stringWidth(prompt)) / 2;
            g.drawString(prompt, promptX, choiceYStart + 2 * choiceSpacing + 50);
        }
    }
    @Override
    public String toString() {
        return "TriviaState";
    }
}
