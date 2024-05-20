package game;

public class Move {
    private String moveName;
    private String moveType;
    private int value;
    private int pp;

    public Move(String name, String moveType, int value, int pp) {
        this.moveName = name;
        this.moveType = moveType;
        this.value = value;
        this.pp = pp;
    }

    public String getMoveName() {
        return moveName;
    }

    public String getMoveType() {
        return moveType;
    }

    public int getValue() {
        return value;
    }

    public int getPp() {
        return pp;
    }
}