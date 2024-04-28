import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TitleState {
    private BufferedImage backgroundImage;
    private BufferedImage startImage;
    private boolean showStartImage;
    private long lastTime;
    private long blinkInterval = 500; // Blink interval in milliseconds

    public TitleState(BufferedImage backgroundImage, BufferedImage startImage, boolean showStartImage) {
        this.backgroundImage = backgroundImage;
        this.startImage = startImage;
        this.showStartImage = showStartImage;
        this.lastTime = System.currentTimeMillis();
    }

    public void render(Graphics g, int width, int height) {

        try {
            backgroundImage = ImageIO.read(new File("resources/images/background1.jpg"));
            startImage = ImageIO.read(new File("resources/images/Start.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        // Calculate the time elapsed since the last blink
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastTime;

        // If the elapsed time exceeds the blink interval, toggle the visibility of the start image
        if (elapsedTime >= blinkInterval) {
            showStartImage = !showStartImage;
            lastTime = currentTime; // Update the last blink time
        }

        // Only draw the start image if showStartImage is true
        if (showStartImage) {
            int startWidth = startImage.getWidth();
            int startHeight = startImage.getHeight();
            int startX = width / 2 - startWidth / 2;
            int startY = height / 2 + ((height / 2) - startHeight);
            g.drawImage(startImage, startX, startY, startWidth, startHeight, null);
        }
    }


    public String toString() {
        return "TitleState";
    }
}
