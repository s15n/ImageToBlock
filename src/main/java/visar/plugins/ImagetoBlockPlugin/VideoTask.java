package visar.plugins.ImagetoBlockPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.AWTFrameGrab;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoTask implements Runnable {
    private int i = 0;
    private int id;
    private final int max;
    private final Location l1;
    private final Location l2;
    private final Player p;
    private final int width;
    private final int height;

    public VideoTask(Location l1, Location l2, Player p, int width, int height, int frames) {
        this.l1=l1;
        this.l2=l2;
        this.p=p;
        this.width=width;
        this.height=height;
        max=frames;
    }

    @Override
    public void run() {
        if(i>=max) {
            Bukkit.getScheduler().cancelTask(id);
        }
        File video = new File("./test.mp4");
        try {
            BufferedImage frame = AWTFrameGrab.getFrame(video,i);
            ImageRenderer.renderImage(l1,l2,ImageRenderer.resizingImage(frame,width,height),p);
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
        }

        i++;
    }

    public void setId(int id) {
        this.id = id;
    }
}
