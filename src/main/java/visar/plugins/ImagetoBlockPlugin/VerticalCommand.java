package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VerticalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if(label.equals("vertical")) {
            if (args.length > 1) {
                p.sendMessage("§cUse /vertical <on or off>");
                return false;
            }
            String path = p.getUniqueId().toString() + ".vertical";
            Main plugin = Main.getPlugin();
            if (args[0].equals("on")) {
                plugin.getConfig().set(path, true);
            } else if (args[0].equals("off")) {
                plugin.getConfig().set(path, false);
            }
            plugin.saveConfig();
        }else if(label.equals("togglevertwarning")) {
            String path = p.getUniqueId().toString()+".togglevertwarning";
            Main plugin = Main.getPlugin();
            if(!(plugin.getConfig().getBoolean(path))) plugin.getConfig().set(path,true);
            else plugin.getConfig().set(path,false);
        }
        return false;
    }
}
