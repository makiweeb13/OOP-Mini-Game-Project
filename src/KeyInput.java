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
            } else if (game.getCurrentScreen() == game.SELECTION) {
                game.setBattleState(new BattleState(game.getSelectionState().selector));
                game.setCurrentScreen(game.BATTLE);
                sound.playMusic("resources/bgm/battle-bgm.wav");
            } else if (game.getCurrentScreen() == game.BATTLE) {
                Pokemon player = game.getBattleState().getChosenPokemon();
                Pokemon enemy = game.getBattleState().getEnemyPokemon();
                Move currPlayerMove = player.getMove(game.getBattleState().getMoveSelector());
                Move currEnemyMove;

                if (game.getBattleState().getFaintedMode()) {
                    sound.playMusic("resources/bgm/title-screen-bgm.wav");
                    game.setCurrentScreen(game.TITLE);
                }
                if (game.getBattleState().getActionSelectionMode()) {
                    game.getBattleState().setActionSelectionMode(false);
                    if (game.getBattleState().getActionSelector() == 0) {
                        game.getBattleState().setFightMode(true);
                    } else {
                        game.setCurrentScreen(game.TITLE);
                        sound.playMusic("resources/bgm/title-screen-bgm.wav");
                    }
                } else if (game.getBattleState().getFightMode()) {
                    if (player.getCurrentPp() >= currPlayerMove.getPp()) {
                        player.use(currPlayerMove, enemy);
                    } else {
                        player.use(player.getDefaultMove(), enemy);
                        player.setCurrentHp(player.getCurrentHp() - 40);
                    }
                    game.getBattleState().setFightMode(false);
                    game.getBattleState().setCommentMode(true);
                } else if (game.getBattleState().getCommentMode()) {
                    game.getBattleState().setCommentMode(false);
                    if (enemy.getCurrentHp() <= 0) {
                        game.getBattleState().setFaintedMode(true);
                        sound.playMusic("resources/bgm/victory-theme.wav");
                    } else {
                        game.getBattleState().setEnemyTurn(true);
                        game.getBattleState().getRandomMove();
                    }
                } else if (game.getBattleState().getEnemyTurn()) {
                    game.getBattleState().setEnemyTurn(false);
                    currEnemyMove = enemy.getMove(game.getBattleState().getCurrEnemyMove());
                    if (enemy.getCurrentPp() >= currEnemyMove.getPp()) {
                        enemy.use(currEnemyMove, player);
                    } else {
                        enemy.use(enemy.getDefaultMove(), player);
                        enemy.setCurrentHp(enemy.getCurrentHp() - 40);
                    }

                    if (player.getCurrentHp() <= 0) {
                        game.getBattleState().setFaintedMode(true);
                    } else {
                        game.getBattleState().setActionSelectionMode(true);
                    }
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
