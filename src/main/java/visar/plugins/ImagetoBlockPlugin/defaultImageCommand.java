package visar.plugins.ImagetoBlockPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class defaultImageCommand implements CommandExecutor {

   @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if(!sender instanceof Player) return false;
      if(args.length > 1) {
        player.sendMessage("§cUse /setdefaultimage <URL or File Path>");
      }
      Player player = (Player) sender;
      Main plugin = Main.getPlugin();
      String path = player.getUniqueId().toString()+".image";
      if(plugin.getConfig().get(path) == null) {
      
        BufferedImage image = ImageIO.read(new URL("https://lh3.googleusercontent.com/proxy/LSP04K2SjZRcUjKzYOMsiM2_zpjqoJiZlzjy5VGXCzj8KkNA_DCtK5slLpusKxA_BvgPDY6FypUPd7vENbkq3G3CRcYa3OGymtZK6pIz0Yy-27QdkLUlE0A-la1PYBzGlAhCq21_fwJsNVca"));
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
      }catch(Exceptione e) {
        player.sendMessage("§cSomething went wrong! Try again");
        return false;
      }
      
      return false;
    }

}
