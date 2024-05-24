package game;

public class Trivia {
    private String question;
    private String[] choices = new String[4];
    private int answer;

    public Trivia(String question, String[] choices, int answer) {
        this.question = question;
        this.choices = choices;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getAnswer() {
        return answer;
    }
    public void addTrivia(String question, String[] choices, int answer, int count) {
        this.question = question;
        this.choices = choices;
        this.answer = answer;
    }
}
