package visar.plugins.ImagetoBlockPlugin.renderers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import visar.plugins.ImagetoBlockPlugin.Main;
import visar.plugins.ImagetoBlockPlugin.VideoTask;


public class VideoRenderer {

    public static void renderVideo(@NotNull Location location1, @NotNull Location location2 , @NotNull String filepath, int frames, boolean resize, Player player) {
        String path = player.getUniqueId().toString()+".vertical";
        Main plugin = Main.getPlugin();
        int w,h;
        if(!plugin.getConfig().contains(path)) plugin.getConfig().set(path,false);
        if(plugin.getConfig().getBoolean(path)) {
            w = Math.abs(location2.getBlockX() - location1.getBlockX());
            h = Math.abs(location2.getBlockY() - location1.getBlockY());
        }else {
            w = Math.abs(location2.getBlockX() - location1.getBlockX());
            h = Math.abs(location2.getBlockZ() - location1.getBlockZ());
        }
        VideoTask task = new VideoTask(filepath, location1, w, h, frames, resize, player);
        task.schedule(20L,1L);
    }
}
