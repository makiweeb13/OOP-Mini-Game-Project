package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class TriviaState implements State{
    private Game game;
    private String[] questions;
    private String[] answers;
    private int currentQuestionIndex;
    private boolean showAnswer;

    public TriviaState(Game game){
        this.game = game;
        this.questions = new String[]{
                "Sample question",
        };
        this.answers = new String[]{
                "Sample answer",
        };
        this.currentQuestionIndex = 0;
        this.showAnswer = false;
    }

    @Override
    public void render(Graphics g, int width, int height){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        g.drawString("Trivia Time!", 50, 50);
        g.drawString("Question: " + questions[currentQuestionIndex], 50, 100);

        if(showAnswer){
            g.drawString("Answer: " + answers[currentQuestionIndex], 50, 150);
        }else{
            g.drawString("Press Enter to reveal the answer", 50, 150);
        }
    }

    @Override
    public String toString(){
        return "TriviaState";
    }
}
