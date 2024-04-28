public class Pokemon {
    private String pokemonName;
    private final int maxHp = 500;
    private final int maxPp = 20;

    //Keeps track of current HP and PP status
    private int currentHp = maxHp;
    private int currentPp = maxPp;

    public Pokemon(String name) {
        this.pokemonName = name;
    }


}
