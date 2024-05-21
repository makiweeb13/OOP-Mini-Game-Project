package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

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
    private BattleState battleState;
    private WinnerState winnerState;
    private TriviaState triviaState; // Added TriviaState
    protected final int TITLE = 0;
    protected final int SELECTION = 1;
    protected final int BATTLE = 2;
    protected final int WINNER = 3;
    protected final int TRIVIA = 4; // Added TRIVIA state
    private int currentScreen;

    public Game() {
        render = new Render();
        this.addKeyListener(new KeyInput(render, this));

        new Window("POKEMON: Defeat Zubat!", this);

        titleState = new TitleState(backgroundImage, startImage, true);
        selectionState = new SelectionState(backgroundImage2, logoImage);
        winnerState = new WinnerState(backgroundImage);
        triviaState = new TriviaState(this); // Initialized TriviaState
    }

    // Getter method for selectionState
    public SelectionState getSelectionState() {
        return selectionState;
    }

    public BattleState getBattleState() {
        return battleState;
    }
    public void setBattleState(BattleState battleState) {
        this.battleState = battleState;
    }

    public int getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(int currentScreen) {
        this.currentScreen = currentScreen;
    }

    public synchronized void start(){
        //gameState = titleState;
        currentScreen = TITLE;
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
//                System.out.println("-----");
//                System.out.println("FPS " + frames);
//                System.out.println("-----");
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

        if (currentScreen == TITLE) {
            titleState.render(g, getWidth(), getHeight());
        } else if (currentScreen == SELECTION) {
            selectionState.render(g, getWidth(), getHeight());
        } else if (currentScreen == BATTLE) {
            battleState.render(g, getWidth(), getHeight());
        } else if (currentScreen == WINNER) {
            winnerState.setWinner(battleState.getWinner());
            winnerState.render(g, getWidth(), getHeight());
        } else if (currentScreen == TRIVIA) { // Added TRIVIA state
            triviaState.render(g, getWidth(), getHeight()); // Render triviaState
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
        new Game().start();
    }
}