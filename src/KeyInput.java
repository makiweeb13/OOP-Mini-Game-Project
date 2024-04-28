import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {
    private Render render;
    private Game game;

    public KeyInput(Render render, Game game) {
        this.render = render;
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            // Access gameState, titleState, and selectionState through getter methods
            if (game.getGameState() == game.getTitleState()) {
                game.setGameState(game.getSelectionState());
            }
        }
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


    public void keyReleased(KeyEvent e) {
        // Handle key released event if needed
    }
}
