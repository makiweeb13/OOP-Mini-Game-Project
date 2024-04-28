import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable {
    private Thread thread;
    private boolean running = false;
    private Render render;
    private BufferedImage backgroundImage;
    private BufferedImage backgroundImage2;
    private BufferedImage logoImage;
    private BufferedImage startImage;

    private BufferedImage bulba;
    private BufferedImage chari;
    private BufferedImage torto;
    private BufferedImage pokemonBall;
    private boolean showStartImage = true;
    protected int gameState;
    protected final int titleState = 0;
    protected final int selectionState = 1;
    protected final int battleState = 2;

    protected int selector = 1;

    public Game() {
        render = new Render();
        this.addKeyListener(new KeyInput(render, this));

        new Window("Pokemon Game", this);

        //start music
        Sound.playMusic("resources/bgm/title-screen-bgm.wav");
    }

    public synchronized void start(){
        gameState = titleState;
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS " + frames + " game state " + gameState);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        render.tick();
    }

    private void render(){
        try {
            backgroundImage = ImageIO.read(new File("resources/images/background1.jpg"));
            backgroundImage2 = ImageIO.read(new File("resources/images/background2.jpg"));
            bulba = ImageIO.read(new File("resources/images/bulba.png"));
            chari = ImageIO.read(new File("resources/images/chari.png"));
            torto = ImageIO.read(new File("resources/images/torto.png"));
            logoImage = ImageIO.read(new File("resources/images/Logo.png"));
            startImage = ImageIO.read(new File("resources/images/Start.png"));
            pokemonBall = ImageIO.read(new File("resources/images/pokemon-ball.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        if (gameState == titleState) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        /*
        "Don't remove these comments so that others will know how this code works."
         */
            if (showStartImage) { // Only draw the start image if showStartImage is true
                int startWidth = startImage.getWidth();
                int startHeight = startImage.getHeight();
                int startX = getWidth() / 2 - startWidth / 2;
                int startY = getHeight() / 2 + ((getHeight() / 2) - startHeight);
                g.drawImage(startImage, startX, startY, startWidth, startHeight, null);
            }
            // Toggle the visibility of the start image every half second
            try {
                Thread.sleep(500); // Sleep for 500 milliseconds (half second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showStartImage = !showStartImage; // Toggle the visibility

        } else if (gameState == selectionState) {

            g.drawImage(backgroundImage2, 0, 0, getWidth(), getHeight(), null);

            Font font = new Font(Font.MONOSPACED, Font.BOLD, 40);
            g.setFont(font);
            g.setColor(Color.black);
            String text = "Choose your";
            int textX = ((getWidth() - g.getFontMetrics().stringWidth(text)) / 2) - 90;
            int textY = 60;

            g.drawString(text, textX, textY);

            int logoX = textX + g.getFontMetrics().stringWidth(text) + 10; // Add some padding between the string and the logo
            int logoY = 0;

            g.drawImage(logoImage, logoX, logoY, null);

            g.drawImage(bulba, 90, 240, 290, 290, null);
            g.drawImage(torto, (getWidth()/2 - torto.getWidth()/2) - 40, 240, 290, 290, null);
            g.drawImage(chari, getWidth() - 360, 240, 290, 290, null);

            if (selector == 1) {
                g.drawImage(pokemonBall, getWidth()/2 - 40, getHeight() - 130, 100, 100, null);
            }
            if (selector == 0) {
                g.drawImage(pokemonBall, 200, getHeight() - 140, 100, 100, null);
            }
            if (selector == 2) {
                g.drawImage(pokemonBall, getWidth() - 260, getHeight() - 130, 100, 100, null);
            }
        } else if (gameState == battleState) {
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLUE);
        }
        render.render(g);
        bs.show();
        g.dispose();
    }

    public static void main(String[] args){
        new Game();
    }
}
