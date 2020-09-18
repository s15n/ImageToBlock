package visar.plugins.ImagetoBlockPlugin;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.imageio.ImageIO;

public class Main extends JavaPlugin implements Listener{

	private static Main plugin;						
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new Canvas(), this);
		Objects.requireNonNull(getCommand("canvas")).setExecutor(new CanvasCommands());
		Objects.requireNonNull(getCommand("delcanvas")).setExecutor(new CanvasCommands());
		Objects.requireNonNull(getCommand("cimage")).setExecutor(new CanvasCommands());
		Objects.requireNonNull(getCommand("cvideo")).setExecutor(new CanvasCommands());
		Objects.requireNonNull(getCommand("clearcanvas")).setExecutor(new CanvasCommands());
		Objects.requireNonNull(getCommand("image")).setExecutor(new ImageCommand());
		Objects.requireNonNull(getCommand("setdefaultimage")).setExecutor(new DefaultImageCommand());
		Objects.requireNonNull(getCommand("video")).setExecutor(new VideoCommand());
		Objects.requireNonNull(getCommand("setdefaultvideo")).setExecutor(new DefaultVideoCommand());
		Objects.requireNonNull(getCommand("vertical")).setExecutor((new VerticalCommand()));
		Objects.requireNonNull(getCommand("togglevertwarning")).setExecutor(new VerticalCommand());
	} 

	public static BufferedImage loadImage(@Nullable String dir, @NotNull Player player) {
		BufferedImage image;
		String filename = dir;
		assert dir != null;
		if(dir.startsWith(player.getUniqueId().toString())) {
			filename = plugin.getConfig().getString(dir);
		}
		try {
			assert filename != null;
			if (!filename.startsWith("http") && !filename.startsWith("ftp")) {
				image = ImageIO.read(new File(filename));
			} else {
				image = ImageIO.read(new URL(filename));
			}
		} catch (IOException e) {
			e.printStackTrace();
			player.sendMessage("§cSomething went wrong while loading the image");
			return null;
		}

		return image;
	}
	public static Main getPlugin() {
		return plugin;
	}
}
