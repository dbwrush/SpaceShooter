package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyCruiser extends Actor{
    BufferedImage sprite;
    private static int laserStrength = 12, fireDelay = 120, pointValue = 500, defaultHealth = 50, turret1XOffset = 14, turret2XOffset = 54, turret1YOffset = 84, turret2YOffset = 66;
    private int timeSinceLastFire = 0;
    private boolean avoidByGoingRight;
    Player player;
    public EnemyCruiser(float x, float y, float acceleration, Level level, Game game) {
        super(x, y, acceleration, Assets.enemyCruiser.getWidth(), Assets.enemyCruiser.getHeight(), defaultHealth, level, game, Assets.enemyCruiser);
        this.health = defaultHealth;
        this.sprite = Assets.enemyCruiser;
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
            fire();
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
        //outer 2 turrets will aim!
        float playerX = player.getX();
        float playerY = player.getY();
        playerY -= player.yMove * 60; //aims for where the player should be in about 1 second
        double angle = -Math.atan2(playerY - y, playerX - x);
        //turret 1 (from left to right)
        level.addEntity(new Laser(x + turret1XOffset, y + turret1YOffset, acceleration * 3, angle , Assets.colors[1], this, laserStrength, level, game, yMove));
        //turret 2
        level.addEntity(new Laser(x + turret2XOffset, y + turret2YOffset, acceleration * 3, 3 * Math.PI / 2 , Assets.colors[1], this, laserStrength, level, game, yMove));
        //turret 3
        level.addEntity(new Laser(x + sprite.getWidth() - turret2XOffset, y + turret2YOffset, acceleration * 3, 3 * Math.PI / 2 , Assets.colors[1], this, laserStrength, level, game, yMove));
        //turret 4
        level.addEntity(new Laser(x + sprite.getWidth() - turret1XOffset, y + turret1YOffset, acceleration * 3, angle , Assets.colors[1], this, laserStrength, level, game, yMove));
        timeSinceLastFire = 0;
    }

    @Override
    public void render(Graphics g) {
        drawHealthBar(g, (int)(y - level.getCameraY()));
        g.drawImage(sprite, (int)x, (int)(y- level.getCameraY()), null);
    }
}
