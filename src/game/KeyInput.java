package game;

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
        sound.playMusic("src/resources/bgm/title-screen-bgm.wav");

    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            // Access gameState, titleState, and selectionState through getter methods
            if (game.getCurrentScreen() == game.TITLE) {
                game.setCurrentScreen(game.SELECTION);
            }
            else if (game.getCurrentScreen() == game.SELECTION) {
                game.setBattleState(new BattleState(game.getSelectionState().selector));
                game.setCurrentScreen(game.BATTLE);
                sound.playMusic("src/resources/bgm/battle-bgm.wav");
            }
            else if (game.getCurrentScreen() == game.BATTLE) {
                Pokemon player = game.getBattleState().getChosenPokemon();
                Pokemon enemy = game.getBattleState().getEnemyPokemon();
                Move currPlayerMove = player.getMove(game.getBattleState().getMoveSelector());
                Move currEnemyMove;

                // Battle flow
                if (game.getBattleState().getFaintedMode()) {
                    game.getBattleState().setFaintedMode(false);
                    game.setCurrentScreen(game.WINNER);
                }
                else if (game.getBattleState().getActionSelectionMode()) {
                    game.getBattleState().setActionSelectionMode(false);
                    if (game.getBattleState().getActionSelector() == 0) {
                        game.getBattleState().setFightMode(true);
                    }
                    else if (game.getBattleState().getActionSelector() == 1) {
                        if (game.getTriviaState().isDisabled()) {
                            game.getBattleState().setDisabledTriviaMode(true);
                        } else {
                            game.setCurrentScreen(game.TRIVIA);
                            game.getTriviaState().setDisabled(true);
                        }
                    }
                    else if (game.getBattleState().getActionSelector() == 2) {
                        game.setCurrentScreen(game.TITLE);
                        sound.playMusic("src/resources/bgm/title-screen-bgm.wav");
                    }
                }
                else if (game.getBattleState().getFightMode()) {
                    if (player.getCurrentPp() >= currPlayerMove.getPp()) {
                        player.use(currPlayerMove, enemy);
                    } else {
                        player.use(player.getDefaultMove(), enemy);
                        player.setCurrentHp(player.getCurrentHp() - 40);
                    }
                    game.getBattleState().setFightMode(false);
                    game.getBattleState().setCommentMode(true);
                }
                else if (game.getBattleState().getCommentMode()) {
                    game.getBattleState().setCommentMode(false);
                    if (enemy.getCurrentHp() <= 0) {
                        game.getBattleState().setFaintedMode(true);
                        sound.playMusic("src/resources/bgm/victory-theme.wav");
                    } else {
                        game.getBattleState().setEnemyTurn(true);
                        game.getBattleState().getRandomMove();
                    }
                }
                else if (game.getBattleState().getEnemyTurn()) {
                    game.getBattleState().setEnemyTurn(false);
                    currEnemyMove = enemy.getMove(game.getBattleState().getCurrEnemyMove());

                    if (enemy.getCurrentPp() >= currEnemyMove.getPp()) {
                        enemy.use(currEnemyMove, player);
                    }
                    else {
                        enemy.use(enemy.getDefaultMove(), player);
                        enemy.setCurrentHp(enemy.getCurrentHp() - 40);
                    }

                    if (player.getCurrentHp() <= 0) {
                        game.getBattleState().setFaintedMode(true);
                        sound.playMusicOnce("src/resources/bgm/defeat-theme.wav");
                    }
                    else {
                         game.getBattleState().setActionSelectionMode(true);
                    }
                }
                else if (game.getBattleState().getDisabledTriviaMode()) {
                    game.getBattleState().setDisabledTriviaMode(false);
                    game.getBattleState().setActionSelectionMode(true);
                }

            }
            else if (game.getCurrentScreen() == game.WINNER) {
                game.setCurrentScreen(game.TITLE);
                sound.playMusic("src/resources/bgm/title-screen-bgm.wav");
            }
            else if (game.getCurrentScreen() == game.TRIVIA) {
                TriviaState trivia = game.getTriviaState();
                if (trivia.isAnsweredMode()) {
                    game.setCurrentScreen(game.BATTLE);
                    game.getBattleState().setActionSelectionMode(true);
                    trivia.setShowAnswer(false);
                    trivia.setAnsweredMode(false);
                } else {
                    String selectedAnswer = trivia.getQuestions()[trivia.getSelectedChoiceIndex()].getCorrectAnswer();
                    String answer = trivia.getQuestions()[trivia.getCurrentQuestionIndex()].getCorrectAnswer();
                    trivia.setAnsweredMode(true);
                    trivia.setShowAnswer(true);
                    if (selectedAnswer.equals(answer)) {
                        Pokemon pokemon = game.getBattleState().getChosenPokemon();
                        pokemon.setExp(pokemon.getExp() + 500); // adds 500 exp points if trivia answered correctly
                        pokemon.setLevel(pokemon.getLevel() + 1); // levels up pokemon
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
                if (key == KeyEvent.VK_DOWN) {
                    if (currActionSelector <= 1 && currActionSelector >= 0) {
                        game.getBattleState().setActionSelector(2);
                    }
                }

                if (key == KeyEvent.VK_UP) {
                    if (currActionSelector == 2) {
                        game.getBattleState().setActionSelector(0);
                    }
                }
            }

            if (currFightMode) {
                Pokemon pokemon = game.getBattleState().getChosenPokemon();
                if (key == KeyEvent.VK_LEFT) {
                    if ((currMoveSelector == 1 || currMoveSelector == 3) && !pokemon.getMove(currMoveSelector - 1).isDisabled()) {
                        game.getBattleState().setMoveSelector(currMoveSelector - 1);
                    }
                }
                if (key == KeyEvent.VK_RIGHT) {
                    if ((currMoveSelector == 0 || currMoveSelector == 2) && !pokemon.getMove(currMoveSelector + 1).isDisabled()) {
                        game.getBattleState().setMoveSelector(currMoveSelector + 1);
                    }
                }
                if (key == KeyEvent.VK_UP) {
                    if ((currMoveSelector == 2 || currMoveSelector == 3) && !pokemon.getMove(currMoveSelector - 2).isDisabled()) {
                        game.getBattleState().setMoveSelector(currMoveSelector - 2);
                    }
                }
                if (key == KeyEvent.VK_DOWN) {
                    if ((currMoveSelector == 0 || currMoveSelector == 1) && !pokemon.getMove(currMoveSelector + 2).isDisabled()) {
                        game.getBattleState().setMoveSelector(currMoveSelector + 2);
                    }
                }
            }
        }

        if (game.getCurrentScreen() == game.TRIVIA) {
            TriviaState triviaState = game.getTriviaState();
            if (key == KeyEvent.VK_UP) {
                triviaState.moveUp();
            } else if (key == KeyEvent.VK_DOWN) {
                triviaState.moveDown();
            } else if (key == KeyEvent.VK_LEFT) {
                triviaState.moveLeft();
            } else if (key == KeyEvent.VK_RIGHT) {
                triviaState.moveRight();
            }
        }




        sound.playSoundEffect("src/resources/bgm/button-sound-effect.wav");
    }


    public void keyReleased(KeyEvent e) {
        // Handle key released event if needed
    }
}
