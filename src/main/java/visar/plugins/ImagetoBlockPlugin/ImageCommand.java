package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label,@NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4You are not a player!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage("§cPlease provide an image! Use §f/image <URL> [width] [height]");
        }
        BufferedImage image = null;
        try {
            if (args[0].startsWith("http") || args[0].startsWith("ftp")) {
                image = ImageIO.read(new URL(args[0]));
            } else {
                image = ImageIO.read(new File(args[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.image = image;
        Bukkit.getPluginManager().callEvent(new PlayerInteractEvent(player, Action.LEFT_CLICK_BLOCK, new ItemStack(Material.WOODEN_AXE), player.getLocation().getBlock(), BlockFace.UP));
        int width;
        int height;
        try {
             width = args.length > 1 ? Integer.parseInt(args[1]) : image.getWidth();
             height = args.length > 2 ? Integer.parseInt(args[2]) : image.getHeight();
        } catch (NumberFormatException e) {
            player.sendMessage("§cThat was not a number!");
            return false;
        }
        Bukkit.getPluginManager().callEvent(new PlayerInteractEvent(player, Action.LEFT_CLICK_BLOCK, new ItemStack(Material.WOODEN_AXE), player.getLocation().clone().add(width, 0, height).getBlock(), BlockFace.UP));
        return true;
    }
}
