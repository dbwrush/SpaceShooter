package me.davidrush.spaceshooter.states;

import me.davidrush.spaceshooter.Game;

import java.awt.*;

public class PauseState extends State{
    private int score;
    private String scoreText, continueText;
    public PauseState(Game game, int score) {
        super(game);
        this.score = score;
        this.scoreText = "Your score is " + score + ".";
        this.continueText = "Press Escape to continue where you left off!";
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString(scoreText, (game.width / 2) - 200, game.height / 2);
        g.drawString(continueText, (game.width / 2) - 200, (game.height / 2) + 30);
    }
}
