package pl.adeks.simplegame.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.swing.JPanel;
import pl.adeks.simplegame.game.SimpleGame;
import pl.adeks.simplegame.listeners.KeyboardListener;
import pl.adeks.simplegame.map.GameMap;
import pl.adeks.simplegame.map.elements.Block;
import pl.adeks.simplegame.map.elements.camera.Camera;
import pl.adeks.simplegame.map.elements.Player;
import pl.adeks.simplegame.map.location.DoubleLocation;
import pl.adeks.simplegame.map.location.Location;
import pl.adeks.simplegame.utils.MathUtil;
import pl.adeks.simplegame.math.Vector;

public class GamePanel extends JPanel {

    private final int width;
    private final int height;
    private final BufferedImage bufferedImage;
    private final GameMap gameMap;
    private final Player player;
    private final Camera playerCamera;
    private KeyboardListener keyboardListener;
    private final ThreadPoolExecutor threadExecutor;

    public GamePanel(final int width, final int height) {
        final SimpleGame game = SimpleGame.getInst();
        this.gameMap = game.getGameMap();
        this.player = game.getPlayer();
        this.playerCamera = game.getPlayer().getCamera();
        this.width = width;
        this.height = height;
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.threadExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(6);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void render() {
        final DoubleLocation playerLocation = this.player.getLocation();

        final int[] pixelArray = new int[this.width * this.height];
        for(int x = 0; x < this.width; x++) {
            final int finalX = x;
            this.threadExecutor.submit(() -> {
                for(int y = 0; y < this.height; y++) {
                    final double camX = 2. * finalX / this.width - 1;
                    final double camZ = 2. * y / this.height - 1;

                    final Vector rayVector = this.playerCamera.getDirection()
                            .addVector(this.playerCamera.getPlaneX().multiply(camX))
                            .addVector(this.playerCamera.getPlaneZ().multiply(camZ));

                    //DDA grid-based based on https://lodev.org/cgtutor/raycasting.html / ChatGPT but I generalised it into 3D by myself
                    //that's just find every cross points with walls by solving parametric equation of the form x_l + a_l * t = n, where n is an specific integer
                    final double deltaDistX = rayVector.getX() == 0 ? 1e30 : Math.abs(1 / rayVector.getX());
                    final double deltaDistY = rayVector.getY() == 0 ? 1e30 : Math.abs(1 / rayVector.getY());
                    final double deltaDistZ = rayVector.getZ() == 0 ? 1e30 : Math.abs(1 / rayVector.getZ());

                    double sideDistX;
                    final int sideX;
                    final Location playerMapLocation = playerLocation.parseToLocation();

                    if(rayVector.getX() <= 0) {
                        sideDistX = (playerLocation.getX() - playerMapLocation.getX()) * deltaDistX;
                        sideX = -1;
                    } else {
                        sideDistX = (playerMapLocation.getX() - playerLocation.getX() + 1) * deltaDistX;
                        sideX = 1;
                    }

                    double sideDistY;
                    final int sideY;
                    if(rayVector.getY() <= 0) {
                        sideDistY = (playerLocation.getY() - playerMapLocation.getY()) * deltaDistY;
                        sideY = -1;
                    } else {
                        sideDistY = (playerMapLocation.getY() - playerLocation.getY() + 1) * deltaDistY;
                        sideY = 1;
                    }

                    double sideDistZ;
                    final int sideZ;
                    if(rayVector.getZ() <= 0) {
                        sideDistZ = (playerLocation.getZ() - playerMapLocation.getZ()) * deltaDistZ;
                        sideZ = -1;
                    } else {
                        sideDistZ = (playerMapLocation.getZ() - playerLocation.getZ() + 1) * deltaDistZ;
                        sideZ = 1;
                    }

                    Block block = null;
                    boolean ended = false;
                    int colorModifier = 0;

                    double rayLocX = 0;
                    double rayLocY = 0;
                    double rayLocZ = 0;

                    int val = 0;

                    while(block == null && !ended) {
                        final double minimum = MathUtil.minimum(sideDistX, sideDistY, sideDistZ);

                        rayLocX = playerLocation.getX() + minimum * rayVector.getX();
                        rayLocY = playerLocation.getY() + minimum * rayVector.getY();
                        rayLocZ = playerLocation.getZ() + minimum * rayVector.getZ();

                        if(sideDistZ == minimum) {
                            sideDistZ += deltaDistZ;
                            playerMapLocation.addZ(sideZ);
                            colorModifier = 3;
                        } else
                        if(sideDistY == minimum) {
                            sideDistY += deltaDistY;
                            playerMapLocation.addY(sideY);
                            colorModifier = 2;
                        } else
                        if(sideDistX == minimum) {
                            sideDistX += deltaDistX;
                            playerMapLocation.addX(sideX);
                            colorModifier = 1;
                        }

                        block = this.gameMap.getBlock(playerMapLocation);
                        //if(this.gameMap.getMaxBlockZ() < playerLocation.getZ() || this.gameMap.getMinBlockZ() > playerLocation.getZ()) ended = true;
                        if(val > 50) ended = true; // that's render distance
                        val++;
                    }

                    //End of DDA algorithm
                    int blockColor = new Color(0, 0 , 0).getRGB();
                    if(block != null) {

                        if(colorModifier == 1)
                            blockColor = block.getColorTexture(rayLocY - playerMapLocation.getY(), rayLocZ - playerMapLocation.getZ());
                        if(colorModifier == 2)
                            blockColor = block.getColorTexture(rayLocX - playerMapLocation.getX(), rayLocZ - playerMapLocation.getZ());
                        if(colorModifier == 3)
                            blockColor = block.getColorTexture(rayLocX - playerMapLocation.getX(), rayLocY - playerMapLocation.getY());

                        int shadow = colorModifier;
                        while(shadow > 0)  {
                            blockColor = new Color(blockColor).darker().getRGB();
                            shadow--;
                        }
                    }
                    if((this.width / 2 - 3 <= finalX && finalX <= this.width / 2 + 3) && (this.height / 2 - 3 <= y && y <= this.height / 2 + 3))
                        blockColor = ~blockColor;

                    pixelArray[finalX + y * this.width] = blockColor;
                }
            });
        }
        while(this.threadExecutor.getActiveCount() != 0 || !this.threadExecutor.getQueue().isEmpty()) {
            try {Thread.sleep(10); } catch (final Exception e) {}
        }

        final int[] data = ((DataBufferInt) this.bufferedImage.getRaster().getDataBuffer()).getData();
        System.arraycopy(pixelArray, 0, data, 0, this.width * this.height); // maybe that's more optimal
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bufferedImage, 0, 0, null);
    }

    @Override
    public void addKeyListener(final KeyListener listener) {
        super.addKeyListener(listener);
        if(listener instanceof final KeyboardListener keyboardListener) {
            this.keyboardListener = keyboardListener;
        }
    }
    public KeyboardListener getKeyboardListener() {
        return this.keyboardListener;
    }

}
