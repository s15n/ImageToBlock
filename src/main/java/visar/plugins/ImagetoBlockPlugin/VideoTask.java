package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.AWTFrameGrab;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class VideoTask {
    private int i_load = 0;
    private int i_render = 0;
    private int id_load;
    private int id_render;
    private final int max;
    private final Location l1;
    private final int width;
    private final int height;
    private final File video;
    private final boolean resize;
    private final Player player;

    private final BufferedImage[] frames;


    public VideoTask(String path, Location l1, int width, int height, int frames, boolean resize, Player player) {
        this.l1 = l1;
        this.width = width;
        this.height = height;
        max = frames;
        video = getFile(path);
        this.resize = resize;
        this.player=player;

        this.frames = new BufferedImage[frames];
    }

    public VideoTask(File file, Location l1, Location l2, int width, int height, int frames, boolean resize, Player player) {
        this.l1 = l1;
        this.width = width;
        this.height = height;
        max = frames;
        video = file;
        this.resize = resize;
        this.player=player;

        this.frames = new BufferedImage[frames];
    }

    public void schedule(long delay, long period) {
        this.id_load = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new VideoImport(), delay, period).getTaskId();
        id_render = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new VideoRender(), delay+200, period).getTaskId();
    }

    private File getFile(String path) {
        if (path.startsWith("http")) {
            URI url = URI.create(path);
            return new File(url);
        }
        return new File(path);
    }

    public final class VideoImport implements Runnable {

        @Override
        public void run() {
            synchronized (frames) {
                try {
                    frames[i_load] = AWTFrameGrab.getFrame(video, i_load);
                    player.sendActionBar(ab((float)i_render/(float)max,(float)i_load/(float)max));
                } catch (IOException | JCodecException e) {
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    Bukkit.getScheduler().cancelTask(id_load);
                    return;
                }
                i_load++;
            }
        }
    }

    public final class VideoRender implements Runnable {

        @Override
        public void run() {
            if (i_render - 1 >= max) {
                Bukkit.getScheduler().cancelTask(id_render);
            }
            try {
                ImageRenderer.renderImageLite(l1, width, height, frames[i_render], resize,player);
            } catch (ArrayIndexOutOfBoundsException e) {
                Bukkit.getScheduler().cancelTask(id_render);
            }
            i_render++;
        }
    }

    private String ab(float p1, float p2) {
        StringBuilder sb = new StringBuilder();
        for (float b1=0; b1<p1; b1+=0.05f) {
            sb.append("§c▮");
        }
        for (float b2=p1; b2<p2; b2+=0.05f) {
            sb.append("§7▮");
        }
        for (float b3=p2; b3<1f; b3+=0.05f) {
            sb.append("§8▮");
        }
        return sb.toString();
    }
}
