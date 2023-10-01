import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageEnhancer {

    public static BufferedImage enhanceImage(BufferedImage image, float contrast) {
        BufferedImage enhancedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int pixel = image.getRGB(i, j);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                red = (int) (red * contrast);
                green = (int) (green * contrast);
                blue = (int) (blue * contrast);

                red = Math.min(red, 255);
                green = Math.min(green, 255);
                blue = Math.min(blue, 255);

                pixel = (red << 16) | (green << 8) | blue;
                enhancedImage.setRGB(i, j, pixel);
            }
        }

        return enhancedImage;
    }

    public static void main(String[] args) {
        if (args.length == 3) {
            // Single-image processing code
            String inputImagePath = args[0];
            String outputImagePath = args[1];
            float contrast;

            try {
                contrast = Float.parseFloat(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid contrast value. Please provide a valid float.");
                return;
            }

            try {
                BufferedImage image = ImageIO.read(new File(inputImagePath));
                BufferedImage enhancedImage = enhanceImage(image, contrast);
                ImageIO.write(enhancedImage, "png", new File(outputImagePath));
                System.out.println("Enhancement completed and saved to " + outputImagePath);
            } catch (IOException e) {
                System.out.println("Error reading/writing images. Please check file paths.");
                e.printStackTrace();
            }
        } else if (args.length == 3) {
            // Batch processing code
            String inputDirectoryPath = args[0];
            String outputDirectoryPath = args[1];
            float contrast;

            try {
                contrast = Float.parseFloat(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid contrast value. Please provide a valid float.");
                return;
            }

            File inputDirectory = new File(inputDirectoryPath);
            File outputDirectory = new File(outputDirectoryPath);

            if (!inputDirectory.exists() || !inputDirectory.isDirectory()) {
                System.out.println("Input directory does not exist.");
                return;
            }

            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            File[] inputFiles = inputDirectory.listFiles();
            if (inputFiles != null) {
                for (File inputFile : inputFiles) {
                    if (inputFile.isFile() && inputFile.getName().endsWith(".png")) {
                        try {
                            BufferedImage image = ImageIO.read(inputFile);
                            BufferedImage enhancedImage = enhanceImage(image, contrast);
                            String outputFileName = outputDirectoryPath + File.separator + inputFile.getName();
                            ImageIO.write(enhancedImage, "png", new File(outputFileName));
                            System.out.println("Enhancement completed and saved to " + outputFileName);
                        } catch (IOException e) {
                            System.out.println("Error reading/writing images. Please check file paths.");
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            System.out.println("Usage: ImageEnhancer <inputImagePath> <outputImagePath> <contrast>");
            System.out.println("       ImageEnhancer <inputDirectory> <outputDirectory> <contrast>");
        }
    }
}
