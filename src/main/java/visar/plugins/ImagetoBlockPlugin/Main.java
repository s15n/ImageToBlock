package visar.plugins.ImagetoBlockPlugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
			//player.sendMessage("The X Axis is the width of the displayed picture, the Z Axis is the height of the picture");
		}
		if(this.getConfig().get(path+".locations"+".firstloc") != null && this.getConfig().get(path+".locations"+".secondloc") != null) {
			Location firstl = (Location) this.getConfig().get(path+".firstloc"),
					 secondl = (Location) this.getConfig().get(path+".secondloc");

			assert firstl != null;
			assert secondl != null;
			int bigX = Math.max(firstl.getBlockX(), secondl.getBlockX()),
				smallX = bigX == firstl.getBlockX() ? secondl.getBlockX() : firstl.getBlockX(),
				bigZ = Math.max(firstl.getBlockZ(), secondl.getBlockZ()),
				smallZ = bigZ == firstl.getBlockZ() ? secondl.getBlockZ() : firstl.getBlockZ();
			try {
				BufferedImage resizedImage = resizingImage(image,(bigX-smallX),(bigZ-smallZ));
				System.out.println("x - " + (bigX - smallX));
				System.out.println("z - " + (bigZ - smallZ));
				int row = 0;
				for(int i = smallZ; i<bigZ; i++) {
					int column = 0;
					for(int j=smallX; j<bigX; j++) {
						Location l = new  Location(firstl.getWorld(), j, firstl.getBlockY(), i);
						Block b = l.getBlock();
						b.setType(RGBBlockColor.getClosestBlockValue(new Color(resizedImage.getRGB(column, row))));
						column++;
					}
					row++;
				}
				this.getConfig().set(path+".firstloc",null);
				this.getConfig().set(path+".secondloc",null);
			} catch (Exception e1) {
				player.sendMessage("Problems while resizing picture");
			}
					
										
		}
	}
	
	private static BufferedImage resizingImage(BufferedImage srcimage,int new_width, int new_height) {
		
		BufferedImage resizedImage = new BufferedImage(new_width, new_height, srcimage.getType());
		Graphics2D g2 = resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcimage, 0, 0, new_width, new_height, null);
		g2.dispose();

		/*File imageFile = new File("C:/users/Simon/Desktop/Visar Server/test_resized.png");
		ImageIO.write(resizedImage,"png",imageFile);*/
		return resizedImage;
	}
	public static Main getPlugin() {
		return plugin;
	}
}
