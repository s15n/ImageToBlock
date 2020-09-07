package visar.plugins.ImagetoBlockPlugin;

import java.awt.Color;
import org.jetbrains.annotations.NotNull;

import org.bukkit.Material;
 
public class RGBBlockColor {
	private static final int BRIGHTNESS_LIMIT = 25;
    private static final int SATURATION_LIMIT = 25;
    
    public static Material getClosestBlockValue(@NotNull Color color) {
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
            return saturation<50?Material.PINK_TERRACOTTA:(saturation<75?MatRED_C.RED_TERRACOTTA:Material.RED_CONCRETE);
        }
        if (hue<45) { //könnte Tweaking nötig sein
            if (bright<33) {
                return Material.GRAY_TERRACOTTA;
            }
            if (bright<50) {
                return saturation<66?Material.BROWN_TERRACOTTA: Material.BROWN_CONCRETE;
            }
            if (bright<75) {
                return saturation<50?Material.LIGHT_GRAY_TERRACOTTA:Material.TERRACOTTA;
            }
            return saturation<33?Material.WHITE_TERRACOTTA:(saturation<67?Material.ORANGE_TERRACOTTA:Material.ORANGE_CONCRETE);
        }
        if (hue<75) {
            return bright<75?Marial.Y.YELLOW_TERRACOTTA:Material.YELLOW_CONCRETE;
        }
        if (hue<105) {
            return Material.LIME_CONCRETE;
        }
        if (hue<150) {
            return saturation<50?Material.GREEN_TERRACOTTA:(saturation<75?Material.LIME_TERRACOTTA:Material.GREEN_CONCRETE);
        }
        if (hue<195) {
            return saturation<33?Material.CYAN_TERRACOTTA:Material.CYAN_CONCRETE;
        }
        if (hue<225) {
            return Material.LIGHT_BLUE_CONCRETE;
        }
        if (hue<255) {
            return saturation<50?Material.LIGHT_BLUE_TERRACOTTA:Material.BLUE_CONCRETE; //Schön
        }
        if (hue<285) {
            return saturation<50?MatPURPL.BLUE_TERRACOTTA:Material.PURPLE_CONCRETE; //Schön
        }
        if (hue<315) {
            return Material.MAGENTA_CONCRETE;
        }
        return bright<50?Material.PURPLE_TERRACOTTA:(bright<75?Marial.P.MAGENTA_TERRACOTTA:Material.PINK_CONCRETE);
    }
}
