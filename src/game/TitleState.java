package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TitleState implements State {
    private BufferedImage backgroundImage;
    private BufferedImage startImage;
    private BufferedImage pokeball;
    private boolean showStartImage;
    private long lastTime;

    private Boolean menuOpened;
    protected final int NEW_GAME = 0;
    protected final int CONTINUE = 1;
    protected final int EXIT = 2;
    private int menuSelector;

    public TitleState(BufferedImage backgroundImage, BufferedImage startImage, boolean showStartImage) {
        this.backgroundImage = backgroundImage;
        this.startImage = startImage;
        this.showStartImage = showStartImage;
        this.lastTime = System.currentTimeMillis();
        this.menuOpened = false;
        this.menuSelector = NEW_GAME;
    }

    public void render(Graphics g, int width, int height) {

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/background1.jpg")));
            startImage = ImageIO.read(new File("res/images/Start.png"));
            pokeball = ImageIO.read(new File("res/images/pokemon-ball.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        // Calculate the time elapsed since the last blink
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastTime;

        // If the elapsed time exceeds the blink interval, toggle the visibility of the start image
        // Blink interval in milliseconds
        long blinkInterval = 500;
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

        if (menuOpened) {
            showMenu(g, width, height);
        }
    }

    public void showMenu(Graphics g, int width, int height) {
        int x = 500;
        int y = 250;
        g.setColor(Color.BLACK);
        g.fillRoundRect(width/2 - x/2, height/2 - y/2, x, y, 20, 20);
        g.setColor(Color.BLUE);
        g.drawRoundRect(width/2 - x/2, height/2 - y/2, x, y, 20, 20);
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 50));

        if (menuSelector == NEW_GAME) g.drawImage( pokeball, width/2 - 150, height/2 - 80, 35, 35, null);
        g.drawString("NEW GAME", width/2 - 100, height/2 - 50);

        if (menuSelector == CONTINUE) g.drawImage( pokeball, width/2 - 150, height/2 - 10, 35, 35, null);
        g.drawString("CONTINUE", width/2 - 100, height/2 + 20);

        if (menuSelector == EXIT) g.drawImage( pokeball, width/2 - 150, height/2 + 60, 35, 35, null);
        g.drawString("EXIT", width/2 - 100, height/2 + 90);
    }

    public Boolean getMenuOpened() {
        return menuOpened;
    }

    public void setMenuOpened(Boolean menuOpened) {
        this.menuOpened = menuOpened;
    }

    public int getMenuSelector() {
        return menuSelector;
    }

    public void setMenuSelector(int menuSelector) {
        this.menuSelector = menuSelector;
    }

    public String toString() {
        return "TitleState";
    }
}
