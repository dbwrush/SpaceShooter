package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyScout extends Actor{
    BufferedImage sprite;
    int laserStrength = 5, fireDelay = 60, timeSinceLastFire = 0, pointValue = 10;
    boolean avoidByGoingRight;
    Player player;
    public EnemyScout(float x, float y, float acceleration, int health, Level level, Game game) {
        super(x, y, acceleration, Assets.enemyScout.getWidth(), Assets.enemyScout.getHeight(), health, level, game);
        this.health = health;
        this.sprite = Assets.enemyScout;
        player = level.getPlayer();
        avoidByGoingRight = Math.random() < 0.5;
    }

    @Override
    public void tick() {
        if(y > level.getCameraY() + game.height) {
            y = level.getCameraY() - sprite.getHeight();
            x = (float)Math.random() * game.width;
        }
        yMove = acceleration;
        if(timeSinceLastFire > fireDelay) {//if we can fire soon, get in front of the player to shoot them.
            if(x > player.getX()) {
                xMove = -acceleration;
            } else if(x < player.getX()){
                xMove = acceleration;
            }
            if(Math.abs(x - player.getX()) < sprite.getWidth() / 2) {
                fire();
            }
        } else {//if we can't fire soon
            if(avoidByGoingRight) {
                xMove = acceleration;
            } else {
                xMove = -acceleration;
            }
            timeSinceLastFire++;
        }
        if(health <= 0) {
            game.score += pointValue;
            level.removeEntity(this);
        }
        move();
    }

    public void fire() {
        if(timeSinceLastFire < fireDelay) {
            return;
        }
        level.addEntity(new Laser(x  + sprite.getWidth() / 2, y + sprite.getHeight(), acceleration * 3, 3 * Math.PI / 2 , Assets.colors[1], false, laserStrength, level, game));
        timeSinceLastFire = 0;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, (int)x, (int)(y- level.getCameraY()), null);
    }
}
