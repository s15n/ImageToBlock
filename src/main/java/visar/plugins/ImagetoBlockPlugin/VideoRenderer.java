package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class VideoRenderer {

    public static void renderVideo(@NotNull Location location1, @NotNull Location location2 , @NotNull String path, int frames, boolean resize, Player player) {
        int w = Math.abs(location2.getBlockX()-location1.getBlockX());
        int h = Math.abs(location2.getBlockZ()-location1.getBlockZ());
        VideoTask task = new VideoTask(path, location1, w, h, frames, resize, player);
        task.schedule(20L,1L);
    }
}
