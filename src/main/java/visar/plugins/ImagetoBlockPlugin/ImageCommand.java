package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label,@NotNull String[] args)  {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4You are not a player!");
            return false;
        }

        Player player = (Player) sender;
        /*if (args.length < 1) {
            player.sendMessage("§cPlease provide an image! Use §f/image <URL> [width] [height]");
        }*/
        BufferedImage image = null;
        Main plugin = Main.getPlugin();
        int width;
        int height;

        if(plugin.getConfig().contains(player.getUniqueId().toString()+".vertical")) plugin.getConfig().set(player.getUniqueId().toString()+".vertical",false);
        if(plugin.getConfig().contains(player.getUniqueId().toString()+".togglevertwarning")) plugin.getConfig().set(player.getUniqueId().toString()+".togglevertwarning",false);

        try {
            if(args.length>0) {
                Integer.parseInt(args[0]);
            }
            image = Main.loadImageFromConfig(Main.getPlugin().getConfig().getString(player.getUniqueId().toString() + ".image"), player);
            if(image==null) {
                player.sendMessage("§cThere is no pre-defined image available.");
                player.sendMessage("§cPlease provide one using : §f/setdefaultimage <path>");
                return false;
            }
            width = args.length > 0 ? Integer.parseInt(args[0]) : image.getWidth();
            height = args.length > 1 ? Integer.parseInt(args[1]) : image.getHeight();
            if(!plugin.getConfig().getBoolean(player.getUniqueId().toString()+".vertical")) {
                ImageRenderer.renderImage(player.getLocation(), player.getLocation().clone().add(width, 0, height), image, player);
            }else ImageRenderer.renderImage(player.getLocation(), player.getLocation().clone().add(width, height,0), image, player);
            return true;
        } catch (NumberFormatException e) {
            try {
                if (args[0].startsWith("http") || args[0].startsWith("ftp")) {
                    image = ImageIO.read(new URL(args[0]));
                } else {
                    image = ImageIO.read(new File(args[0]));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if(image==null) {
            player.sendMessage("§cCould not find an image at the given path!");
            return false;
        }
        try {
            width = args.length > 1 ? Integer.parseInt(args[1]) : image.getWidth();
            height = args.length > 2 ? Integer.parseInt(args[2]) : image.getHeight();
            if(!plugin.getConfig().getBoolean(player.getUniqueId().toString()+".vertical")) {
                ImageRenderer.renderImage(player.getLocation(), player.getLocation().clone().add(width, 0, height), image, player);
            }else ImageRenderer.renderImage(player.getLocation(), player.getLocation().clone().add(width, height,0), image, player);
        } catch (NumberFormatException e) {
            player.sendMessage("§cThat was not a number!");
            return false;
        }
        return true;
    }
}
