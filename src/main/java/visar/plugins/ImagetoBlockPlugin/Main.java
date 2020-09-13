package visar.plugins.ImagetoBlockPlugin;


import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.imageio.ImageIO;

public class Main extends JavaPlugin implements Listener{

	private static Main plugin;						
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		Objects.requireNonNull(getCommand("image")).setExecutor(new ImageCommand());
		Objects.requireNonNull(getCommand("setdefaultimage")).setExecutor(new DefaultImageCommand());
		Objects.requireNonNull(getCommand("video")).setExecutor(new VideoCommand());
		Objects.requireNonNull(getCommand("setdefaultvideo")).setExecutor(new DefaultVideoCommand());
		Objects.requireNonNull(getCommand("vertical")).setExecutor((new VerticalCommand()));
		Objects.requireNonNull(getCommand("togglevertwarning")).setExecutor(new VerticalCommand());
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
