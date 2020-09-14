package me.davidrush.spaceshooter;

import me.davidrush.spaceshooter.entities.Player;
import me.davidrush.spaceshooter.graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {
    private Player player;
    private BufferedImage sprite;
    private BufferedImage[] powerSprites;
    private int x, y, shield, weapon, engine, unused, scale = 5, activePower;

    public HUD(Player player, Game game) {
        this.player = player;
        sprite = Assets.hud;
        powerSprites = new BufferedImage[3];
        powerSprites[0] = Assets.weapon;
        powerSprites[1] = Assets.shield;
        powerSprites[2] = Assets.engine;
        x = scale;
        y = game.height - scale - (sprite.getHeight() * scale);
    }

    public void tick() {
        unused = player.getAvailablePower();
        weapon = player.getPower()[0];
        shield = player.getPower()[1];
        engine = player.getPower()[2];
        activePower = player.getPowerSelect();
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x, y, sprite.getWidth() * scale, sprite.getHeight() * scale, null);
        g.drawImage(powerSprites[activePower], x, y - ((powerSprites[activePower].getHeight()  + 1) * scale), powerSprites[activePower].getWidth() * scale, powerSprites[activePower].getHeight() * scale, null);
        drawPowerBar(weapon, x + (2 * scale), y + (2 * scale), Assets.colors[1], g);//draw weapon bar
        drawPowerBar(shield, x + (6 * scale), y + (2 * scale), Assets.colors[3], g);//draw shield bar
        drawPowerBar(engine, x + (10 * scale), y + (2 * scale), Assets.colors[2], g);//draw engine bar
        drawPowerBar(unused, x + (14 * scale), y + (2 * scale), Assets.colors[0], g);//draw unused bar
        g.setColor(Color.WHITE);
        g.drawString("Health: " + player.getHealth(), x + (sprite.getWidth() + 1) * scale, y + (16 * scale));
    }
    private void drawPowerBar(int power, int x, int y, BufferedImage color, Graphics g) {
        for(int i = 0; i < power; i++) {
            g.drawImage(color, x, y + (scale * i), color.getWidth() * scale, color.getHeight() * scale, null);
        }
    }
}
