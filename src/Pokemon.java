public class Pokemon {
    private String pokemonName;

    // default stats
    private final int maxHp = 500;
    private final int maxPp = 20;
    private final int atkStat = 50;
    private final int defStat = 50;

    // Keeps track of current stats
    private int currentHp;
    private int currentPp;
    private int currentAtkStat;
    private int currentDefStat;

    // To store 4 pokemon moves
    private Move[] moves = new Move[4];

    public Pokemon(String name) {
        this.pokemonName = name;
        this.currentHp = this.maxHp;
        this.currentPp = this.maxPp;
        this.currentAtkStat = this.atkStat;
        this.currentDefStat = this.defStat;
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

    public Move[] getMoves() {
        return moves;
    }

    public void addMove(Move moveToAdd) {
        int size = moves.length;
        if (size < 4) {
            moves[size] = moveToAdd;
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
            case "attackBuff" ->
                    this.currentAtkStat += 10;
            case "defBuff" ->
                    this.currentDefStat += 10;
            default -> throw new IllegalStateException("Unexpected value: " + move.getMoveType());
        }
        this.currentPp -= move.getPp();
    }
}
