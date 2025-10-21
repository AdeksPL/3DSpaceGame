package pl.adeks.simplegame.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private boolean forward;
    private boolean back;
    private boolean turnLeft;
    private boolean turnRight;
    private boolean turnUp;
    private boolean turnDown;
    private boolean up;
    private boolean down;

    @Override
    public void keyTyped(final KeyEvent e) {}

    @Override
    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.forward = true;
            case KeyEvent.VK_S -> this.back = true;
            case KeyEvent.VK_LEFT -> this.turnLeft = true;
            case KeyEvent.VK_RIGHT -> this.turnRight = true;
            case KeyEvent.VK_UP -> this.turnUp = true;
            case KeyEvent.VK_DOWN -> this.turnDown = true;
            case KeyEvent.VK_SPACE -> this.up = true;
            case KeyEvent.VK_SHIFT -> this.down = true;
            case KeyEvent.VK_ESCAPE -> System.exit(0);
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.forward = false;
            case KeyEvent.VK_S -> this.back = false;
            case KeyEvent.VK_LEFT -> this.turnLeft = false;
            case KeyEvent.VK_RIGHT -> this.turnRight = false;
            case KeyEvent.VK_UP -> this.turnUp = false;
            case KeyEvent.VK_DOWN -> this.turnDown = false;
            case KeyEvent.VK_SPACE -> this.up = false;
            case KeyEvent.VK_SHIFT -> this.down = false;
        }
    }

    public boolean isBack() {
        return this.back;
    }
    public boolean isForward() {
        return this.forward;
    }
    public boolean isTurnLeft() {
        return this.turnLeft;
    }
    public boolean isTurnRight() {
        return this.turnRight;
    }
    public boolean isTurnUp() {
        return this.turnUp;
    }
    public boolean isTurnDown() {
        return this.turnDown;
    }
    public boolean isUp() {
        return this.up;
    }
    public boolean isDown() {
        return this.down;
    }
}
