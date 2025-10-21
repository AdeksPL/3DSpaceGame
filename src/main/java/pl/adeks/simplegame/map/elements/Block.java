package pl.adeks.simplegame.map.elements;

import java.awt.Color;
import java.awt.image.BufferedImage;
import pl.adeks.simplegame.map.location.Location;
import pl.adeks.simplegame.utils.ImageUtil;

public class Block {

    private final Location location;
    private final BufferedImage texture;

    public Block(final Location location, final Color color) {
        this.location = location;
        this.texture = ImageUtil.read("textures/dirt.jpg");
    }

    public Location getLocation() {
        return this.location;
    }

    public int getColorTexture(final double x, final double y) {
        int pointX = (int) (this.texture.getWidth() * x);
        int pointY = (int) (this.texture.getHeight() * y);

        if(pointX >= this.texture.getWidth())
            pointX = this.texture.getWidth() - 1;

        if(pointY >= this.texture.getHeight())
            pointY = this.texture.getHeight() - 1;

        if(pointX < 0) pointX = 0;
        if(pointY < 0) pointY = 0;

        return this.texture.getRGB(pointX, pointY);
    }

    @Override
    public String toString() {
        return "Block{" +
                "location=" + this.location +
                ", texture=" + this.texture +
                '}';
    }
}
