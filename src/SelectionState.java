import java.awt.*;
import java.awt.image.BufferedImage;

public class SelectionState {
    private BufferedImage backgroundImage;
    private BufferedImage logoImage;
    private long lastTime;
    private long blinkInterval = 500; // Adjust the blink interval to make it slower
    private boolean showText = true; // Variable to control whether to show the text or not

    public SelectionState(BufferedImage backgroundImage, BufferedImage logoImage) {
        this.backgroundImage = backgroundImage;
        this.logoImage = logoImage;
        this.lastTime = System.currentTimeMillis();
    }

    public void render(Graphics g, int width, int height) {
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastTime;

        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.BLACK);
        String text = "Choose your";
        int textX = ((width - g.getFontMetrics().stringWidth(text)) / 2) - 90;
        int textY = 50;

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
    }

    public String toString() {
        return "SelectionState";
    }
}
