package me.davidrush.spaceshooter.states;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleScreenState extends State{
    BufferedImage title, controls;
    public TitleScreenState(Game game) {
        super(game);
        title = Assets.title;
        controls = Assets.controls;
    }

    @Override
    public void tick() {
        if(game.getKeyManager().start) {
            game.startNew();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(title, ((game.width / 2) - title.getWidth() / 2), (game.height / 4), null);
        g.drawImage(controls, ((game.width / 2) - controls.getWidth() / 2), (game.height / 3), null);
        g.setColor(Color.WHITE);
        g.drawString("Press ENTER to start game!", (game.width / 2) - 135, 3 * (game.height / 4));
    }
}
