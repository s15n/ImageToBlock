package visar.plugins.ImagetoBlockPlugin;

import java.awt.Color;

import org.bukkit.Material;
 
public class RGBBlockColor {
	private static final int BRIGHTNESS_LIMIT = 25;
    private static final int SATURATION_LIMIT = 25;
    
    public static Material getClosestBlockValue(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null);
        int hue = (int) (hsb[0]*360);
        int saturation = (int) (hsb[1]*100);
        int bright = (int) (hsb[2]*100);
        
        if (bright<BRIGHTNESS_LIMIT) {
            return Material.BLACK_CONCRETE;
        }
        
        if (saturation<SATURATION_LIMIT) {
            if (bright<25) {
                return Material.BLACK_CONCRETE;
            }
            if (bright<50) {
                return Material.GRAY_CONCRETE;
            }
            if (bright<75) {
                return Material.LIGHT_GRAY_CONCRETE;
            }
            return Material.WHITE_CONCRETE;
        }
        
        if (hue<15||hue>=345) {
            return Material.RED_CONCRETE;
        }
        if (hue<45) {
            return bright<67?Material.BROWN_CONCRETE:Material.ORANGE_CONCRETE;
        }
        if (hue<75) {
            return Material.YELLOW_CONCRETE;
        }
        if (hue<105) {
            return Material.LIME_CONCRETE;
        }
        if (hue<145) {
            return Material.GREEN_CONCRETE;
        }
        if (hue<210) {
            return Material.CYAN_CONCRETE;
        }
        if (hue<255) {
            return Material.BLUE_CONCRETE;
        }
        if (hue<285) {
            return Material.PURPLE_CONCRETE;
        }
        if (hue<315) {
            return Material.MAGENTA_CONCRETE;
        }
        return Material.PINK_CONCRETE;
    }
}
