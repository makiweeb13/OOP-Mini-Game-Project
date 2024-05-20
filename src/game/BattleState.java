package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattleState implements State {
    private BufferedImage backgroundImage;
    private BufferedImage pokemonBall;
    private int pokemonID;
    private Pokemon chosenPokemon;
    private Pokemon enemyPokemon;
    private int actionSelector;
    private Boolean actionSelectionMode;
    private Boolean fightMode;
    private Boolean commentMode;
    private Boolean enemyTurn;
    private Boolean faintedMode;
    private int moveSelector;
    private int currEnemyMove;

    public BattleState(int pokemonID) {
        this.pokemonID = pokemonID;
        this.actionSelector = 0;
        this.moveSelector = 0;
        this.actionSelectionMode = true;
        this.fightMode = false;
        this.commentMode = false;
        this.enemyTurn = false;
        this.faintedMode = false;

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
        enemyPokemon = new Pokemon("ZUBAT");
        enemyPokemon.addMove(new Move("Supersonic", "atk", 70, 2));
        enemyPokemon.addMove(new Move("Screech", "atkBuff", 50, 2));
        enemyPokemon.addMove(new Move("Absorb", "atk", 100, 3));
        enemyPokemon.addMove(new Move("Mean Look", "defDeBuff", 50, 2));
    }

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

    public Boolean getFaintedMode() {
        return faintedMode;
    }

    public void setFaintedMode(Boolean faintedMode) {
        this.faintedMode = faintedMode;
    }

    public Pokemon getChosenPokemon() {
        return chosenPokemon;
    }

    public Pokemon getEnemyPokemon() {
        return enemyPokemon;
    }

    public int getCurrEnemyMove() {
        return currEnemyMove;
    }

    public int getRandomMove() {
        int randomNum = (int) (Math.random() * ((3) + 1));
        this.currEnemyMove = randomNum;
        System.out.println(randomNum);
        return randomNum;
    }

    public void render(Graphics g, int width, int height) {
        try {
            backgroundImage = ImageIO.read(new File("src/resources/images/pokemon-battle-template.png"));
            pokemonBall = ImageIO.read(new File("src/resources/images/pokemon-ball.png"));
            enemyPokemon.setSpriteFront(ImageIO.read(new File("src/resources/images/zubat-front.png")));
            if (pokemonID == 0) {
                chosenPokemon.setSpriteFront(ImageIO.read(new File("src/resources/images/balbausaur-front.png")));
                chosenPokemon.setSpriteBack(ImageIO.read(new File("src/resources/images/balbausaur-back.png")));
            }
            else if (pokemonID == 1) {
                chosenPokemon.setSpriteFront(ImageIO.read(new File("src/resources/images/squirtle-front.png")));
                chosenPokemon.setSpriteBack(ImageIO.read(new File("src/resources/images/squirtle-back.png")));
            }
            else if (pokemonID == 2) {
                chosenPokemon.setSpriteFront(ImageIO.read(new File("src/resources/images/charmander-front.png")));
                chosenPokemon.setSpriteBack(ImageIO.read(new File("src/resources/images/charmander-back.png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        Font stat = new Font(Font.MONOSPACED, Font.BOLD, 35);
        g.setFont(stat);

        g.drawString(enemyPokemon.getPokemonName(), 90, 70);
        g.drawString("HP " + (Math.max(enemyPokemon.getCurrentHp(), 0)) + "/" + enemyPokemon.getMAX_HP(), 90, 110);
        g.drawString("PP " + enemyPokemon.getCurrentPp() + "/" + enemyPokemon.getMAX_PP(), 340, 110);

        g.drawString(chosenPokemon.getPokemonName(), width - 510, height - 300);
        g.drawString("HP " + (Math.max(chosenPokemon.getCurrentHp(), 0)) + "/" + chosenPokemon.getMAX_HP(), width - 510, height - 260);
        g.drawString("PP " + chosenPokemon.getCurrentPp() + "/" + chosenPokemon.getMAX_PP(), width - 260, height - 260);

        // enemy pokemon sprite
        g.drawImage(enemyPokemon.getSpriteFront(), width - (enemyPokemon.getSpriteFront().getWidth() + 500), 70, 400, 400, null);

        // player pokemon sprite
        g.drawImage(chosenPokemon.getSpriteBack(), chosenPokemon.getSpriteBack().getWidth(), height - (chosenPokemon.getSpriteBack().getHeight() + 550), 600, 600, null);

        Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
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
            if (chosenPokemon.getCurrentPp() >= chosenPokemon.getMove(moveSelector).getPp())
                g.drawString(chosenPokemon.getPokemonName() + " used " + chosenPokemon.getMove(moveSelector).getMoveName() + "!", 60, height - 100);
            else
                g.drawString(chosenPokemon.getPokemonName() + " used " + chosenPokemon.getDefaultMove().getMoveName() + "!", 60, height - 100);
        }

        if (enemyTurn) {
            if (enemyPokemon.getCurrentPp() >= enemyPokemon.getMove(currEnemyMove).getPp())
                g.drawString(enemyPokemon.getPokemonName() + " used " + enemyPokemon.getMove(currEnemyMove).getMoveName() + "!", 60, height - 100);
            else
                g.drawString(enemyPokemon.getPokemonName() + " used " + enemyPokemon.getDefaultMove().getMoveName() + "!", 60, height - 100);
        }

        if (faintedMode) {
            Pokemon faintedPokemon;
            if (chosenPokemon.getCurrentHp() <= 0) {
                faintedPokemon = chosenPokemon;
            } else {
                faintedPokemon = enemyPokemon;
            }
            g.drawString(faintedPokemon.getPokemonName() + " fainted", 60, height - 100);
        }
    }

    public Pokemon getWinner() {
        if (chosenPokemon.getCurrentHp() <= 0) {
            return enemyPokemon; // Enemy wins
        } else if (enemyPokemon.getCurrentHp() <= 0) {
            return chosenPokemon; // Player wins
        } else {
            return null; // No winner yet
        }
    }

    public String toString() {
        return "BattleState";
    }
}
