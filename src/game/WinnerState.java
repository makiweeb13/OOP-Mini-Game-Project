package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinnerState implements State {
    private BufferedImage backgroundImage;
    private Pokemon winner;
    private boolean showText = true;
    private long lastToggleTime;

    public WinnerState(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        lastToggleTime = System.currentTimeMillis();
    }

    public void setWinner(Pokemon winner) {
        this.winner = winner;
    }

    public void render(Graphics g, int width, int height) {
        try {
            if (backgroundImage == null) {
                backgroundImage = ImageIO.read(new File("res/images/winner.jpg"));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        Font font = new Font(Font.MONOSPACED, Font.BOLD, 60);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();

        String message = "";
        if (winner != null) {
            message = winner.getPokemonName() + " WINS!";
        }

        g.drawString(message, (width - metrics.stringWidth(message)) / 2, 200);
        g.drawImage(winner.getSpriteFront(), (width/2) - (winner.getSpriteFront().getWidth() + 150), (height/2) - (winner.getSpriteFront().getWidth() + 200), 500, 500, null);

        // Toggle visibility every 500 milliseconds
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastToggleTime > 500) {
            showText = !showText;
            lastToggleTime = currentTime;
        }

        if (showText) {
            Font smallFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
            g.setFont(smallFont);
            // Adjusted the position of the "Press Enter to play again" message
            g.drawString("Press Enter to play again", width/2 - 230, height - 200);
        }
    }

}
