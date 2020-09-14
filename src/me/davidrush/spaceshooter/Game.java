package me.davidrush.spaceshooter;

import me.davidrush.Display;
import me.davidrush.KeyManager;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;
import me.davidrush.spaceshooter.states.GameOverState;
import me.davidrush.spaceshooter.states.GameState;
import me.davidrush.spaceshooter.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{
    private Display display;
    public int width, height, score;
    private String title;
    private String version = "Space Shooter Test v1.2";

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    //io
    private KeyManager keyManager;

    //states
    private State gameState;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();
        gameState = new GameState(this);
        State.setCurrentState(gameState);
    }

    private void tick() {
        keyManager.tick();
        if(State.getCurrentState() != null) {
            State.getCurrentState().tick();
        }
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setFont(Assets.font);


        //clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);


        //draw here
        if(State.getCurrentState() != null) {
            State.getCurrentState().render(g);
        }
        g.setColor(Color.WHITE);
        g.drawString(version, width - 260, 20);

        //stop drawing
        bs.show();
        g.dispose();
    }

    public void run() {
        init();

        int desiredFPS = 60;
        double timePerTick = 1000000000 / desiredFPS;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if(delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000) {
                System.out.println("fps: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public void gameOver() {
        GameOverState gameOverState = new GameOverState(this, score);
        State.setCurrentState(gameOverState);
    }

    public void startNew() {
        gameState = new GameState(this);
        State.setCurrentState(gameState);
    }

    public synchronized void start() {
        if(!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop() {
        if(running) {
            try {
                thread.join();
            } catch(InterruptedException e) {

            }
        }
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
}
