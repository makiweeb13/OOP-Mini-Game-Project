package game;

public class Move {
    private String moveName;
    private String moveType;
    private int value;
    private int pp;
    private int expPoints;
    private boolean disabled = true;

    public Move(String name, String moveType, int value, int pp, int expPoints) {
        this.moveName = name;
        this.moveType = moveType;
        this.value = value;
        this.pp = pp;
        this.expPoints = expPoints;
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

    public int getExpPoints() {
        return expPoints;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}