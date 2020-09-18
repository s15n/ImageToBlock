package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class CanvasCommands implements CommandExecutor {
    private final Main plugin = Main.getPlugin();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        String path = p.getUniqueId().toString()+".canvas";
        if(label.equals("canvas") && args.length == 2) {
            try {
                Canvas.createCanvas(p.getLocation(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), p);
            }catch(NumberFormatException e) {
                e.printStackTrace();
                p.sendMessage("width and height need to be integers");
                return false;
            }
        }
        else if(label.equals("delcanvas")) {
            Canvas.deleteCanvas(p.getUniqueId().toString()+".canvas");
        }
        else if(label.equals("cimage")) {
            if(args.length == 0) {
                Location l = (Location) plugin.getConfig().get(path+".location");
                String filepath = p.getUniqueId().toString()+".image";
                if(!plugin.getConfig().contains(filepath)) {
                    p.sendMessage("§cYou haven't set a default image yet!");
                    p.sendMessage("§cYou need to use §6/setdefaultimage <URL or File Path>");
                    return false;
                }
                boolean vert = plugin.getConfig().getBoolean(p.getUniqueId().toString()+".vertical");
                BufferedImage img = Main.loadImage(filepath,p);
                assert l != null;
                assert img != null;
                ImageRenderer.renderImage(l,l.clone().add(img.getWidth(),vert ? img.getHeight() : 0,vert ? 0 : img.getHeight()),img,p);
            }else if(args.length == 1) {
                Location l = (Location) plugin.getConfig().get(path+".location");
                boolean vert = plugin.getConfig().getBoolean(p.getUniqueId().toString()+".vertical");
                BufferedImage img = Main.loadImage(args[0],p);
                assert l != null;
                assert img != null;
                ImageRenderer.renderImage(l,l.clone().add(img.getWidth(),vert ? img.getHeight() : 0,vert ? 0 : img.getHeight()),img,p);
            }
            else if(args.length == 2) {
                Location l = (Location) plugin.getConfig().get(path+".location");
                String filepath = p.getUniqueId().toString()+".image";
                if(!plugin.getConfig().contains(filepath)) {
                    p.sendMessage("§cYou haven't set a default image yet!");
                    p.sendMessage("§cYou need to use §6/setdefaultimage <URL or File Path>");
                    return false;
                }
                boolean vert = plugin.getConfig().getBoolean(p.getUniqueId().toString()+".vertical");
                BufferedImage img = Main.loadImage(filepath,p);
                assert l != null;
                assert img != null;
                try {
                    ImageRenderer.renderImage(l, l.clone().add(Integer.parseInt(args[0]), vert ? Integer.parseInt(args[1]) : 0, vert ? 0 : Integer.parseInt(args[1])), img, p);
                }catch(NumberFormatException e) {
                    p.sendMessage("§cWidth and height need to be integers");
                    return false;
                }
            }else if(args.length == 3) {
                Location l = (Location) plugin.getConfig().get(path+".location");
                boolean vert = plugin.getConfig().getBoolean(p.getUniqueId().toString()+".vertical");
                BufferedImage img = Main.loadImage(args[0],p);
                assert l != null;
                assert img != null;
                try {
                    ImageRenderer.renderImage(l, l.clone().add(Integer.parseInt(args[1]), vert ? Integer.parseInt(args[2]) : 0, vert ? 0 : Integer.parseInt(args[2])), img, p);
                }catch(NumberFormatException e) {
                    p.sendMessage("§cWidth and height need to be integers");
                    return false;
                }
            }else {
                p.sendMessage("§cUse /cimage [URL or File Path] [width] [height]");
                return false;
            }
        }

        return false;
    }


}
