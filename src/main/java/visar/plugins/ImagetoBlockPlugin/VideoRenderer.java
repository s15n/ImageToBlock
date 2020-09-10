package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class VideoRenderer {

    public static void renderVideo(@NotNull Location location1, @NotNull Location location2 , @NotNull String path, @NotNull Player player, int frames) {
        int w = Math.abs(location2.getBlockX()-location1.getBlockX());
        int h = Math.abs(location2.getBlockZ()-location1.getBlockZ());
        VideoTask task = new VideoTask(path, location1, location2, player, w, h, frames);
        task.schedule(20L,1L);
    }
}
