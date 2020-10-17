package visar.plugins.ImagetoBlockPlugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import visar.plugins.ImagetoBlockPlugin.Main;

public class DefaultVideoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(args.length != 1) {
            player.sendMessage("§cUse /setdefaultimage <File Path>");
            return false;
        }
        Main plugin = Main.getPlugin();
        String path = player.getUniqueId().toString()+".video";
        plugin.getConfig().set(path,args[0]);
        plugin.saveConfig();
        return false;
    }
}
