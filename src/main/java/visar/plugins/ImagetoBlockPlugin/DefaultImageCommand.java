package visar.plugins.ImagetoBlockPlugin;
import java.awt.image.BufferedImage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class DefaultImageCommand implements CommandExecutor {

   @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull  Command command,@NotNull  String label,@NotNull String[] args) {


	   if(!(sender instanceof Player)) return false;
	   Player player = (Player) sender;
	   if(args.length < 1) {
		   player.sendMessage("§cUse /setdefaultimage <URL or File Path>");
		   return false;
	   }    
	   Main plugin = Main.getPlugin();
	   String path = player.getUniqueId().toString()+".image";
	   if(plugin.getConfig().get(path) == null) {

		   String UrlORDir = "http://4.bp.blogspot.com/-tjadUZwK6s8/UTpGgK7G1cI/AAAAAAABF2s/L2dNg7-UQ4E/s1600/POKEMON+%252899%2529.png";
		   plugin.getConfig().set(path,UrlORDir);
		   plugin.saveConfig();
		   
	   }
	   try {
		   String UrlORDir = args[0];
		   if (!args[0].startsWith("http") && !args[0].startsWith("ftp") && !args[0].startsWith("https")) {
		   		plugin.getConfig().set(path,UrlORDir);
		   } else {
				 plugin.getConfig().set(path,UrlORDir);
		   }
		   plugin.saveConfig();
		   player.sendMessage("§cImage successfully set");
	  }catch(Exception e) {
		  player.sendMessage("§cSomething went wrong! Try again");
		  return false;
      }

      return false;
    }

}
