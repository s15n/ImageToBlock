package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import visar.plugins.ImagetoBlockPlugin.renderers.ImageRenderer;


public class ItemFrameCanvas {
    private static final Main plugin = Main.getPlugin();
    public static void createCanvas(Player p, int width, int height) {
        String path = p.getUniqueId().toString()+".itemframe";
        String exists = path+".canvasexists";
        Location l = p.getLocation();
        ItemStack[][] maps = new ItemStack[width][height];
        if(!plugin.getConfig().contains(exists)) {
            plugin.getConfig().set(exists,false);
        }
        if(!plugin.getConfig().getBoolean(exists)) {
            boolean vertical = plugin.getConfig().getBoolean(p.getUniqueId().toString() + ".vertical");
            ImageRenderer.renderItemFrameImage(p,width,height,null);
            plugin.getConfig().set(exists,true);
            plugin.getConfig().set(path+".location",l);
            plugin.getConfig().set(path+".width",width);
            plugin.getConfig().set(path+".height",height);
            plugin.getConfig().set(path+".itemframes",maps);
            plugin.saveConfig();
            if (vertical) {
                p.sendMessage("§4Canvas created vertically");
            } else p.sendMessage("§4Canvas created horizontally");
        }else p.sendMessage("§cCanvas already exists, if you want to create a new one you need to delete your old canvas with §6/delifcanvas");
    }
    public static void deleteCanvas(String path) {
        plugin.getConfig().set(path,null);
        plugin.saveConfig();
    }
}
