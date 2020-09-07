package visar.plugins.ImagetoBlockPlugin;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class defaultImageCommand implements CommandExecutor {

   @Override
    public boolean onCommand( CommandSender sender,  Command command,  String label, String[] args) {
	       	
	   if(!(sender instanceof Player)) return false;
	   Player player = (Player) sender;
	   if(args.length > 1) {
		   player.sendMessage("§cUse /setdefaultimage <URL or File Path>");
	   }    
	   Main plugin = Main.getPlugin();
	   String path = player.getUniqueId().toString()+".image";
	   if(plugin.getConfig().get(path) == null) {
      
		   BufferedImage image = null;
		   try {
			   image = ImageIO.read(new URL("http://4.bp.blogspot.com/-tjadUZwK6s8/UTpGgK7G1cI/AAAAAAABF2s/L2dNg7-UQ4E/s1600/POKEMON+%252899%2529.png"));
		   } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		   //Keine Ahnung ob er das Bild laden kann, müssen wir austesten
		   plugin.getConfig().set(path,image);
		   
	   }
	   try {
        
		  if (args[0].startsWith("http") || args[0].startsWith("ftp")) {
                BufferedImage image = ImageIO.read(new URL(args[0]));
                plugin.getConfig().set(path,image);
		  } else {
			   	BufferedImage image = ImageIO.read(new File(args[0]));
                plugin.getConfig().set(path,image);
          }
		  player.sendMessage("§cImage successfully set"); 
	  }catch(Exception e) {
		  player.sendMessage("§cSomething went wrong! Try again");
		  return false;
      }
      
      return false;
    }

}
