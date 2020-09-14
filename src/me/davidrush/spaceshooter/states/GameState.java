package me.davidrush.spaceshooter.states;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;

public class GameState extends State{
    private Level level;
    public GameState(Game game) {
        super(game);
        level = new Level(game);
    }

    @Override
    public void tick() {
        level.tick();
    }

    @Override
    public void render(Graphics g) {
        level.render(g);
    }
}
