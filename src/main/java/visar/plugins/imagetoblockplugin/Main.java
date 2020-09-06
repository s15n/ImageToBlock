package visar.plugins.imagetoblockplugin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	private static Location firstL,secondL;
	private static BufferedImage image = null, resizedImage = null;
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	
		try {
		image = ImageIO.read(new File("C:\\Users\\Simon\\Documents\\Unbenannt.png"));

		}catch(IOException e) {
			getServer().broadcastMessage("Didn't work");
		}
		
	} 
	@EventHandler
	public void woodAxeRightClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE && e.getAction() == Action.LEFT_CLICK_BLOCK) {
			assert e.getClickedBlock() != null;
			if(firstL == null) firstL = e.getClickedBlock().getLocation();
			else secondL = e.getClickedBlock().getLocation();
			player.sendMessage(image.getHeight()+""+image.getWidth());
			player.sendMessage("The Z Axis is the width of the displayed picture, the X Axis is the height of the picture");
		}
		if(firstL != null && secondL != null) {
			int bigX = Math.max(firstL.getBlockX(), secondL.getBlockX()),
				smallX = bigX == firstL.getBlockX() ? secondL.getBlockX() : firstL.getBlockX(),
				bigZ = Math.max(firstL.getBlockZ(), secondL.getBlockZ()),
				smallZ =bigZ == firstL.getBlockZ() ? secondL.getBlockZ() : firstL.getBlockZ();
				resizingImage(image,(bigZ-smallZ),(bigX-smallX));
					
				int row = 0;
				for(int i = smallZ; i<=bigZ; i++) {
					int column = 0;
					for(int j= smallX; j<=bigX; j++) {
						Location l = new  Location(firstL.getWorld(), j, firstL.getBlockY(), i);
						Block b = l.getBlock();
						b.setType(RGBBlockColor.getClosestBlockValue(new Color(resizedImage.getRGB(column, row))));
						column++;
					}
					row++;
					
				
			}
			firstL = null;
			secondL = null;
		}
	}

	private static BufferedImage resizingImage(BufferedImage srcimage,int new_width, int new_height) {

		resizedImage = new BufferedImage(new_width, new_height, Transparency.TRANSLUCENT);
		Graphics2D g2 = resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(resizedImage, 0, 0, new_width, new_height, null);
		g2.dispose();

		return resizedImage;
	}
	
}
