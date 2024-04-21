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

    public Game(){
        try{
            // Load the background image
            backgroundImage = ImageIO.read(new File("TEST/practice.jpg"));

        }catch(IOException e){
            e.printStackTrace();
        }
        new Window("Pokemon Game", this);
        render = new Render();
//        render.addObject(new Player(100,100, ID.Player));
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementing game loop
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        // Game logic update
        render.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3); // creates 3 buffers within the game
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        //render game object
        render.render(g);

        // Release resources
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        System.out.println("Start");
        new Game();
    }
}