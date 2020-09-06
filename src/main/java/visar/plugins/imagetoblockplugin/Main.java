package visar.plugins.imagetoblockplugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
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
import visar.plugins.imagetoblockplugin.RGBBlockColor;

public class Main extends JavaPlugin implements Listener {
    private static BufferedImage image = null,
            resizedImage = null;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        try {
            //image = ImageIO.read(new File("C:\\Users\\visar\\OneDrive\\Dokumente\\pikachu.jpg"));
            image = ImageIO.read(new File("C:\\Users\\Simon\\Desktop\\Visar Server\\test.png"));

        } catch (IOException e) {
            getServer().broadcastMessage("Didn't work");
        }


    }

    @EventHandler
    public void woodAxeRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        String path = player.getUniqueId().toString() + ".locations";

        if (player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE && e.getAction() == Action.LEFT_CLICK_BLOCK) {
            assert e.getClickedBlock()!=null;
            Location l = e.getClickedBlock().getLocation();
            if (this.getConfig().get(path + ".firstLoc") == null) {
                this.getConfig().set(path + ".firstLoc", l);
            } else this.getConfig().set(path + ".secondLoc", l);

            player.sendMessage(image.getHeight() + " " + image.getWidth());
            //player.sendMessage("The Z Axis is the width of the displayed picture, the X Axis is the height of the picture");
        }
        if (this.getConfig().get(path + ".firstLoc") != null && this.getConfig().get(path + ".secondLoc") != null) {
            Location firstL = (Location) this.getConfig().get(path + ".firstLoc"),
                    secondL = (Location) this.getConfig().get(path + ".secondLoc");

            if(firstL==null || secondL==null) {
                player.sendMessage("§cSomething went wrong!");
                return;
            }

            int bigX = Math.max(firstL.getBlockX(), secondL.getBlockX()),
                    smallX = bigX == firstL.getBlockX() ? secondL.getBlockX() : firstL.getBlockX(),
                    bigZ = Math.max(firstL.getBlockZ(), secondL.getBlockZ()),
                    smallZ = bigZ == firstL.getBlockZ() ? secondL.getBlockZ() : firstL.getBlockZ();
            //resizingImage(image,(bigZ-smallZ),(bigX-smallX));

            int row = 0;
            for (int i = smallZ; i <= bigZ; i++) {
                int column = 0;
                for (int j = smallX; j <= bigX; j++) {
                    Location l = new Location(firstL.getWorld(), j, firstL.getBlockY(), i);
                    Block b = l.getBlock();
                    b.setType(RGBBlockColor.getClosestBlockValue(new Color(image.getRGB(column, row))));
                    column++;
                }
                row++;

            }
            this.getConfig().set(path + ".firstLoc", null);
            this.getConfig().set(path + ".secondLoc", null);

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
