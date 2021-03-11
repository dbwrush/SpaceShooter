package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends Entity{
    BufferedImage[] sprite = Assets.explosion;
    int animationIndex = 0;
    public Explosion(float x, float y,Level level, Game game, Entity parent) {
        super(x, y, 0, parent.getWidth(), parent.getHeight(), level, game);
    }

    @Override
    public void tick() {
        animationIndex++;
        if(animationIndex >= sprite.length) {
            level.removeEntity(this);
            animationIndex = sprite.length - 1;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite[animationIndex], (int)x, (int)(y - level.getCameraY()), width, height, null);
    }
}
