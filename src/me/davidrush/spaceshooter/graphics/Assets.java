package me.davidrush.spaceshooter.graphics;

import me.davidrush.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage player, enemyScout, enemyBomber, enemyFighter, enemyCruiser, hud, weapon, shield, engine, gameOver, title, controls, healthDrop, upgrade;
    public static BufferedImage[] stars, nebulae, galaxies, colors;
    public static Font font;
    public static void init() {
        stars = new BufferedImage[3];
        nebulae = new BufferedImage[3];
        colors = new BufferedImage[4];
        player = ImageLoader.loadImage("/textures/player.png");
        enemyScout = ImageLoader.loadImage("/textures/enemyScout.png");
        enemyBomber = ImageLoader.loadImage("/textures/enemyBomber.png");
        enemyFighter = ImageLoader.loadImage("/textures/enemyFighter.png");
        enemyCruiser = ImageLoader.loadImage("/textures/enemyCruiser.png");
        hud = ImageLoader.loadImage("/textures/powerHUDSprite.png");
        weapon = ImageLoader.loadImage("/textures/weapon.png");
        shield = ImageLoader.loadImage("/textures/shield.png");
        engine = ImageLoader.loadImage("/textures/engine.png");
        gameOver = ImageLoader.loadImage("/textures/gameOver.png");
        title = ImageLoader.loadImage("/textures/title.png");
        controls = ImageLoader.loadImage("/textures/controls.png");
        healthDrop = ImageLoader.loadImage("/textures/healthdrop.png");
        upgrade = ImageLoader.loadImage("/textures/upgrade.png");
        stars[0] = ImageLoader.loadImage("/textures/star0.png");
        stars[1] = ImageLoader.loadImage("/textures/star1.png");
        stars[2] = ImageLoader.loadImage("/textures/star2.png");
        nebulae[0] = ImageLoader.loadImage("/textures/nebula0.png");
        nebulae[1] = ImageLoader.loadImage("/textures/nebula1.png");
        nebulae[2] = ImageLoader.loadImage("/textures/nebula2.png");
        colors[0] = ImageLoader.loadImage("/textures/white.png");
        colors[1] = ImageLoader.loadImage("/textures/red.png");
        colors[2] = ImageLoader.loadImage("/textures/green.png");
        colors[3] = ImageLoader.loadImage("/textures/blue.png");
        font = new Font("Consolas", Font.BOLD, 20);
        System.out.println("Assets loaded");
    }
}
