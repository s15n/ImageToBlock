package visar.plugins.ImagetoBlockPlugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import visar.plugins.ImagetoBlockPlugin.ItemFrameCanvas;
import visar.plugins.ImagetoBlockPlugin.Main;
import visar.plugins.ImagetoBlockPlugin.renderers.ImageRenderer;

import java.awt.image.BufferedImage;

public class IFCanvasCommands implements CommandExecutor {
    private static final Main plugin = Main.getPlugin();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        String path = p.getUniqueId().toString()+".itemframe";
        int width = plugin.getConfig().getInt(path+".width"), height = plugin.getConfig().getInt(path+".height");
        switch (label) {
            case "ifcanvas":
            case "itemframecanvas":
                ItemFrameCanvas.createCanvas(p, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                break;
            case "delifcanvas":
            case "delitemframecanvas":
                ItemFrameCanvas.deleteCanvas(p.getUniqueId().toString() + ".itemframe");
                break;
            case "ifcimage":
            case "itemframecimage":
                if (args.length == 0) {
                    String filepath = p.getUniqueId().toString() + ".image";
                    if (!plugin.getConfig().contains(filepath)) {
                        p.sendMessage("§cYou haven't set a default image yet!");
                        p.sendMessage("§cYou need to use §6/setdefaultimage <URL or File Path>");
                        return false;
                    }
                    BufferedImage img = Main.loadImage(filepath, p);
                    assert img != null;
                    ImageRenderer.renderItemFrameImage(p, width, height, img);
                } else if (args.length == 1) {
                    BufferedImage img = Main.loadImage(args[0], p);
                    assert img != null;
                    ImageRenderer.renderItemFrameImage(p, width, height, img);
                } else p.sendMessage("§cYou need to use §6/itemframecimage [URL or File Path]");

                break;
        }
        return false;
    }
}
