package visar.plugins.ImagetoBlockPlugin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static BufferedImage image = null;

	private static Main plugin;						
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		Objects.requireNonNull(getCommand("image")).setExecutor(new ImageCommand());
		Objects.requireNonNull(getCommand("setdefaultimage")).setExecutor(new defaultImageCommand());
		
	} 
	@EventHandler
	public void woodAxeRightClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		String path = player.getUniqueId().toString()+".locations";

		if(plugin.getConfig().get(player.getUniqueId().toString()+".image") == null) {
			try {
				image = ImageIO.read(new URL("http://4.bp.blogspot.com/-tjadUZwK6s8/UTpGgK7G1cI/AAAAAAABF2s/L2dNg7-UQ4E/s1600/POKEMON+%252899%2529.png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			//Keine Ahnung ob er das Bild laden kann, müssen wir austesten
			plugin.getConfig().set(path,image);

		}else image = (BufferedImage) this.getConfig().get(player.getUniqueId().toString()+".image");

		if(player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE && e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Location l = Objects.requireNonNull(e.getClickedBlock()).getLocation();
			if(this.getConfig().get(path+".firstloc") == null) {
				this.getConfig().set(path+".firstloc",l);
			}else this.getConfig().set(path+".secondloc",l);
			player.sendMessage(image.getWidth()+" "+image.getHeight());
			//player.sendMessage("The Z Axis is the width of the displayed picture, the X Axis is the height of the picture");
		}
		if(this.getConfig().get(path+".locations"+".firstloc") != null && this.getConfig().get(path+".locations"+".secondloc") != null) {
			Location firstl = (Location) this.getConfig().get(path+".firstloc"),
					 secondl = (Location) this.getConfig().get(path+".secondloc");
			assert firstl != null;
			assert secondl != null;
			ImageRenderer.renderImage(firstl,secondl,image,player);
		}
	}
	

	public static Main getPlugin() {
		return plugin;
	}
}
