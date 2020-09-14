package me.davidrush.spaceshooter;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Space Shooter", 1280, 720);
        game.start();
    }
}
