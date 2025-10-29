package pl.adeks.simplegame.map;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import pl.adeks.simplegame.map.elements.Player;
import pl.adeks.simplegame.map.location.Location;
import pl.adeks.simplegame.map.elements.Block;

public class GameMap {

    private final Object2ObjectMap<Location, Block> map = new Object2ObjectOpenHashMap<>();
    private int maxBlockZ = Integer.MIN_VALUE;
    private int minBlockZ = Integer.MAX_VALUE;

    public GameMap() {
    }

    public Block getBlock(final Location location) {
        return this.map.get(location);
    }
    public void putBlock(final Block block) {
        this.maxBlockZ = Math.max(this.maxBlockZ, block.getLocation().getZ());
        this.minBlockZ = Math.min(this.minBlockZ, block.getLocation().getZ());
        //System.out.println("Max " + this.maxBlockZ + " min " + this.minBlockZ);
        this.map.put(block.getLocation(), block);
    }

    public void standardGenerate() {
        this.putBlock(new Block(new Location(5 + 2, 5, 5), new Color(128, 128, 182)));
        this.putBlock(new Block(new Location(5 - 2, 5, 5), new Color(128, 128, 182)));
        this.putBlock(new Block(new Location(5, 5 - 2, 5), new Color(128, 128, 182)));
        this.putBlock(new Block(new Location(5, 5 + 2, 5), new Color(128, 128, 182)));
        this.putBlock(new Block(new Location(5, 5, 5 - 2), new Color(128, 128, 182)));
        this.putBlock(new Block(new Location(5, 5, 5 + 2), new Color(128, 128, 182)));
        this.putBlock(new Block(new Location(6, 13, 6), new Color(128, 128, 182)));
    }

    public void randomGenerate() {
        final Random r = new Random();
        for(int i = 0; i < 100; i++) {
            final Location location = new Location(r.nextInt(16), r.nextInt(16), r.nextInt(16));
            final Block block = new Block(location, new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            this.putBlock(block);
        }
    }
    public void flatChunkGenerate() {
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                for(int z = 0; z < 1; z++) {
                    //if((x + y) % 2 == 0) continue;
                    final Location location = new Location(x, y, z);
                    final Block block = new Block(location, new Color(0, 0, 0));
                    this.putBlock(block);
                }
            }
        }
    }

    public void simpleGenerate() {
        final Location location = new Location(5, 6, 5);
        final Block block = new Block(location, new Color(0, 0, 0));
        this.putBlock(block);
    }

    //very slow
    public void generateGaussChunk() {
        final int chunkXMax = 100;
        final int chunkYMax = 100;

        final Random r = new Random();
        final int size = 1 + r.nextInt((int) Math.pow((chunkYMax + chunkYMax) / 2., 1.2));
        final List<Location> gaussLocations = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            gaussLocations.add(new Location(r.nextInt(chunkYMax), 1 + r.nextInt(3) , r.nextInt(chunkYMax)));
        }
        int general = 0;
        for(int x = 0; x < chunkXMax; x++) {
            for(int y = 0; y < chunkYMax; y++) {
                double val = 0;
                for(final Location location : gaussLocations) {
                    val += location.getY() * Math.sqrt(this.gauss(2.4, 2.4, location.getX(), location.getZ(), x, y));
                }
                System.out.println("generating... " + (int)((double)(general) / (chunkYMax * chunkXMax) * 100) + "%");
                for(int i = 0; i < val; i++) {
                    final Location location = new Location(x, y, -i);
                    final Block block = new Block(location, new Color(0, 0, 0));
                    this.putBlock(block);
                }
                general++;
            }
        }
    }

    private double gauss(final double sigmaX, final double sigmaY, final double locExtremumX, final double locExtremumY, final int x, final int y) {
        return  Math.exp(- 1./2. * (Math.pow((x - locExtremumX) / sigmaX, 2) + Math.pow((y - locExtremumY) / sigmaY, 2)));
    }

    public int getMaxBlockZ() {
        return this.maxBlockZ;
    }

    public int getMinBlockZ() {
        return this.minBlockZ;
    }
}
