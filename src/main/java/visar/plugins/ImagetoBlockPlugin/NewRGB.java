package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Material;

import java.awt.*;

public class NewRGB {
    static final Material[][][] materials = new Material[12][4][4];

    public static Material getClosest(Color c) {
        float[] hsb = Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null);
        int hue = (int) (hsb[0]*12+0.5f);
        int saturation = (int) (hsb[1]*4+0.5f);
        int brightness = (int) (hsb[2]*4+0.5f);
        return materials[hue][saturation][brightness];
    }
}
