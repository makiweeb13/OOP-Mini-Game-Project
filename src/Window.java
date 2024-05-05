import java.awt.Canvas;
import javax.swing.JFrame;

public class Window extends Canvas {


    public Window (String title, Game game) {
        JFrame frame = new JFrame(title);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
}