package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer {
    public static void renderImage(@NotNull Location Location1, @NotNull Location Location2, @NotNull BufferedImage img,@NotNull Player player) {

        Main plugin = Main.getPlugin();
        String path = player.getUniqueId().toString()+".locations";
        int bigX = Math.max(Location1.getBlockX(), Location2.getBlockX()),
            smallX = bigX == Location1.getBlockX() ? Location2.getBlockX() : Location1.getBlockX(),
            bigZ = Math.max(Location1.getBlockZ(), Location2.getBlockZ()),
            smallZ = bigZ == Location1.getBlockZ() ? Location2.getBlockZ() : Location1.getBlockZ();
        try {
            BufferedImage resizedImage = resizingImage(img,(bigX-smallX),(bigZ-smallZ));
            System.out.println("x - " + (bigX - smallX));
            System.out.println("z - " + (bigZ - smallZ));
            int row = 0;
            for(int i = smallZ; i<bigZ; i++) {
                int column = 0;
                for(int j=smallX; j<bigX; j++) {
                    Location l = new  Location(Location1.getWorld(), j, Location1.getBlockY(), i);
                    Block b = l.getBlock();
                    b.setType(RGBBlockColor.getClosestBlockValue(new Color(resizedImage.getRGB(column, row))));
                    column++;
                }
                row++;
            }
            plugin.getConfig().set(path+".firstloc",null);
            plugin.getConfig().set(path+".secondloc",null);
        } catch (Exception e1) {
            player.sendMessage("Problems while resizing picture");
        }

    }

    private static BufferedImage resizingImage(BufferedImage srcimage,int new_width, int new_height) {

        BufferedImage resizedImage = new BufferedImage(new_width, new_height, srcimage.getType());
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcimage, 0, 0, new_width, new_height, null);
        g2.dispose();

		/*File imageFile = new File("C:/users/Simon/Desktop/Visar Server/test_resized.png");
		ImageIO.write(resizedImage,"png",imageFile);*/
        return resizedImage;
    }
}
