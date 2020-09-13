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
    private int i_load = -1;
    private int i_render = -1;
    private int id_load;
    private int id_render;
    private final int max;
    private final Location l1;
    private final int width;
    private final int height;
    private File video;
    private final boolean vert;
    private final boolean resize;
    private final Player player;

    private final BufferedImage[] frames;


    public VideoTask(String path, Location l1, int width, int height, int frames, boolean resize, Player player) {
        this(l1, width, height, frames, resize, player);
        video = getFile(path);
    }

    public VideoTask(File file, Location l1, int width, int height, int frames, boolean resize, Player player) {
        this(l1, width, height, frames, resize, player);
        video = file;
    }

    private VideoTask(Location l, int w, int h, int f, boolean r, Player p) {
        l1 = l;
        width = w;
        height = h;
        max = f;
        resize = r;
        player = p;

        vert = Main.getPlugin().getConfig().getBoolean(p.getUniqueId().toString() + ".vertical");

        this.frames = new BufferedImage[f];
    }

    public void schedule(long delay, long period) {
        this.id_load = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), /*resize?(*/new VideoImport()/*):(new VideoImportOriginal())*/, delay, period).getTaskId();
        id_render = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new VideoRender(), delay + 200, period).getTaskId();
    }

    private File getFile(String path) {
        if (path.startsWith("http")) {
            URI url = URI.create(path);
            return new File(url);
        }
        return new File(path);
    }

    public final class VideoImportOriginal implements Runnable {

        @Override
        public void run() {
            i_load++;
            System.out.println("Loading frame: " + i_load + "/" + max);
            try {
                frames[i_load] = AWTFrameGrab.getFrame(video, i_load);
            } catch (IOException | JCodecException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                Bukkit.getScheduler().cancelTask(id_load);
            }
        }
    }

    public final class VideoImport implements Runnable {

        @Override
        public void run() {
            i_load++;
            System.out.println("Loading frame: " + i_load + "/" + max);
            try {
                frames[i_load] = ImageRenderer.resizingImage(AWTFrameGrab.getFrame(video, i_load), width, height);
            } catch (IOException | JCodecException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                Bukkit.getScheduler().cancelTask(id_load);
            }

        }
    }

    public final class VideoRender implements Runnable {

        @Override
        public void run() {
            i_render++;
            player.sendActionBar(ab(i_render / (float) max, i_load / (float) max));
            if (i_render - 1 >= max) {
                Bukkit.getScheduler().cancelTask(id_render);
            }
            try {
                ImageRenderer.renderImageLite(l1, width, height, frames[i_render], player, vert);
            } catch (ArrayIndexOutOfBoundsException e) {
                Bukkit.getScheduler().cancelTask(id_render);
            }
        }
    }

    private String ab(float p1, float p2) {
        StringBuilder sb = new StringBuilder();
        int p1_i = (int) (p1 * 20);
        int p2_i = (int) (p2 * 20);
        for (int b1 = 0; b1 < p1_i; b1++) {
            sb.append("§c█");
        }
        for (int b2 = p1_i; b2 < p2_i; b2++) {
            sb.append("§7█");
        }
        for (int b3 = p2_i; b3 < 20; b3++) {
            sb.append("§8█");
        }
        return sb.toString();
    }
}
