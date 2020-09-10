package visar.plugins.ImagetoBlockPlugin;

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

        if (args.length > 3) {
            player.sendMessage("§cUse /video <File Path> [width] [height] or /video [width] [height] ");
            return false;
        }
        String path = player.getUniqueId().toString() + ".video";
        String filepath = plugin.getConfig().getString(path);
        assert filepath != null;
        if(args.length == 0) {
            try {
                File video = new File(filepath);
                BufferedImage image = AWTFrameGrab.getFrame(video, 1);
                renderVideo(filepath,player,image.getWidth(),image.getHeight());
            }catch(IOException | JCodecException ex) {
                ex.printStackTrace();
                player.sendMessage("§cYou need to first set a default image");
                return false;
            }

        }
        if (isInteger(args[0]) && args.length == 2) {
            if(plugin.getConfig().getString(path) == null) {
                player.sendMessage("§cYou need to first set a default video, to do that type /setdefaultvideo <File Path>");
                return false;
            }
            try {
                renderVideo(filepath,player,Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            }catch(NumberFormatException e) {
                e.printStackTrace();
                player.sendMessage("§cWidth and height need to be integers");
                return false;
            }
        } else if(!isInteger(args[0]) && args.length == 1){

            try {
                File video = new File(args[0]);
                BufferedImage image = AWTFrameGrab.getFrame(video,1);
                renderVideo(args[0],player,image.getWidth(),image.getHeight());
            } catch (IOException | JCodecException e) {
                e.printStackTrace();
                player.sendMessage("§cFile does not exist!");
                return false;
            }

        }else if(!isInteger(args[0]) && args.length == 3) {
            try {
                renderVideo(args[0], player, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            }catch(NumberFormatException e) {
                player.sendMessage("§c Width and height need to be integers");
                return false;
            }
        }

        return false;

    }
    private void renderVideo(String filepath,Player player,int width, int height) {
        try {
            File file = new File(filepath);
            BufferedImage exampleImg = AWTFrameGrab.getFrame(file, 1);
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
            DemuxerTrack vt = grab.getVideoTrack();
            VideoTask videoTask = new VideoTask(filepath, player.getLocation(), player.getLocation().clone().add(width, 0, height), player, exampleImg.getWidth(), exampleImg.getHeight(), vt.getMeta().getTotalFrames());
            videoTask.schedule(20L,1L);
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
