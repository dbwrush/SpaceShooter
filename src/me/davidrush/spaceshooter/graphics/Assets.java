package me.davidrush.spaceshooter.graphics;

import me.davidrush.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage player, enemyScout, enemyBomber, enemyFighter, enemyCruiser, enemyCarrier, hud, weapon, shield, engine, gameOver, title, controls, healthDrop, upgrade;
    public static BufferedImage[] stars, nebulae, galaxies, colors, explosion;
    public static Font font;
    public static void init() {
        stars = new BufferedImage[3];
        nebulae = new BufferedImage[3];
        colors = new BufferedImage[4];
        explosion = new BufferedImage[16];
        player = ImageLoader.loadImage("/textures/player.png");
        enemyScout = ImageLoader.loadImage("/textures/enemyScout.png");
        enemyBomber = ImageLoader.loadImage("/textures/enemyBomber.png");
        enemyFighter = ImageLoader.loadImage("/textures/enemyFighter.png");
        enemyCruiser = ImageLoader.loadImage("/textures/enemyCruiser.png");
        enemyCarrier = ImageLoader.loadImage("/textures/enemyCarrier.png");
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
        explosion[0] = ImageLoader.loadImage("/textures/explosion1.png");
        explosion[1] = ImageLoader.loadImage("/textures/explosion2.png");
        explosion[2] = ImageLoader.loadImage("/textures/explosion3.png");
        explosion[3] = ImageLoader.loadImage("/textures/explosion4.png");
        explosion[4] = ImageLoader.loadImage("/textures/explosion5.png");
        explosion[5] = ImageLoader.loadImage("/textures/explosion6.png");
        explosion[6] = ImageLoader.loadImage("/textures/explosion7.png");
        explosion[7] = ImageLoader.loadImage("/textures/explosion8.png");
        explosion[8] = ImageLoader.loadImage("/textures/explosion9.png");
        explosion[9] = ImageLoader.loadImage("/textures/explosion10.png");
        explosion[10] = ImageLoader.loadImage("/textures/explosion11.png");
        explosion[11] = ImageLoader.loadImage("/textures/explosion12.png");
        explosion[12] = ImageLoader.loadImage("/textures/explosion13.png");
        explosion[13] = ImageLoader.loadImage("/textures/explosion14.png");
        explosion[14] = ImageLoader.loadImage("/textures/explosion15.png");
        explosion[15] = ImageLoader.loadImage("/textures/explosion16.png");
        font = new Font("Consolas", Font.BOLD, 20);
        System.out.println("Assets loaded");
    }
}
