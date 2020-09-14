package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {
    protected float x, y, xMove, yMove, acceleration;
    protected int width, height;
    protected Level level;
    protected Game game;

    public Entity(float x, float y, float acceleration, int width, int height, Level level, Game game) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.level = level;
        this.game = game;
        this.acceleration = acceleration;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public boolean checkEntityCollisions(float newX, float newY) {
        for(Entity entity : level.getEntities()) {
            if(!entity.equals(this)) {
                if(checkCollide(entity, newX, newY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCollide(Entity entity, float newX, float newY) {
        if(newX <= entity.getX() + entity.getWidth() &&
                newX + width >= entity.getX() &&
                newY <= entity.getY() + entity.getHeight() &&
                newY + height >= entity.getY()) {
            return true;
        }
        return false;
    }

    public void move() {
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
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
