package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyFighter extends Actor{
    BufferedImage sprite;
    private static int laserStrength = 12, fireDelay = 30, pointValue = 30, defaultHealth = 10;
    private int timeSinceLastFire = 0;
    private boolean avoidByGoingRight;
    Player player;
    public EnemyFighter(float x, float y, float acceleration, Level level, Game game) {
        super(x, y, acceleration, Assets.enemyFighter.getWidth(), Assets.enemyFighter.getHeight(), defaultHealth, level, game, Assets.enemyFighter);
        this.health = defaultHealth;
        this.sprite = Assets.enemyFighter;
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
        if(timeSinceLastFire > fireDelay && y < player.getY()) {//if we can fire soon, get in front of the player to shoot them.
            fire();
        } else {//if we can't fire soon
            if(avoidByGoingRight) {
                xMove = acceleration;
                if(x >= game.width) {
                    avoidByGoingRight = false;
                }
            } else {
                xMove = -acceleration;
                if(x <= 0) {
                    avoidByGoingRight = true;
                }
            }
            timeSinceLastFire++;
        }
        if(health <= 0) {
            game.score += pointValue;
            level.removeEntity(this);
            if(Math.random() < HealthDrop.getDropRate()) {
                System.out.println("Added healthdrop");
                level.addEntity(new HealthDrop(x, y, level, game));
            }
            if(Math.random() < UpgradeDrop.getDropRate()) {
                System.out.println("Added upgradeDrop");
                level.addEntity(new UpgradeDrop(x, y, level, game));
            }
        }
        move();
    }

    public void fire() {
        if(timeSinceLastFire < fireDelay) {
            return;
        }
        float playerX = player.getX();
        float playerY = player.getY();
        playerY -= player.yMove * 60; //aims for where the player should be in about 1 second
        double angle = -Math.atan2(playerY - y, playerX - x);
        level.addEntity(new Laser(x  + sprite.getWidth() / 2, y + sprite.getHeight(), acceleration * 3, angle , Assets.colors[1], this, laserStrength, level, game, yMove));
        timeSinceLastFire = 0;
    }

    @Override
    public void render(Graphics g) {
        drawHealthBar(g, (int)(y - level.getCameraY()));
        g.drawImage(sprite, (int)x, (int)(y- level.getCameraY()), null);
    }
}
