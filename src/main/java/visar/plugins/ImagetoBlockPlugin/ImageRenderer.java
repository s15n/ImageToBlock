package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer {
    private static final Main plugin = Main.getPlugin();
    public static void renderImage(@NotNull Location Location1, @NotNull Location Location2, @NotNull BufferedImage img,@NotNull Player player) {
        String path = player.getUniqueId().toString()+".vertical";
        if(!plugin.getConfig().contains(player.getUniqueId().toString()+".togglevertwarning")) plugin.getConfig().set(player.getUniqueId().toString()+".togglevertwarning",false);
        if(!plugin.getConfig().contains(path)) plugin.getConfig().set(path,false);
        if(plugin.getConfig().getBoolean(path)) {
            int bigX = Math.max(Location1.getBlockX(), Location2.getBlockX()),
                    smallX = bigX == Location1.getBlockX() ? Location2.getBlockX() : Location1.getBlockX(),
                    bigY = Math.max(Location1.getBlockY(), Location2.getBlockY()),
                    smallY = bigY == Location1.getBlockY() ? Location2.getBlockY() : Location1.getBlockY();
            try {
                BufferedImage resizedImage = img;
                if ((bigX - smallX) != img.getWidth() && (bigY - smallY) != img.getHeight()) {
                    resizedImage = resizingImage(img, (bigX - smallX), (bigY - smallY));
                }
                int row = 0;
                for (int i = smallY; i < bigY; i++) {
                    int column = 0;
                    for (int j = smallX; j < bigX; j++) {
                        Location l = new Location(Location1.getWorld(), j,i,Location1.getBlockZ());
                        Block b = l.getBlock();
                        b.setType(RGBBlockColor.getClosestBlockValue(new Color(resizedImage.getRGB(column, row))));
                        column++;
                    }
                    row++;
                }
                if(!plugin.getConfig().getBoolean(player.getUniqueId().toString()+".togglevertwarning")) {
                    player.sendMessage("§aImage displayed vertically");
                    player.sendMessage("§cIf you want to display the image horizontally use §6/vertical off§r");
                    player.sendMessage("§cIf you don't want to see this warning anymore use §6/togglevertwarning");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                player.sendMessage("Problems while resizing picture");
            }
        }else {
            int bigX = Math.max(Location1.getBlockX(), Location2.getBlockX()),
                    smallX = bigX == Location1.getBlockX() ? Location2.getBlockX() : Location1.getBlockX(),
                    bigZ = Math.max(Location1.getBlockZ(), Location2.getBlockZ()),
                    smallZ = bigZ == Location1.getBlockZ() ? Location2.getBlockZ() : Location1.getBlockZ();
            try {
                BufferedImage resizedImage = img;
                if ((bigX - smallX) != img.getWidth() && (bigZ - smallZ) != img.getHeight()) {
                    resizedImage = resizingImage(img, (bigX - smallX), (bigZ - smallZ));
                }
                int row = 0;
                for (int i = smallZ; i < bigZ; i++) {
                    int column = 0;
                    for (int j = smallX; j < bigX; j++) {
                        Location l = new Location(Location1.getWorld(), j, Location1.getBlockY(), i);
                        Block b = l.getBlock();
                        b.setType(RGBBlockColor.getClosestBlockValue(new Color(resizedImage.getRGB(column, row))));
                        column++;
                    }
                    row++;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                player.sendMessage("Problems while resizing picture");
            }
            if(!plugin.getConfig().getBoolean(player.getUniqueId().toString()+".togglevertwarning")) {
                player.sendMessage("§aImage displayed horizontally");
                player.sendMessage("§cIf you want to display the image vertically use §6/vertical on§r");
                player.sendMessage("§cIf you don't want to see this warning anymore use §6/togglevertwarning");
            }
        }
    }

    public static void renderImageLite(@NotNull Location l, int w, int h, @NotNull BufferedImage img, Player p, boolean vert) {
       // boolean vert = /*plugin.getConfig().getBoolean(p.getUniqueId().toString()+".vertical")*/true;
        renderHelper(l,w,h,img,vert, p);
    }

    private static void renderHelper(@NotNull Location l, int w, int h, @NotNull BufferedImage img,boolean vertical, Player  player) {
        Location lc = l.clone();
        if(vertical) {
            lc.add(0,h,0);
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    try {
                        lc.add(0, -1, 0).getBlock().setType(RGBBlockColor.getClosestBlockValue(new Color(img.getRGB(x, y))));
                        //player.sendBlockChange(lc.add(0,-1,0),RGBBlockColor.getClosestBlockValue(new Color(img.getRGB(x, y))).createBlockData());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return;
                    }
                }
                lc.add(1,h,0);
            }
        }else {
            for(int x = 0; x<w; x++) {
                for(int z = 0; z<h; z++) {
                    try {
                        lc.add(0,0,1).getBlock().setType(RGBBlockColor.getClosestBlockValue(new Color(img.getRGB(x,z))));
                        //player.sendBlockChange(lc.add(0,0,1),RGBBlockColor.getClosestBlockValue(new Color(img.getRGB(x, z))).createBlockData());
                    }catch(ArrayIndexOutOfBoundsException e) {
                        return;
                    }
                }
                lc.add(1,0,-h);
            }
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
