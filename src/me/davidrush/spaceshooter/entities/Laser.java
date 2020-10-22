package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Laser extends Entity{
    BufferedImage color;
    float xSpeed, ySpeed;
    int scale = 3, strength;
    boolean fromPlayer;
    public Laser(float x, float y, float acceleration, double angle, BufferedImage color, boolean fromPlayer, int strength, Level level, Game game, float creatorYMove) {
        super(x, y, acceleration, color.getWidth(), color.getHeight(), level, game);
        this.color = color;
        this.fromPlayer = fromPlayer;
        this.strength = strength;
        ySpeed = (float)Math.sin(angle) * acceleration + creatorYMove;
        xSpeed = (float)Math.cos(angle) * acceleration;
    }

    @Override
    public void tick() {
        x+=xSpeed;
        y-=ySpeed;
        if(y < level.getCameraY() || y > level.getCameraY() + game.height || x < 0 || x > game.width) {
            level.removeEntity(this);
        }
        if(!fromPlayer) {
            if(checkCollide(level.getPlayer(), x, y)) {
                level.getPlayer().damage(strength);
                level.removeEntity(this);
            }
        }
        for(Actor actor : level.getActors()) {
            if(checkCollide(actor, x, y)) {
                actor.damage(strength);
                level.removeEntity(this);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.colors[0], (int)x - (color.getWidth() * scale / 2), (int)(y + (color.getHeight() * scale / 2) - level.getCameraY()), color.getWidth() * scale, color.getHeight() * scale, null);
        for(int i = 0; i < strength; i++) {
            g.drawImage(color, (int)(x - (color.getWidth() * scale / 2) - (xSpeed / 2) * i), (int)((y + (color.getHeight() * scale / 2) + (ySpeed / 2) * i) - level.getCameraY()), color.getWidth() * scale, color.getHeight() * scale, null);
        }
    }
}
