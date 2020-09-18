package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Canvas implements Listener {
    private static final Main plugin = Main.getPlugin();

    public static void createCanvas(Location l, int width, int height, Player p) {
        String path = p.getUniqueId().toString()+".canvas";
        if(plugin.getConfig().getBoolean(path)) {
            boolean vertical = plugin.getConfig().getBoolean(p.getUniqueId().toString() + ".vertical");
            try {
                ImageRenderer.renderImage(p.getLocation(), p.getLocation().clone().add(width, vertical ? height : 0, vertical ? 0 : height), ImageIO.read(new File(Canvas.class.getResource("/main/resources/white3.jpg").getFile())),p);
            }catch(IOException e) {
                e.printStackTrace();
                p.sendMessage("§cSomething went wrong! Try again!");
                return;
            }
            plugin.getConfig().set(path,true);
            plugin.getConfig().set(path+".location",l);
            plugin.getConfig().set(path+".width",width);
            plugin.getConfig().set(path+".height",height);
            plugin.saveConfig();
            if (vertical) {
                p.sendMessage("§4Canvas created vertically");
            } else p.sendMessage("§4Canvas created horizontally");
        }else p.sendMessage("§cCanvas already exists, if you want to create a new one you need to delete your old canvas with /delcanvas");
    }
    public static void deleteCanvas(String path) {
        plugin.getConfig().set(path,null);
        plugin.saveConfig();
    }

}
