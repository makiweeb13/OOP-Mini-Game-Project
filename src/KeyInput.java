import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

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
            if (game.gameState == game.titleState) {
                game.gameState = game.selectionState;
            }
        }
        if (key == KeyEvent.VK_RIGHT) {
            if (game.selector < 2) {
                game.selector += 1;
            }
        }
        if (key == KeyEvent.VK_LEFT) {
            if (game.selector > 0) {
                game.selector -= 1;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
    }
}
