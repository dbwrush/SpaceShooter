package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.HUD;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.graphics.Toast;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Actor{
    private final HUD hud;
    private final BufferedImage sprite;
    private final int[] power = new int[3]; //0 = weapon, 1 = shield, 2 = engines/speed
    private final int powerChangeDelay = 10;
    private double fireDelay = 40;
    private int powerSelect, availablePower, timeSincePowerChange = powerChangeDelay, timeSinceLastFire = (int)fireDelay, shieldUpgradeLevel = 1, weaponUpgradeLevel = 1, engineUpgradeLevel = 1;
    private static final int maxPower = 12, defaultHealth = 20;
    private float laserSpeed = 0;
    private int maxHealth;
    public Player(float x, float y, float acceleration, Level level, Game game) {
        super(x, y, acceleration, Assets.player.getWidth(), Assets.player.getHeight(), defaultHealth, level, game, Assets.player);
        hud = new HUD(this, game);
        health = defaultHealth;
        sprite = Assets.player;
        power[0] = maxPower / 4;
        power[1] = maxPower / 4;
        power[2] = maxPower / 4;
        availablePower = maxPower / 4;
        maxHealth = defaultHealth;
    }

    @Override
    public void tick() {
        level.setCameraY(y - level.getCameraOffset());
        getInput();
        move();
        hud.tick();
    }

    public void getInput() {
        double enginePower = acceleration + power[2] * engineUpgradeLevel;
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
        laserSpeed = (acceleration * 5) - yMove;
        timeSinceLastFire++;
        timeSincePowerChange++;
    }

    public void fire() {
        if(timeSinceLastFire < fireDelay) {
            return;
        }
        level.addEntity(new Laser(x  + (int)(sprite.getWidth() / 2.0), y, laserSpeed, Math.PI / 2, Assets.colors[3], this, (power[0] * weaponUpgradeLevel) + 1, level, game, yMove));
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
        //The shield should never be strong enough to ALWAYS block all of the damage, but the player should be able to upgrade it to block nearly all damage!
        double damage = amount / ((power[1] * shieldUpgradeLevel) + 1);
        if(damage <= 1 && Math.random() / (power[1] * shieldUpgradeLevel) >= 1) {
            damage = 1;
        }
        health -= damage;
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

    public void upgrade(int type) {
        //TYPES: 0 = weapon, 1 = shield, 2 = speed
        switch(type) {
            case 0:
                level.addToast(new Toast(240 ,"Weapons upgraded!"));
                weaponUpgradeLevel++;
                fireDelay = fireDelay * 0.95;
                break;
            case 1:
                level.addToast(new Toast(240 ,"Shields upgraded!"));
                shieldUpgradeLevel++;
                maxHealth += 5;
                health += 5;
                healthOffset = ((maxHealth * scale) / 2) - (sprite.getWidth() / 2);
                break;
            case 2:
                level.addToast(new Toast(240 ,"Engines upgraded!"));
                engineUpgradeLevel++;
                break;
        }
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

    public int getDefaultHealth() {
        return defaultHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
