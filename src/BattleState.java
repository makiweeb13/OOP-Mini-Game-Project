import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattleState {
    private BufferedImage backgroundImage;

    public BattleState() {

    }

    public void render(Graphics g, int width, int height) {

        try {
            backgroundImage = ImageIO.read(new File("resources/images/pokemon-battle-template.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0, 0, width, height, null);

    }
    public String toString() {
        return "BattleState";
    }
}
