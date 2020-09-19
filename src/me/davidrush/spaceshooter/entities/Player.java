package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.HUD;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Actor{
    private final HUD hud;
    private final BufferedImage sprite;
    private final int[] power = new int[3]; //0 = weapon, 1 = shield, 2 = engines/speed
    private final int powerChangeDelay = 10, fireDelay = 40;
    private int powerSelect, availablePower, timeSincePowerChange = powerChangeDelay, timeSinceLastFire = fireDelay;
    private static final int maxPower = 12, defaultHealth = 20;
    private float laserSpeed = 0;
    public Player(float x, float y, float acceleration, Level level, Game game) {
        super(x, y, acceleration, Assets.player.getWidth(), Assets.player.getHeight(), defaultHealth, level, game, Assets.player);
        hud = new HUD(this, game);
        health = defaultHealth;
        sprite = Assets.player;
        power[0] = maxPower / 4;
        power[1] = maxPower / 4;
        power[2] = maxPower / 4;
        availablePower = maxPower / 4;
    }

    @Override
    public void tick() {
        level.setCameraY(y - level.getCameraOffset());
        getInput();
        move();
        hud.tick();
    }

    public void getInput() {
        double enginePower = power[2];
        enginePower = (enginePower / 3) + 1;
        xMove = 0;
        yMove = (float)((2 * -acceleration / 3) * enginePower);
        if(game.getKeyManager().fire) {
            fire();
        }
        if(game.getKeyManager().up) {//move up
            yMove -= acceleration * enginePower;
        }
        if(game.getKeyManager().down) {//move down
            yMove += acceleration * enginePower;
        }
        if(game.getKeyManager().left) {//move left
            xMove -= 2 * acceleration;
        }
        if(game.getKeyManager().right) {//move right
            xMove += 2 * acceleration;
        }
        if(game.getKeyManager().engineSelect && !game.getKeyManager().shift) {
            increasePower(2);
        }
        if(game.getKeyManager().shieldSelect && !game.getKeyManager().shift) {
            increasePower(1);
        }
        if(game.getKeyManager().weaponSelect && !game.getKeyManager().shift) {
            increasePower(0);
        }
        if(game.getKeyManager().engineSelect && game.getKeyManager().shift) {
            decreasePower(2);
        }
        if(game.getKeyManager().shieldSelect && game.getKeyManager().shift) {
            decreasePower(1);
        }
        if(game.getKeyManager().weaponSelect && game.getKeyManager().shift) {
            decreasePower(0);
        }
        laserSpeed = (acceleration * 10) - yMove;
        timeSinceLastFire++;
        timeSincePowerChange++;
    }

    public void fire() {
        if(timeSinceLastFire < fireDelay) {
            return;
        }
        level.addEntity(new Laser(x  + (int)(sprite.getWidth() / 2.0), y, laserSpeed, Math.PI / 2, Assets.colors[3], true, power[0] + 1, level, game));
        timeSinceLastFire = 0;
    }

    public void increasePower(int selection) {
        if(timeSincePowerChange < powerChangeDelay) {
            return;
        }
        powerSelect = selection;
        if(availablePower >= 1 && power[selection] <= maxPower - 1) {
            power[selection] += 1;
            availablePower -= 1;
        }
        timeSincePowerChange = 0;
    }

    public void decreasePower(int selection) {
        if(timeSincePowerChange < powerChangeDelay) {
            return;
        }
        powerSelect = selection;
        if(availablePower <= maxPower - 1 && power[selection] >= 1) {
            power[selection] -= 1;
            availablePower += 1;
        }
        timeSincePowerChange = 0;
    }

    @Override
    public void damage(int amount) {
        amount = amount / ((power[1] / 3) + 1);
        if(amount <= 1) {
            amount = 1;
        }
        health -= amount;
        if(health <= 0) {
            game.gameOver();
        }
    }

    @Override
    public void render(Graphics g) {
        drawHealthBar(g,(int)(y - level.getCameraY()) + sprite.getHeight());
        g.drawImage(sprite, (int)x, level.getCameraOffset(), null);
        hud.render(g);
    }

    public int[] getPower() {
        return power;
    }

    public int getPowerSelect() {
        return powerSelect;
    }

    public int getAvailablePower() {
        return availablePower;
    }
}
