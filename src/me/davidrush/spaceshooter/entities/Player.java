package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.HUD;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Actor{
    private HUD hud;
    private BufferedImage sprite;
    private int[] power = new int[3]; //0 = weapon, 1 = shield, 2 = speed
    private int powerSelect = 0, availablePower, delay = 10, timeSincePowerChange = 0, timeSinceLastFire = 0;
    private static final int maxPower = 12, defaultHealth = 20;
    public Player(float x, float y, float acceleration, Level level, Game game) {
        super(x, y, acceleration, Assets.player.getWidth(), Assets.player.getHeight(), defaultHealth, level, game);
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
        xMove = 0;
        yMove = (-acceleration / 2) * (power[2] / 3);
        if(game.getKeyManager().fire) {
            fire();
        }
        if(game.getKeyManager().up) {//move up
            yMove -= acceleration * (power[2] / 3);
        }
        if(game.getKeyManager().down) {//move down
            yMove += acceleration * (power[2] / 3);
        }
        if(game.getKeyManager().left) {//move left
            xMove -= acceleration;
        }
        if(game.getKeyManager().right) {//move right
            xMove += acceleration;
        }
        if(game.getKeyManager().engineSelect) {
            powerSelect = 2;
        }
        if(game.getKeyManager().shieldSelect) {
            powerSelect = 1;
        }
        if(game.getKeyManager().weaponSelect) {
            powerSelect = 0;
        }
        if(game.getKeyManager().powerUp && availablePower > 0 && timeSincePowerChange > delay) {
            power[powerSelect]++;
            availablePower--;
            timeSincePowerChange = 0;
        } else if(game.getKeyManager().powerDown && timeSincePowerChange > delay && power[powerSelect] > 0) {
            availablePower++;
            power[powerSelect]--;
            timeSincePowerChange = 0;
        } else {
            timeSincePowerChange++;
        }
        timeSinceLastFire++;
    }

    public void fire() {
        if(timeSinceLastFire < delay) {
            return;
        }
        level.addEntity(new Laser(x  + sprite.getWidth() / 2, y, acceleration * 3, Math.PI / 2, Assets.colors[3], true, power[0] + 1, level, game));
        timeSinceLastFire = 0;
    }

    @Override
    public void damage(int amount) {
        amount = amount / power[1];
        health -= amount;
        if(health <= 0) {
            game.gameOver();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, (int)x, (int)level.getCameraOffset(), null);
        hud.render(g);
    }

    public int[] getPower() {
        return power;
    }

    public int getAvailablePower() {
        return availablePower;
    }

    public int getPowerSelect() {
        return powerSelect;
    }
}
