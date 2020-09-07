package visar.plugins.ImagetoBlockPlugin;

import java.awt.image.BufferedImage;
import java.io.File;
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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Main extends JavaPlugin implements Listener{
	public static BufferedImage image = null;

	private static Main plugin;						
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		Objects.requireNonNull(getCommand("image")).setExecutor(new ImageCommand());
		Objects.requireNonNull(getCommand("setdefaultimage")).setExecutor(new DefaultImageCommand());
		
	} 
	@EventHandler
	public void woodAxeRightClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		String path = player.getUniqueId().toString()+".locations";

		if(plugin.getConfig().get(player.getUniqueId().toString()+".image") == null) {
			try {
				String UrlORDir = "http://4.bp.blogspot.com/-tjadUZwK6s8/UTpGgK7G1cI/AAAAAAABF2s/L2dNg7-UQ4E/s1600/POKEMON+%252899%2529.png";
				image = ImageIO.read(new URL(UrlORDir));
				this.getConfig().set(path,UrlORDir);
				this.saveConfig();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			//Keine Ahnung ob er das Bild laden kann, müssen wir austesten

		}else image = loadImageFromConfig(this.getConfig().getString(player.getUniqueId().toString()+".image"),player);




		if(player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE && e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Location l = Objects.requireNonNull(e.getClickedBlock()).getLocation();
			if(this.getConfig().get(path+".firstloc") == null) {
				this.getConfig().set(path+".firstloc",l);
			}else this.getConfig().set(path+".secondloc",l);
			player.sendMessage(image.getWidth()+" "+image.getHeight());
			//player.sendMessage("The Z Axis is the width of the displayed picture, the X Axis is the height of the picture");
		}
		if(this.getConfig().get(path+".firstloc") != null && this.getConfig().get(path+".secondloc") != null) {
			Location firstl = (Location) this.getConfig().get(path+".firstloc"),
					 secondl = (Location) this.getConfig().get(path+".secondloc");
			assert firstl != null;
			assert secondl != null;
			ImageRenderer.renderImage(firstl,secondl,image,player);
		}
	}

	@Contract("null,_ -> null")
	public static BufferedImage loadImageFromConfig(@Nullable String dir, @NotNull Player player) {
		if(dir == null) {
			return null;
		}
		String path = player.getUniqueId().toString()+".image";
		BufferedImage image = null;
		try {
			if (!dir.startsWith("http") && !dir.startsWith("ftp") && !dir.startsWith("https")) {
				image = ImageIO.read(new File(Objects.requireNonNull(plugin.getConfig().getString(path))));
			} else {
				image = ImageIO.read(new URL(Objects.requireNonNull(plugin.getConfig().getString(path))));
			}
		}catch(Exception e) {
			player.sendMessage("Something went wrong while loading the image");
		}
		return image;
	}
	public static Main getPlugin() {
		return plugin;
	}
}
