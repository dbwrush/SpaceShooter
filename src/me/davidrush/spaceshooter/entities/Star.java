package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Star extends Entity{
    BufferedImage sprite;
    double moveRatio;
    public Star(float x, float y, Level level, Game game) {
        super(x, y, 0, 5, 5, level, game);
        double spriteChoice = Math.random();
        if(spriteChoice <= 0.01) {//if it will be a nebula
            sprite = Assets.nebulae[(int)(Math.random() * Assets.nebulae.length)];
        } else {
            sprite = Assets.stars[(int)(Math.random() * Assets.stars.length)];
        }
        moveRatio = Math.random();
    }

    @Override
    public void tick() {
        if(y > level.getCameraY() + game.height) {
            x = (float)(Math.random() * game.width);
            moveRatio = Math.random();
            changeSprite();
            y = level.getCameraY() - sprite.getHeight();
        }
        y += moveRatio * level.getDistance();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, (int)x, (int)(y - level.getCameraY()), null);
    }

    private void changeSprite() {
        double spriteChoice = Math.random();
        if(spriteChoice <= 0.02) {//if it will be a nebula
            sprite = Assets.nebulae[(int)(Math.random() * Assets.nebulae.length)];
        } else {
            sprite = Assets.stars[(int)(Math.random() * Assets.stars.length)];
        }
    }
}
