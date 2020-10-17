package visar.plugins.ImagetoBlockPlugin.renderers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import visar.plugins.ImagetoBlockPlugin.Main;
import visar.plugins.ImagetoBlockPlugin.NewRGB;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

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
    public static void renderItemFrameImage(Player p,int width, int height, BufferedImage img) {
        String path = p.getUniqueId().toString();
        Location l = (Location) plugin.getConfig().get(path+".itemframe.location");
        //boolean vert = plugin.getConfig().getBoolean(path+".vertical");
        ItemStack[][] maps = (ItemStack[][]) plugin.getConfig().get(path+".itemframe.itemframes");
        if(img != null) maps = imageToMaps(img, width, height);
        assert l != null;
        for(int i=0; i < width; i++) {
            for(int j=0; j< height; j++) {
                l.add(0,1,0).getBlock().setType(Material.BLACK_WOOL);
                ItemFrame itemFrame = p.getLocation().getWorld().spawn(l.clone().add(0,0,1),ItemFrame.class);
                itemFrame.setItem(img == null ? new ItemStack(Material.BARRIER) : maps[i][j]);
            }
            l.add(1,-height,0);
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
    public static ItemStack[][] imageToMaps(BufferedImage img, int width, int height) {
        ItemStack[][] maps = new ItemStack[width][height];
        int widthRatio = img.getWidth()/width,heightRatio = img.getHeight()/height;
        for(int i=0; i<width; i++) {
            for(int j=0; j< height; j++){
                BufferedImage mapImg = img.getSubimage(i*widthRatio,j*heightRatio,widthRatio,heightRatio);
                ItemStack tempMap = new ItemStack(Material.MAP);
                MapMeta mapMeta = (MapMeta) tempMap.getItemMeta();
                Objects.requireNonNull(mapMeta.getMapView()).getRenderers().clear();
                mapMeta.getMapView().addRenderer(new MapRenderer() {
                    @Override
                    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
                        canvas.drawImage(0,0,mapImg);
                    }
                });
                maps[i][j] = tempMap;
            }
        }
        return maps;
    }
}
