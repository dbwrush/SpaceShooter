package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Laser extends Entity{
    BufferedImage color;
    float xSpeed, ySpeed;
    int scale =1, strength;
    boolean fromPlayer;
    public Laser(float x, float y, float acceleration, double angle, BufferedImage color, boolean fromPlayer, int strength, Level level, Game game) {
        super(x, y, acceleration, color.getWidth(), color.getHeight(), level, game);
        this.color = color;
        this.fromPlayer = fromPlayer;
        this.strength = strength;
        ySpeed = (float)Math.sin(angle) * acceleration;
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
        g.drawImage(color, (int)x, (int)(y- level.getCameraY()), color.getWidth() * scale, color.getHeight() * scale, null);
        for(int i = 0; i < strength; i++) {
            g.drawImage(color, (int)(x - (xSpeed / 2) * i), (int)((y - (ySpeed / 2) * i) - level.getCameraY()), color.getWidth() * scale, color.getHeight() * scale, null);
        }
    }
}
