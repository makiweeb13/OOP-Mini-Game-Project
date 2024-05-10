package JavaGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface State {
    void render (Graphics g, int width, int height);
}
