package me.davidrush.spaceshooter.entities;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Laser extends Entity{
    BufferedImage color;
    float xSpeed, ySpeed;
    int scale = 3, strength;
    Actor creator;
    public Laser(float x, float y, float acceleration, double angle, BufferedImage color, Actor creator, int strength, Level level, Game game, float creatorYMove) {
        super(x, y, acceleration, color.getWidth(), color.getHeight(), level, game);
        this.color = color;
        this.creator = creator;
        this.strength = strength;
        ySpeed = (float)Math.sin(angle) * acceleration + creatorYMove;
        xSpeed = (float)Math.cos(angle) * acceleration;
    }

    @Override
    public void tick() {
        x+=xSpeed;
        y-=ySpeed;
        if(y < level.getCameraY() || y > level.getCameraY() + game.height || x < 0 || x > game.width) {
            level.removeEntity(this);
        }
        for(Actor actor : level.getActors()) {
            if(!actor.equals(creator) && checkCollide(actor, x, y)) {//Hit an enemy
                if(!(creator instanceof Player)) {//Came from an enemy
                    strength *= (Math.random() * 0.2);
                }
                int aHealth = actor.getHealth();
                actor.damage(strength);
                strength -= aHealth;
                if(strength <= 0) {//If a laser does more than enough damage to kill an enemy, the laser can go through that enemy to hit the one behind it!
                    level.removeEntity(this);
                }
            }
        }
        if(!level.getPlayer().equals(creator) && checkCollide(level.getPlayer(), x, y)) {//Hit the player
            level.getPlayer().damage(strength);
            level.removeEntity(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.colors[0], (int)x - (color.getWidth() * scale / 2), (int)(y + (color.getHeight() * scale / 2) - level.getCameraY()), color.getWidth() * scale, color.getHeight() * scale, null);
        for(int i = 0; i < strength; i++) {
            g.drawImage(color, (int)(x - (color.getWidth() * scale / 2) - (xSpeed / acceleration) * 2 * i), (int)((y + (color.getHeight() * scale / 2) + (ySpeed / acceleration) * 2 * i) - level.getCameraY()), color.getWidth() * scale, color.getHeight() * scale, null);
        }
    }
}

//todo Add Enemy Carrier (spawns Scouts and Fighters until destroyed.)
//todo Add Enemy Controller (makes enemy movmenents around it more coordinated. Heals enemies. More powerful/more healthy enemies move to the front, weaker ones move back.
//todo Add ship upgrade drops. Give a permanent buff to one aspect of the ship. More powerful turret, better shields, etc.
