package visar.plugins.ImagetoBlockPlugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

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
	private static BufferedImage image = null;
							
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		try {
		//image = ImageIO.read(new File("C:\\Users\\visar\\OneDrive\\Dokumente\\pikachu.jpg"));
			//image = ImageIO.read(new File("C:\\Users\\Simon\\Desktop\\Visar Server\\butterfly.png"));
			image = ImageIO.read(new URL("https://i.imgur.com/FUx3ACH.jpg"));
		
		}catch(IOException e) {
			getServer().broadcastMessage("Didn't work");
		}
		
		
	} 
	@EventHandler
	public void woodAxeRightClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		String path = player.getUniqueId().toString()+".locations";

		if(player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE && e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Location l = e.getClickedBlock().getLocation();
			if(this.getConfig().get(path+".firstloc") == null) {
				this.getConfig().set(path+".firstloc",l);
			}else this.getConfig().set(path+".secondloc",l);
			
			
			player.sendMessage(image.getWidth()+" "+image.getHeight());
			//player.sendMessage("The X Axis is the width of the displayed picture, the Z Axis is the height of the picture");
		}
		if(this.getConfig().get(path+".firstloc") != null && this.getConfig().get(path+".secondloc") != null) {
			Location firstl = (Location) this.getConfig().get(path+".firstloc"),
					 secondl = (Location) this.getConfig().get(path+".secondloc");
						
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
			} catch (IOException e1) {
				player.sendMessage("Problems while resizing picture");
			}
					
										
		}
	}
	
	private static BufferedImage resizingImage(BufferedImage srcimage,int new_width, int new_height) throws IOException {
		
		BufferedImage resizedImage = new BufferedImage(new_width, new_height, srcimage.getType());
		Graphics2D g2 = resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcimage, 0, 0, new_width, new_height, null);
		g2.dispose();

		File imageFile = new File("C:/users/Simon/Desktop/Visar Server/test_resized.png");
		imageFile.createNewFile();
		ImageIO.write(resizedImage,"png",imageFile);
		return resizedImage;
	}
	
}
