package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyBomber extends Actor{
    BufferedImage sprite;
    private static int laserStrength = 6, fireDelay = 120, pointValue = 20, defaultHealth = 10, leftTurretOffset, rightTurretOffset;
    private int timeSinceLastFire = 0;
    private boolean avoidByGoingRight;
    Player player;
    public EnemyBomber(float x, float y, float acceleration, Level level, Game game) {
        super(x, y, acceleration, Assets.enemyBomber.getWidth(), Assets.enemyBomber.getHeight(), defaultHealth, level, game, Assets.enemyBomber);
        this.health = defaultHealth;
        this.sprite = Assets.enemyBomber;
        leftTurretOffset = 8;
        rightTurretOffset = sprite.getWidth() - leftTurretOffset;
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
            if(Math.abs(x - player.getX()) < sprite.getWidth() / 2.0) {
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
        level.addEntity(new Laser(x  + leftTurretOffset, y + sprite.getHeight(), acceleration * 3, 3 * Math.PI / 2 , Assets.colors[1], this, laserStrength, level, game, yMove));
        level.addEntity(new Laser(x  + rightTurretOffset, y + sprite.getHeight(), acceleration * 3, 3 * Math.PI / 2 , Assets.colors[1], this, laserStrength, level, game, yMove));
        timeSinceLastFire = 0;
    }

    @Override
    public void render(Graphics g) {
        drawHealthBar(g, (int)(y - level.getCameraY()));
        g.drawImage(sprite, (int)x, (int)(y- level.getCameraY()), null);
    }
}
