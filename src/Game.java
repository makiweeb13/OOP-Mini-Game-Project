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
    private BufferedImage logoImage;

    public Game() {
        try {
            backgroundImage = ImageIO.read(new File("resources/images/Background.jpg"));
            logoImage = ImageIO.read(new File("resources/images/Logo.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        new Window("Pokemon Game", this);
        render = new Render();

        //start music
        Sound.playMusic("resources/bgm/start-bgm.wav");
    }

    public synchronized void start(){
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
                System.out.println("FPS " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        render.tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        /*
        "Don't remove these comments so that others will know how this code works."
        Lines 87-91 contain the code for creating the logo, which is the 'Pokémon' logo.
        */
        int desiredWidth = 350;
        int logoX = getWidth() / 2 - desiredWidth / 2 - 20;
        int desiredHeight = 350;
        int logoY = getHeight() / 8 - desiredHeight / 2;
        g.drawImage(logoImage, logoX, logoY, desiredWidth, desiredHeight, null);

        /*
        "Don't remove these comments so that others will know how this code works."
        line 89 - 98 (is the code for creating the button "Press 'ENTER' to start the game".)
         */
        try{ //line 89 - 98 is the code for creating the button "Press 'ENTER' to start the game".
            BufferedImage startImage = ImageIO.read(new File("resources/images/Start.png"));
            // line 96 - 97 is to get the dimensions of the loaded image
            int startWidth = startImage.getWidth();
            int startHeight = startImage.getHeight();
            // Lines 98-99 calculate the position of the image horizontally and vertically.
            int startX = getWidth() / 2 - startWidth / 2;
            int startY = getHeight() / 2 - startHeight / 2;
            g.drawImage(startImage, startX, startY, startWidth, startHeight, null);
        } catch(IOException e){
            e.printStackTrace();
        }

        render.render(g);
        bs.show();
        g.dispose();
    }

    public static void main(String[] args){
        new Game();
    }
}
