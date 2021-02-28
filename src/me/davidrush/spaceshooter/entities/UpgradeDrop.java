package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UpgradeDrop extends Entity{
    private int health;
    private static BufferedImage sprite = Assets.upgrade;
    private static int default_acceleration = 0, maxHealth = 20;
    private static double dropRate = 0.1;//testing! This should probably be around 0.03 for real gameplay!
    private int upgradeType;
    public UpgradeDrop(float x, float y, Level level, Game game) {
        super(x, y, default_acceleration, sprite.getWidth(), sprite.getHeight(), level, game);
        upgradeType = (int)(Math.random() * 3);
    }

    @Override
    public void tick() {
        if(checkCollide(level.getPlayer(), x, y)) {
            level.removeEntity(this);
            level.getPlayer().upgrade(upgradeType);
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