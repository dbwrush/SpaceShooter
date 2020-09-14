package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.level.Level;

public abstract class Actor extends Entity{
    int health;
    public Actor(float x, float y, float acceleration, int width, int height, int health, Level level, Game game) {
        super(x, y, acceleration, width, height, level, game);
    }

    @Override
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
        if(x < 0) {
            x = 0;
        }
        if(x > game.width) {
            x = game.width;
        }
    }

    public void damage(int amount) {
        health -= amount;
    }

    public int getHealth() {
        return health;
    }
}
