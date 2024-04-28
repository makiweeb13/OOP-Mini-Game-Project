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

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }
}
