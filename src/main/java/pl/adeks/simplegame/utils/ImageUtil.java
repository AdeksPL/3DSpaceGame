package pl.adeks.simplegame.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public final class ImageUtil {

    public static BufferedImage read(final String str) {
        try {
            return ImageIO.read(new File(str));
        } catch (final Exception e) { e.printStackTrace();}
        return null;
    }
}
