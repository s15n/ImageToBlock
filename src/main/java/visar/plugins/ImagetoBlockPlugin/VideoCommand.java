package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.AWTFrameGrab;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.io.NIOUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoCommand implements CommandExecutor {
    private final Main plugin = Main.getPlugin();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (args.length < 3) {
            player.sendMessage("§cUse /video <File Path> [width] [height] or /video [width] [height] ");
            return false;
        }
        String path = player.getUniqueId().toString() + ".video";
        if (isInteger(args[0])) {
            if(plugin.getConfig().getString(path) == null) {
                player.sendMessage("§cYou need to first set a default video, to do that type /setdefaultvideo <File Path>");
                return false;
            }
            renderVideo(plugin.getConfig().getString(path),player);
        } else {
            renderVideo(args[0],player);
        }

        return false;

    }
    private void renderVideo(String filepath,Player player) {
        try {
            File file = new File(filepath);
            BufferedImage exampleImg = AWTFrameGrab.getFrame(file, 1);
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
            DemuxerTrack vt = grab.getVideoTrack();
            VideoRenderer.renderVideo(player.getLocation(), player.getLocation().clone().add(192,0,108),filepath,player,700);
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
            player.sendMessage("§cSomething went wrong while trying to show the video");
        }
    }
    private boolean isInteger(String s) {
        boolean isNumber = true;
        try {
            Integer.parseInt(s);
        }catch(NumberFormatException e) {
            isNumber = false;
        }
        return isNumber; 
    } 
}
