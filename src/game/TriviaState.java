package game;

import java.util.Random;
import java.util.Arrays;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TriviaState implements State {
    private Game game;
    private Question[] questions;
    private int currentQuestionIndex;
    private boolean showAnswer;
    private BufferedImage backgroundImage;
    private BufferedImage pokeballImage;

    private int selectedChoiceIndex;

    private boolean answeredMode;
    private boolean disabled; // disables trivia after being used once

    public TriviaState(Game game) {
        this.game = game;
        this.questions = new Question[]{
                new Question("What type is Pikachu?", new String[]{"Water", "Electric", "Fire", "Grass"}, "Electric"),
                new Question("Which Pokémon evolves into Charizard?", new String[]{"Squirtle", "Bulbasaur", "Charmander", "Pikachu"}, "Charmander"),
                new Question("Which Pokémon is also known as the 'Shellfish Pokémon'?", new String[]{"Squirtle", "Slowpoke", "Cloyster", "Krabby"}, "Squirtle"),
                new Question("Which Pokémon is known as the 'Fire Mouse'?", new String[]{"Pikachu", "Charmander", "Jigglypuff", "Cyndaquil"}, "Cyndaquil"),
                new Question("What is the first Pokémon in the Pokédex?", new String[]{"Bulbasaur", "Pikachu", "Rattata", "Charmander"}, "Bulbasaur"),
                new Question("What Pokémon type is effective against Ground type moves?", new String[]{"Electric", "Flying", "Water", "Grass"}, "Water"),
                new Question("Which Pokémon has the Pokédex number 007?", new String[]{"Squirtle", "Charmander", "Bulbasaur", "Wartortle"}, "Squirtle"),
                new Question("What is the final evolution of Eevee when exposed to a Water Stone?", new String[]{"Vaporeon", "Jolteon", "Flareon", "Espeon"}, "Vaporeon"),
                new Question("Which Pokémon is the only one to have been designed by an American artist?", new String[]{"Pikachu", "Jynx", "Ditto", "Mew"}, "Jynx"),
                new Question("What Pokémon is known as the 'Tiny Bird Pokémon'?", new String[]{"Pidgey", "Spearow", "Fearow", "Farfetch'd"}, "Pidgey")
        };
        this.currentQuestionIndex = 0;
        this.showAnswer = false;
        this.selectedChoiceIndex = 0;
        this.answeredMode = false;
        this.disabled = false;
        shuffleQuestions();

        try {
            backgroundImage = ImageIO.read(new File("src/resources/images/Trivia.jpg"));
            pokeballImage = ImageIO.read(new File("src/resources/images/pokemon-ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shuffleQuestions() {
        Random random = new Random();
        for (int i = questions.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Question temp = questions[index];
            questions[index] = questions[i];
            questions[i] = temp;
        }
    }


    @Override
    public void render(Graphics g, int width, int height) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();

        String title = "Trivia Time!";
        int titleX = (width - fm.stringWidth(title)) / 2;
        g.drawString(title, titleX, 50);

        String question = questions[currentQuestionIndex].getQuestion();
        int questionX = (width - fm.stringWidth(question)) / 2;
        int questionY = 300;
        g.drawString(question, questionX, questionY);

        String[] choices = questions[currentQuestionIndex].getChoices();

        int choiceYStart = questionY + 200;
        int choiceSpacing = 120;
        int choiceColumnOffset1 = 1020;
        int choiceColumnOffset = 350;
        int pokeballWidth = 40;
        int pokeballHeight = 40;
        int pokeballOffsetX = -42;
        int pokeballOffsetY = 12;

        for (int i = 0; i < choices.length; i++) {
            int choiceY = choiceYStart + (i / 2) * choiceSpacing;
            int choiceX = (i % 2 == 0) ? (width - choiceColumnOffset1 - fm.stringWidth(choices[i]))
                    : (width - choiceColumnOffset - fm.stringWidth(choices[i]));
            g.drawString(choices[i], choiceX, choiceY);

            if (i == selectedChoiceIndex) {
                g.drawImage(pokeballImage, choiceX + pokeballOffsetX, choiceY - pokeballHeight / 2 - pokeballOffsetY, pokeballWidth, pokeballHeight, null);
            }
        }

        if (showAnswer) {
            String answer = "Answer: " + questions[currentQuestionIndex].getCorrectAnswer();
            int answerX = (width - fm.stringWidth(answer)) / 2;
            g.drawString(answer, answerX, choiceYStart + 2 * choiceSpacing + 50);
        } else {
            String prompt = "Press Enter to reveal the answer";
            int promptX = (width - fm.stringWidth(prompt)) / 2;
            g.drawString(prompt, promptX, choiceYStart + 2 * choiceSpacing + 50);
        }
    }

    public void moveUp() {
        if (selectedChoiceIndex >= 2) {
            selectedChoiceIndex -= 2;
        }
    }

    public void moveDown() {
        if (selectedChoiceIndex + 2 < questions[currentQuestionIndex].getChoices().length) {
            selectedChoiceIndex += 2;
        }
    }

    public void moveLeft() {
        if (selectedChoiceIndex % 2 != 0) {
            selectedChoiceIndex--;
        }
    }

    public void moveRight() {
        if (selectedChoiceIndex % 2 == 0 && selectedChoiceIndex + 1 < questions[currentQuestionIndex].getChoices().length) {
            selectedChoiceIndex++;
        }
    }

    public boolean isAnsweredMode() {
        return answeredMode;
    }

    public void setAnsweredMode(boolean answeredMode) {
        this.answeredMode = answeredMode;
    }

    public void setShowAnswer(boolean showAnswer) {
        this.showAnswer = showAnswer;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public int getSelectedChoiceIndex() {
        return selectedChoiceIndex;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    @Override
    public String toString() {
        return "TriviaState";
    }
}