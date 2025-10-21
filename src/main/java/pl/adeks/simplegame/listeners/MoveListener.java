package pl.adeks.simplegame.listeners;

import pl.adeks.simplegame.game.SimpleGame;
import pl.adeks.simplegame.map.elements.Player;

public class MoveListener {

    private final SimpleGame game;

    public MoveListener(final SimpleGame game) {
        this.game = game;
    }

    public void onMove(final Player player) {
        final KeyboardListener listener = this.game.getGamePanel().getKeyboardListener();
        if(listener == null) return;
        if(listener.isForward()) {
            player.move(0.5);
        }
        if(listener.isBack()) {
            player.move(-0.5);
        }
        if(listener.isTurnLeft()) {
            player.getCamera().naturalRotate(0, 0.1);
        }
        if(listener.isTurnRight()) {
            player.getCamera().naturalRotate(0, -0.1);
        }
        if(listener.isTurnUp()) {
            player.getCamera().naturalRotate(0.1, 0);
        }
        if(listener.isTurnDown()) {
            player.getCamera().naturalRotate(-0.1, 0);
        }
        if(listener.isUp()) {
            player.moveUpDown(0.5);
        }
        if(listener.isDown()) {
            player.moveUpDown(-0.5);
        }
    }
}
