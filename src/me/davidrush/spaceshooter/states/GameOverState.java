package me.davidrush.spaceshooter.states;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverState extends State{
    String scoreText, continueText;
    BufferedImage gameOverText;
    public GameOverState(Game game, int score) {
        super(game);
        gameOverText = Assets.gameOver;
        scoreText = "You scored " + score + " points!";
        continueText = "Press ENTER to continue";
    }

    @Override
    public void tick() {
        if(game.getKeyManager().start) {
            game.startNew();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(gameOverText, (game.width / 2) - (gameOverText.getWidth() / 2), game.height / 2, null);
        g.setColor(Color.WHITE);
        g.drawString(scoreText, game.width / 2 - 98, 420);
        g.drawString(continueText, game.width / 2 - 120, 450);
    }
}
