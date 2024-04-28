import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TitleState {
    private BufferedImage backgroundImage;
    private BufferedImage startImage;

    public TitleState() {
        try {
            backgroundImage = ImageIO.read(new File("resources/images/background1.jpg"));
            startImage = ImageIO.read(new File("resources/images/Start.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g, boolean showStartImage, int width, int height) {
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        if (showStartImage) {
            int startWidth = startImage.getWidth();
            int startHeight = startImage.getHeight();
            int startX = width / 2 - startWidth / 2;
            int startY = height / 2 + ((height / 2) - startHeight);
            g.drawImage(startImage, startX, startY, startWidth, startHeight, null);
        }
    }
}
