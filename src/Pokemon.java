import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Pokemon {
    private String pokemonName;

    // default stats
    private final int MAX_HP = 500;
    private final int MAX_PP = 20;
    private final int ATK_STAT = 50;
    private final int DEF_STAT = 50;

    // Keeps track of current stats
    private int currentHp;
    private int currentPp;
    private int currentAtkStat;
    private int currentDefStat;

    // To store 4 pokemon moves
    private Move[] moves = new Move[4];
    private int movesCount;

    // pokemon sprite
    private BufferedImage spriteFront;
    private BufferedImage spriteBack;

    public Pokemon(String name) {
        this.pokemonName = name;
        this.currentHp = this.MAX_HP;
        this.currentPp = this.MAX_PP;
        this.currentAtkStat = this.ATK_STAT;
        this.currentDefStat = this.DEF_STAT;
        this.movesCount = 0;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getCurrentPp() {
        return currentPp;
    }

    public void setCurrentPp(int currentPp) {
        this.currentPp = currentPp;
    }

    public int getCurrentAtkStat() {
        return currentAtkStat;
    }

    public void setCurrentAtkStat(int currentAtkStat) {
        this.currentAtkStat = currentAtkStat;
    }

    public int getCurrentDefStat() {
        return currentDefStat;
    }

    public void setCurrentDefStat(int currentDefStat) {
        this.currentDefStat = currentDefStat;
    }

    public Move getMove(int i) {
        return moves[i];
    }

    public void addMove(Move moveToAdd) {
        if (movesCount < 4) {
            moves[movesCount] = moveToAdd;
            movesCount++;
        }
    }

    public void use(Move move, Pokemon opponent) {
        switch (move.getMoveType()) {
            case "atk" ->
                    opponent.setCurrentHp(opponent.getCurrentHp() - (opponent.getCurrentAtkStat() + move.getValue()));
            case "atkDeBuff" ->
                    opponent.setCurrentAtkStat(opponent.getCurrentAtkStat() - move.getValue());
            case "defDeBuff" ->
                    opponent.setCurrentDefStat(opponent.getCurrentDefStat() - move.getValue());
            case "atkBuff" ->
                    this.currentAtkStat += 10;
            case "defBuff" ->
                    this.currentDefStat += 10;
            default -> throw new IllegalStateException("Unexpected value: " + move.getMoveType());
        }
        this.currentPp -= move.getPp();
    }
}