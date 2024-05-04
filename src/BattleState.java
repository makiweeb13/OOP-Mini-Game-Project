import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattleState {
    private BufferedImage backgroundImage;
    private int pokemonID;
    private Pokemon chosenPokemon;
    private Pokemon enemyPokemon;
    private Font font;
    private int actionSelector;
    private Boolean actionSelectionMode;
    private Boolean fightMode;
    private Boolean commentMode;
    private Boolean enemyTurn;
    private int moveSelector;
    private BufferedImage pokemonBall;
    private final int RANDOM_NUM = (int) (Math.random() * ((3) + 1));

    public int getActionSelector() {
        return actionSelector;
    }

    public void setActionSelector(int actionSelector) {
        this.actionSelector = actionSelector;
    }

    public int getMoveSelector() {
        return moveSelector;
    }

    public void setMoveSelector(int moveSelector) {
        this.moveSelector = moveSelector;
    }

    public Boolean getActionSelectionMode() {
        return actionSelectionMode;
    }

    public void setActionSelectionMode(Boolean actionSelectionMode) {
        this.actionSelectionMode = actionSelectionMode;
    }

    public Boolean getFightMode() {
        return fightMode;
    }

    public void setFightMode(Boolean fightMode) {
        this.fightMode = fightMode;
    }

    public Boolean getCommentMode() {
        return commentMode;
    }

    public void setCommentMode(Boolean commentMode) {
        this.commentMode = commentMode;
    }

    public Boolean getEnemyTurn() {
        return enemyTurn;
    }

    public void setEnemyTurn(Boolean enemyTurn) {
        this.enemyTurn = enemyTurn;
    }

    public BattleState(int pokemonID) {
        font = new Font(Font.MONOSPACED, Font.BOLD, 50);

        this.pokemonID = pokemonID;
        this.actionSelector = 0;
        this.moveSelector = 0;
        this.actionSelectionMode = true;
        this.fightMode = false;
        this.commentMode = false;
        this.enemyTurn = false;

        // Create Pokemon object for chosen pokemon
        if (pokemonID == 0) {
            chosenPokemon = new Pokemon("BALBAUSAUR");
            chosenPokemon.addMove(new Move("Tackle", "atk", 70, 2));
            chosenPokemon.addMove(new Move("Growl", "atkBuff", 50, 2));
            chosenPokemon.addMove(new Move("Vine Whip", "atk", 100, 3));
            chosenPokemon.addMove(new Move("Solar Beam", "atk", 150, 5));
        }
        else if (pokemonID == 1) {
            chosenPokemon = new Pokemon("SQUIRTLE");
            chosenPokemon.addMove(new Move("Tackle", "atk", 70, 2));
            chosenPokemon.addMove(new Move("Tail Whip", "defDeBuff", 50, 2));
            chosenPokemon.addMove(new Move("Water Gun", "atk", 100, 3));
            chosenPokemon.addMove(new Move("Hydro Pump", "atk", 150, 5));
        }
        else {
            chosenPokemon = new Pokemon("CHARMANDER");
            chosenPokemon.addMove(new Move("Scratch", "atk", 70, 2));
            chosenPokemon.addMove(new Move("Growl", "atkBuff", 50, 2));
            chosenPokemon.addMove(new Move("Ember", "atk", 100, 3));
            chosenPokemon.addMove(new Move("Fire Blast", "atk", 150, 5));
        }

        // Create Pokemon object for enemy pokemon
        enemyPokemon = new Pokemon("Zubat");
        enemyPokemon.addMove(new Move("Supersonic", "atk", 70, 2));
        enemyPokemon.addMove(new Move("Screech", "atkDeBuff", 50, 2));
        enemyPokemon.addMove(new Move("Absorb", "atk", 100, 3));
        enemyPokemon.addMove(new Move("Mean Look", "defDeBuff", 50, 2));
    }

    public void render(Graphics g, int width, int height) {
        try {
            backgroundImage = ImageIO.read(new File("resources/images/pokemon-battle-template.png"));
            pokemonBall = ImageIO.read(new File("resources/images/pokemon-ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0, 0, width, height, null);
        g.setFont(font);

        if (actionSelectionMode) {
            g.drawString("What should " + chosenPokemon.getPokemonName() + " do?", 60, height - 100);
            if (actionSelector == 0) {
                g.drawImage(pokemonBall, width - 510, height - 130, 35, 35, null);
            }
            g.drawString("FIGHT", width - 460, height - 100);
            if (actionSelector == 1) {
                g.drawImage(pokemonBall, width - 260, height - 130, 35, 35, null);
            }
            g.drawString("RUN", width - 210, height - 100);
        }
        if (fightMode) {
            if (moveSelector == 0) {
                g.drawImage(pokemonBall, 50, height - 160, 35, 35, null);
            } else if (moveSelector == 1) {
                g.drawImage(pokemonBall, 450, height - 160, 35, 35, null);
            } else if (moveSelector == 2) {
                g.drawImage(pokemonBall, 50, height - 100, 35, 35, null);
            } else if (moveSelector == 3) {
                g.drawImage(pokemonBall, 450, height - 100, 35, 35, null);
            }
            g.drawString(chosenPokemon.getMove(0).getMoveName(), 100, height - 130);
            g.drawString(chosenPokemon.getMove(1).getMoveName(), 500, height - 130);
            g.drawString(chosenPokemon.getMove(2).getMoveName(), 100, height - 70);
            g.drawString(chosenPokemon.getMove(3).getMoveName(), 500, height - 70);
            g.drawString("Choose a move", width - 500, height - 100);
        }

        if (commentMode) {
            g.drawString(chosenPokemon.getPokemonName() + " used " + chosenPokemon.getMove(moveSelector).getMoveName(), 60, height - 100);
        }

        if (enemyTurn) {
            g.drawString(enemyPokemon.getPokemonName() + " used " + enemyPokemon.getMove(RANDOM_NUM).getMoveName(), 60, height - 100);
        }
    }


    public String toString() {
        return "BattleState";
    }
}
