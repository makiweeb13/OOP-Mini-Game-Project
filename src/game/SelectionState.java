package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SelectionState implements State {
    private BufferedImage backgroundImage;
    private BufferedImage logoImage;
    private BufferedImage bulba;
    private BufferedImage chari;
    private BufferedImage torto;
    private BufferedImage pokemonBall;
    int imageSpacing = 50; // Adjust this value to increase/decrease the spacing between images

    protected int selector = 1;

    public SelectionState() {
        try {
            backgroundImage = ImageIO.read(new File("res/images/background2.jpg"));
            logoImage = ImageIO.read(new File("res/images/Logo.png"));
            bulba = ImageIO.read(new File("res/images/BALBAUSAUR.png"));
            chari = ImageIO.read(new File("res/images/CHARMANDER.png"));
            torto = ImageIO.read(new File("res/images/SQUIRTLE.png"));
            pokemonBall = ImageIO.read(new File("res/images/pokemon-ball.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g, int width, int height) {
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
        g.setFont(font);
        g.setColor(Color.BLACK);
        String text = "CHOOSE YOUR";
        int textX = ((width - g.getFontMetrics().stringWidth(text)) / 2) - 90;
        int textY = 60;

        int logoX = textX + g.getFontMetrics().stringWidth(text) + 10; // Add some padding between the string and the logo
        int logoY = 0;

        g.drawString(text, textX, textY);
        g.drawImage(logoImage, logoX, logoY, null);
        g.drawImage(bulba, 70, 240, 280, 280, null);
        g.drawImage(torto, (width / 2) - (torto.getWidth() / 2) - imageSpacing, 240, 280, 280, null);
        g.drawImage(chari, width - 310 - imageSpacing, 240, 280, 280, null);

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
