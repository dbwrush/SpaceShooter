package me.davidrush.spaceshooter;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Space Shooter", 1600, 900);
        game.start();
    }
}
