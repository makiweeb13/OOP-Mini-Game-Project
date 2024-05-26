package game;

public class Progress {
    private Pokemon player;
    private Pokemon enemy;
    private Boolean triviaMode;

    public Progress (Pokemon player, Pokemon enemy, Boolean triviaMode) {
        this.player = player;
        this.enemy = enemy;
        this.triviaMode = triviaMode;
    }

    public Pokemon getPlayer() {
        return player;
    }

    public Pokemon getEnemy() {
        return enemy;
    }

    public Boolean getTriviaMode() {
        return triviaMode;
    }
}
