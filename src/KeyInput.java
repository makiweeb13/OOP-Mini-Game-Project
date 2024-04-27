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
            System.out.println("Enter Pressed");

        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
    }
}
