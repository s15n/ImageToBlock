package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer {
    private static final Main plugin = Main.getPlugin();

    public static void renderImage(@NotNull Location l, int width, int height, @Nullable BufferedImage img, Player p) {
        Location l2 = l.clone();
        String path = p.getUniqueId().toString();
        boolean vert = plugin.getConfig().getBoolean(path+".vertical");
        BufferedImage resimg = img;
        if(img != null && img.getHeight() != height && img.getWidth() != width) resimg = resizingImage(img,width,height);

        for(int i=0; i < width; i++) {
            for(int j=0; j< height; j++) {
                l2.add(0,vert ? -1 : 0,vert ? 0 : 1).getBlock().setType(img == null ? Material.WHITE_CONCRETE : NewRGB.getClosestBlockValue(new Color(resimg.getRGB(i,j))));
            }
            l2.add(1,vert ? height : 0,vert ? 0 : -height);
        }
        if(!plugin.getConfig().getBoolean(p.getUniqueId().toString()+".togglevertwarning")) {
            p.sendMessage("§aImage displayed" + (vert ? "vertically":"horizontally"));
            p.sendMessage("§cIf you want to display the image"+ (vert ? "horizontally":"vertically") + "use §6/vertical"+ (vert ? "off":"on"));
            p.sendMessage("§cIf you don't want to see this warning anymore use §6/togglevertwarning");
        }
    }

    public static BufferedImage resizingImage(BufferedImage srcimage,int new_width, int new_height) {

        BufferedImage resizedImage = new BufferedImage(new_width, new_height, srcimage.getType());
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcimage, 0, 0, new_width, new_height, null);
        g2.dispose();

        return resizedImage;
    }
}
