import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SelectionState {
    private BufferedImage backgroundImage;
    private BufferedImage logoImage;
    private BufferedImage bulba;
    private BufferedImage chari;
    private BufferedImage torto;
    private BufferedImage pokemonBall;
    int imageSpacing = 50; // Adjust this value to increase/decrease the spacing between images
    private long lastTime;
    private long blinkInterval = 500; // Adjust the blink interval to make it slower
    private boolean showText = true; // Variable to control whether to show the text or not

    protected int selector = 1;

    public SelectionState(BufferedImage backgroundImage, BufferedImage logoImage) {
        this.backgroundImage = backgroundImage;
        this.logoImage = logoImage;
        this.lastTime = System.currentTimeMillis();
    }

    private int getWidth() {
        return backgroundImage.getWidth();
    }

    private int getHeight() {
        return backgroundImage.getHeight();
    }


    public void render(Graphics g, int width, int height) {
        try {

            backgroundImage = ImageIO.read(new File("resources/images/background2.jpg"));
            logoImage = ImageIO.read(new File("resources/images/Logo.png"));
            bulba = ImageIO.read(new File("resources/images/bulba.png"));
            chari = ImageIO.read(new File("resources/images/chari.png"));
            torto = ImageIO.read(new File("resources/images/torto.png"));
            pokemonBall = ImageIO.read(new File("resources/images/pokemon-ball.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        g.drawImage(backgroundImage, 0, 0, width, height, null);

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastTime;

        Font font = new Font(Font.MONOSPACED, Font.BOLD, 40);
        g.setFont(font);
        g.setColor(Color.black);
        String text = "Choose your";
        int textX = ((width - g.getFontMetrics().stringWidth(text)) / 2) - 90;
        int textY = 60;

        int logoX = textX + g.getFontMetrics().stringWidth(text) + 10; // Add some padding between the string and the logo
        int logoY = 0;

        if (elapsedTime >= blinkInterval) {
            showText = !showText; // Toggle the showText variable
            lastTime = currentTime; // Update the last blink time
        }

        if (showText) {
            g.drawString(text, textX, textY);
            g.drawImage(logoImage, logoX, logoY, null);
        }

        g.drawImage(bulba, 90, 240, 290, 290, null);
        g.drawImage(torto, (width / 2) - (torto.getWidth() / 2) - imageSpacing, 240, 290, 290, null);
        g.drawImage(chari, width - 360 - imageSpacing, 240, 290, 290, null);

        if (selector == 1) {
            g.drawImage(pokemonBall, (width / 2) - 40, height - 130, 100, 100, null);
        }
        if (selector == 0) {
            g.drawImage(pokemonBall, 200, height - 140, 100, 100, null);
        }
        if (selector == 2) {
            g.drawImage(pokemonBall, width - 260, height - 130, 100, 100, null);
        }
    }

    public String toString() {
        return "SelectionState";
    }
}
