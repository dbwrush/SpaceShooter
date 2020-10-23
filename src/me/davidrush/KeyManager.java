package me.davidrush;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    private boolean[] keys;
    public boolean up, down, left, right, fire, engineSelect, shieldSelect, weaponSelect, powerUp, powerDown, start, shift, escape;

    public KeyManager() {
        keys = new boolean[256];
    }

    public void tick() {
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        fire =  keys[KeyEvent.VK_SPACE];
        engineSelect = keys[KeyEvent.VK_3];
        shieldSelect = keys[KeyEvent.VK_2];
        weaponSelect = keys[KeyEvent.VK_1];
        powerUp = keys[KeyEvent.VK_R];
        powerDown = keys[KeyEvent.VK_F];
        start = keys[KeyEvent.VK_ENTER];
        shift = keys[KeyEvent.VK_SHIFT];
        escape = keys[KeyEvent.VK_ESCAPE];
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }
}
