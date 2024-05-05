import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {
    private Render render;
    protected Sound sound = new Sound();
    private Game game;

    public KeyInput(Render render, Game game) {
        this.render = render;
        this.game = game;

        //start music
        sound.playMusic("resources/bgm/title-screen-bgm.wav");

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            // Access gameState, titleState, and selectionState through getter methods
            if (game.getCurrentScreen() == game.TITLE) {
                game.setCurrentScreen(game.SELECTION);
            }
            else if (game.getCurrentScreen() == game.SELECTION)  {
                game.setBattleState(new BattleState(game.getSelectionState().selector));
                game.setCurrentScreen(game.BATTLE);
                sound.playMusic("resources/bgm/battle-bgm.wav");
            }
            else if (game.getCurrentScreen() == game.BATTLE) {
                Pokemon player = game.getBattleState().getChosenPokemon();
                Pokemon enemy = game.getBattleState().getEnemyPokemon();
                Move currPlayerMove = player.getMove(game.getBattleState().getMoveSelector());

                if (game.getBattleState().getActionSelectionMode() && game.getBattleState().getActionSelector() == 0) {
                    game.getBattleState().setActionSelectionMode(false);
                    game.getBattleState().setFightMode(true);
                } else if (game.getBattleState().getFightMode()) {
                    player.use(currPlayerMove, enemy);
                    game.getBattleState().setFightMode(false);
                    game.getBattleState().setCommentMode(true);
                } else if (game.getBattleState().getCommentMode()) {
                    game.getBattleState().setCommentMode(false);
                    if (player.getCurrentHp() < 0 || enemy.getCurrentHp() < 0) {
                        game.getBattleState().setFaintedMode(true);
                    } else {
                        game.getBattleState().setEnemyTurn(true);
                    }
                } else if (game.getBattleState().getEnemyTurn()) {
                    game.getBattleState().setEnemyTurn(false);
                    Move currEnemyMove = enemy.getMove(game.getBattleState().getRandomMove());
                    enemy.use(currEnemyMove, player);
                    game.getBattleState().setActionSelectionMode(true);
                }
            }
        }
        if (game.getCurrentScreen() == game.SELECTION) {
            if (key == KeyEvent.VK_RIGHT) {
                if (game.getSelectionState().selector < 2) {
                    game.getSelectionState().selector += 1;
                }
            }
            if (key == KeyEvent.VK_LEFT) {
                if (game.getSelectionState().selector > 0) {
                    game.getSelectionState().selector -= 1;
                }
            }
        }

        if (game.getCurrentScreen() == game.BATTLE) {
            Boolean currActionSelectionMode = game.getBattleState().getActionSelectionMode();
            Boolean currFightMode = game.getBattleState().getFightMode();

            int currActionSelector = game.getBattleState().getActionSelector();
            int currMoveSelector = game.getBattleState().getMoveSelector();

            if (currActionSelectionMode) {
                if (key == KeyEvent.VK_LEFT) {
                    if (currActionSelector > 0) {
                        game.getBattleState().setActionSelector(currActionSelector - 1);
                    }
                }
                if (key == KeyEvent.VK_RIGHT) {
                    if (currActionSelector < 1) {
                        game.getBattleState().setActionSelector(currActionSelector + 1);
                    }
                }
            }

            if (currFightMode) {
                if (key == KeyEvent.VK_LEFT) {
                    if (currMoveSelector == 1) game.getBattleState().setMoveSelector(0);
                    if (currMoveSelector == 3) game.getBattleState().setMoveSelector(2);
                }
                if (key == KeyEvent.VK_RIGHT) {
                    if (currMoveSelector == 0) game.getBattleState().setMoveSelector(1);
                    if (currMoveSelector == 2) game.getBattleState().setMoveSelector(3);
                }
                if (key == KeyEvent.VK_UP) {
                    if (currMoveSelector == 2) game.getBattleState().setMoveSelector(0);
                    if (currMoveSelector == 3) game.getBattleState().setMoveSelector(1);
                }
                if (key == KeyEvent.VK_DOWN) {
                    if (currMoveSelector == 0) game.getBattleState().setMoveSelector(2);
                    if (currMoveSelector == 1) game.getBattleState().setMoveSelector(3);
                }
            }
        }

        sound.playSoundEffect("resources/bgm/button-sound-effect.wav");
    }


    public void keyReleased(KeyEvent e) {
        // Handle key released event if needed
    }
}
