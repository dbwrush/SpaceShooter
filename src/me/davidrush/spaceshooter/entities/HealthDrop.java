package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthDrop extends Entity{
    private int health;
    private static BufferedImage sprite = Assets.healthDrop;
    private static int default_acceleration = 0, maxHealth = 20;
    private static double dropRate = 0.01;
    public HealthDrop(float x, float y, Level level, Game game) {
        super(x, y, default_acceleration, sprite.getWidth(), sprite.getHeight(), level, game);
        System.out.println("HealthDrop!");
        health = (int)(Math.random() * maxHealth) + 1;
    }

    @Override
    public void tick() {
        if(checkCollide(level.getPlayer(), x, y)) {
            level.removeEntity(this);
            level.getPlayer().setHealth(level.getPlayer().getHealth() + health);
            if(level.getPlayer().health > level.getPlayer().getDefaultHealth()) {
                level.getPlayer().setHealth(level.getPlayer().getDefaultHealth());
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, (int)x, (int)(y - level.getCameraY()), null);
    }

    public static double getDropRate() {
        return dropRate;
    }
}
