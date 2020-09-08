package visar.plugins.ImagetoBlockPlugin;

import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.AWTFrameGrab;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoImport {

    public static void grabFrame(@NotNull File video, String pathnameFolder, double frame) throws IOException, JCodecException {
        for(double i=0; i<=frame; i++) {
            File output = new File(pathnameFolder+"\\picture"+i);
            if(output.createNewFile()) {
                System.out.println("Working");
            }else {
                System.out.println("File overwritten");
            }
            BufferedImage img = AWTFrameGrab.getFrame(video, i);
            ImageIO.write(img,".png",output);
        }
    }
}