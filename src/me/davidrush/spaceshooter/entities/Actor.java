package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Actor extends Entity{
    protected int health, scale = 5, healthOffset;
    private final BufferedImage healthColor, sprite;
    public Actor(float x, float y, float acceleration, int width, int height, int health, Level level, Game game, BufferedImage sprite) {
        super(x, y, acceleration, width, height, level, game);
        healthColor = Assets.colors[1];
        this.health = health;
        this.sprite = sprite;
        healthOffset = ((health * scale) / 2) - (sprite.getWidth() / 2);
    }

    @Override
    public void move() {
        /*
        boolean xCollided = checkEntityCollisions(x + xMove, y);
        boolean yCollided = checkEntityCollisions(x, y + yMove);
        boolean bothCollided = checkEntityCollisions(x + xMove, y + yMove);
        if(!bothCollided) {
            x += xMove;
            y += yMove;
        } else if(!yCollided) {
            y += yMove;
            xMove = 0;
        } else if(!xCollided) {
            x += xMove;
            yMove = 0;
        } else {
            xMove = 0;
            yMove = 0;
        }
        */
        x += xMove;
        y += yMove;
        if(x < 0) {
            x = 0;
        }
        if(x > game.width - sprite.getWidth()) {
            x = game.width - sprite.getWidth();
        }
    }

    public void damage(int amount) {
        health -= amount;
        if(health <= 0) {
            level.addEntityConcurrent(new Explosion(x, y, level, game, this));
        }
    }

    public abstract void render(Graphics g);

    public void drawHealthBar(Graphics g, int drawY) {
        for(int i = 0; i < health; i++) {
            g.drawImage(healthColor, (int)(x + (scale * i)) - healthOffset, drawY, healthColor.getWidth() * scale, healthColor.getHeight() * scale, null);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }
}
