import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utils {

    public static BufferedImage readImage(String filePath) throws IOException {
        File file = new File(filePath);
        return javax.imageio.ImageIO.read(file);
    }

    public static void writeImage(BufferedImage image, String filePath) throws IOException {
        File file = new File(filePath);
        javax.imageio.ImageIO.write(image, "png", file);
    }

    public static BufferedImage enhanceImage(BufferedImage image) {
        // Implement your image enhancement algorithm here
        return image;
    }
}
