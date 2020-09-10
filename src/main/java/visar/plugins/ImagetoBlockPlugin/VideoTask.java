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
    private final Location l2;
    private final Player p;
    private final int width;
    private final int height;
    private final File video;

    private BufferedImage[] frames;


    public VideoTask(String path, Location l1, Location l2, Player p, int width, int height, int frames) {
        this.l1=l1;
        this.l2=l2;
        this.p=p;
        this.width=width;
        this.height=height;
        max=frames;
        video = getFile(path);
        
        this.frames = new BufferedImage[frames];
    }

    public VideoTask(File file, Location l1, Location l2, Player p, int width, int height, int frames) {
        this.l1=l1;
        this.l2=l2;
        this.p=p;
        this.width=width;
        this.height=height;
        max=frames;
        video = file;

        this.frames = new BufferedImage[frames];
    }

    public void schedule(long delay, long period) {
        this.id_load = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new VideoImport(), delay, period).getTaskId();
        this.id_render = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new VideoRender(), delay+100, period).getTaskId();
    }

    private File getFile(String path) {
        if(path.startsWith("http")) {
            URI uri = URI.create(path);
            return new File(uri);
        }
        return new File(path);
    }

    public final class VideoImport implements Runnable {

        @Override
        public void run() {
            synchronized (video) {
                if (i_load-1 >= max) {
                    Bukkit.getScheduler().cancelTask(id_load);
                }
                try {
                    frames[i_load] = AWTFrameGrab.getFrame(video, i_load);
                } catch (IOException | JCodecException e) {
                    e.printStackTrace();
                }
                i_load++;
            }
        }
    }

    public final class VideoRender implements Runnable {

        @Override
        public void run() {
            synchronized (video) {
                if(i_render-1 >= max) {
                    Bukkit.getScheduler().cancelTask(id_render);
                }
                ImageRenderer.renderImage(l1,l2,frames[i_render],p);
                i_render++;
            }
        }
    }
}
