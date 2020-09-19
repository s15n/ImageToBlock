package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Location;
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

public class CanvasCommands implements CommandExecutor {
    private final Main plugin = Main.getPlugin();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        String path = p.getUniqueId().toString()+".canvas";
        Location l = (Location) plugin.getConfig().get(path+".location");
        int width = plugin.getConfig().getInt(path+".width"), height = plugin.getConfig().getInt(path+".height");
        assert l != null;
        boolean vert = plugin.getConfig().getBoolean(p.getUniqueId().toString()+".vertical");
        switch (label) {
            case "canvas":
                if (args.length == 2) {
                    try {
                        Canvas.createCanvas(p.getLocation(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), p);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        p.sendMessage("width and height need to be integers");
                        return false;
                    }
                } else p.sendMessage("§cUse /canvas <width> <height>");
                break;
            case "delcanvas":
                Canvas.deleteCanvas(p.getUniqueId().toString() + ".canvas");
                break;
            case "cimage":
                if (args.length == 0) {
                    String filepath = p.getUniqueId().toString() + ".image";
                    if (!plugin.getConfig().contains(filepath)) {
                        p.sendMessage("§cYou haven't set a default image yet!");
                        p.sendMessage("§cYou need to use §6/setdefaultimage <URL or File Path>");
                        return false;
                    }
                    BufferedImage img = Main.loadImage(filepath, p);
                    assert img != null;
                    ImageRenderer.renderImage(l, l.clone().add(width, vert ? height : 0, vert ? 0 : height), img, p);
                } else if (args.length == 1) {
                    BufferedImage img = Main.loadImage(args[0], p);
                    assert img != null;
                    ImageRenderer.renderImage(l, l.clone().add(width, vert ? height : 0, vert ? 0 : height), img, p);
                } else p.sendMessage("§cYou need to use §6/cimage [URL or File Path]");

                break;
            case "cvideo":
                if (args.length == 0) {
                    String videopath = p.getUniqueId().toString() + ".video";
                    if (!plugin.getConfig().contains(videopath)) {
                        p.sendMessage("§cYou haven't set a default video yet!");
                        p.sendMessage("§cYou need to use §6/setdefaultvideo <File Path>");
                        return false;
                    }
                    String filepath = plugin.getConfig().getString(videopath);
                    assert filepath != null;
                    try {
                        File video = new File(filepath);
                        BufferedImage img = AWTFrameGrab.getFrame(video, 1);
                        boolean resize = true;
                        if (width == img.getWidth() && height == img.getHeight()) resize = false;
                        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(video));
                        DemuxerTrack vt = grab.getVideoTrack();

                        VideoRenderer.renderVideo(l, l.clone().add(width, vert ? height : 0, vert ? 0 : height), filepath, vt.getMeta().getTotalFrames(), resize, p);
                    } catch (IOException | JCodecException e) {
                        e.printStackTrace();
                        p.sendMessage("§cSomething went wrong!Try Again");
                        return false;
                    }
                } else if (args.length == 1) {
                    String filepath = args[0];
                    try {
                        File video = new File(filepath);
                        BufferedImage img = AWTFrameGrab.getFrame(video, 1);
                        boolean resize = true;
                        if (width == img.getWidth() && height == img.getHeight()) resize = false;
                        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(video));
                        DemuxerTrack vt = grab.getVideoTrack();

                        VideoRenderer.renderVideo(l, l.clone().add(width, vert ? height : 0, vert ? 0 : height), filepath, vt.getMeta().getTotalFrames(), resize, p);
                    } catch (IOException | JCodecException e) {
                        e.printStackTrace();
                        p.sendMessage("§cSomething went wrong!Try Again");
                        return false;
                    }
                } else p.sendMessage("§cYou need to use §6/cvideo [File Path]");
                break;
            case "clearcanvas":
                Canvas.clearCanvas(path, p);
                break;
        }

        return false;
    }


}
