package pl.adeks.simplegame;

import javax.swing.SwingUtilities;
import pl.adeks.simplegame.game.SimpleGame;

public final class Main {
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final SimpleGame game = new SimpleGame();
            game.setVisible(true);
        });
    }
}