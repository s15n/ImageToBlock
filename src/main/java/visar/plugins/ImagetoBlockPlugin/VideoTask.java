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

public class VideoTask implements Runnable {
    private int i = 0;
    private int id;
    private final int max;
    private final Location l1;
    private final Location l2;
    private final Player p;
    private final int width;
    private final int height;
    private final File video;

    private BufferedImage[] frames;
    /*private long time1;
    private long time2;*/

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

    @Override
    public void run() {
        synchronized (video) {
            //time1 = System.currentTimeMillis();
            if (i-9 >= max) {
                Bukkit.getScheduler().cancelTask(id);
            }
            try {
                if(i<max) {
                    frames[i] = AWTFrameGrab.getFrame(video, i);
                }
                if(i>=0) {
                    ImageRenderer.renderImage(l1, l2, frames[i-10], p);
                }
            } catch (IOException | JCodecException e) {
                e.printStackTrace();
            }

        /*time2 = System.currentTimeMillis();
        System.out.println("Frame: "+i+"/"+max+" - "+(time2 - time1)+"ms");*/
        }
        i++;
    }

    public void setId(int id) {
        this.id = id;
    }

    private File getFile(String path) {
        if(path.startsWith("http")) {
            URI uri = URI.create(path);
            return new File(uri);
        }
        return new File(path);
    }
}
