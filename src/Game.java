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
    private boolean showStartImage = true;
    private TitleState titleState;
    private SelectionState selectionState;
    private Object gameState;
    private Object battleState;


    // Getter method for gameState
    public Object getGameState() {
        return gameState;
    }

    // Getter method for titleState
    public TitleState getTitleState() {
        return titleState;
    }

    // Getter method for selectionState
    public SelectionState getSelectionState() {
        return selectionState;
    }

    public void setGameState(Object state) {
        this.gameState = state;
    }


    public Game() {
        render = new Render();
        this.addKeyListener(new KeyInput(render, this));

        new Window("Pokemon Game", this);

        //start music
        Sound.playMusic("resources/bgm/title-screen-bgm.wav");

        try {
            backgroundImage = ImageIO.read(new File("resources/images/background1.jpg"));
            backgroundImage2 = ImageIO.read(new File("resources/images/background2.jpg"));
            logoImage = ImageIO.read(new File("resources/images/Logo.png"));
            startImage = ImageIO.read(new File("resources/images/Start.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        titleState = new TitleState(backgroundImage, startImage, true);

        selectionState = new SelectionState(backgroundImage2, logoImage);

        // Set initial game state
        gameState = titleState;
        System.out.println("titleState: " + titleState);
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
                //System.out.println("FPS " + frames);
                System.out.println(gameState);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        render.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        if (gameState == titleState && titleState != null) { // Use the reference variable titleState for comparison
            titleState.render(g, getWidth(), getHeight());
        } else if (gameState == selectionState && selectionState != null) { // Use the reference variable selectionState for comparison
            selectionState.render(g, getWidth(), getHeight());
        } else if (gameState == battleState) {
            // Render battle state
        }

        // Toggle the visibility of the start image every half second
        try {
            Thread.sleep(500); // Sleep for 500 milliseconds (half second)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showStartImage = !showStartImage; // Toggle the visibility


        render.render(g);
        bs.show();
        g.dispose();


    }

    public static void main(String[] args){
        Game game = new Game(); // Create a Game instance
        game.start(); // Call the start method
    }
}
