import java.awt.Graphics;
import java.awt.Color;

public class Player extends GameObject {

    public Player(int x, int y, ID id) {
        super(x, y, id);
        velX = 1;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, 50, 50);
    }
}
