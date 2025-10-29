package pl.adeks.simplegame.game;

import javax.swing.JFrame;
import pl.adeks.simplegame.listeners.KeyboardListener;
import pl.adeks.simplegame.listeners.MoveListener;
import pl.adeks.simplegame.map.GameMap;
import pl.adeks.simplegame.map.elements.camera.Camera;
import pl.adeks.simplegame.map.elements.Player;
import pl.adeks.simplegame.map.location.DoubleLocation;
import pl.adeks.simplegame.panel.GamePanel;

public class SimpleGame extends JFrame {

    private final Player player;
    private final GamePanel gamePanel;
    private final GameMap gameMap;
    private final MoveListener moveListener;
    private Thread gameThread;
    private Camera oldCamera;
    private DoubleLocation oldLocation;

    private static SimpleGame inst;
    private boolean enabled;

    public SimpleGame() {
        inst = this;
        this.enabled = true;
        this.player = new Player(new DoubleLocation(5.0, 5.0, -15.0), new Camera());
        this.moveListener = new MoveListener(this);
        this.gameMap = new GameMap();
        this.gameMap.generateGaussChunk();
        this.gamePanel = new GamePanel(800, 480);
        this.add(this.gamePanel);
        this.pack();
        this.setSize(800, 480);
        this.setResizable(false);
        this.runGameThread();
        this.gamePanel.addKeyListener(new KeyboardListener());
        System.out.println("Game loaded");
    }

    public static SimpleGame getInst() {
        return inst;
    }

    public void runGameThread() {
        this.gameThread = new Thread(() -> {
            while(true) {
                final long time = System.nanoTime();
                if(!this.player.getCamera().equals(this.oldCamera) || !this.player.getLocation().equals(this.oldLocation)) {
                    this.gamePanel.render();
                    this.gamePanel.repaint();
                }
                this.oldCamera = this.player.getCamera().clone();
                this.oldLocation = this.player.getLocation().clone();
                this.moveListener.onMove(this.player);

                final long loopTime = (System.nanoTime() - time);
                if(loopTime > 200000) System.out.println(loopTime);
            }
        });
        this.gameThread.start();
    }

    public Thread getGameThread() {
        return this.gameThread;
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    public Player getPlayer() {
        return this.player;
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
