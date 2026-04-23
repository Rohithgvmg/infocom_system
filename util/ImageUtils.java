package util;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageUtils {

    // Read grayscale image and return pixel array
    public static int[] readImage(String path, int[] dimensions) throws Exception {
        BufferedImage img = ImageIO.read(new File(path));

        int width = img.getWidth();
        int height = img.getHeight();

        dimensions[0] = width;
        dimensions[1] = height;

        int[] pixels = new int[width * height];

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);

                int gray = (rgb >> 16) & 0xFF; // since grayscale
                pixels[index++] = gray;
            }
        }

        return pixels;
    }

    // Convert pixel array to string
    public static String pixelsToString(int[] pixels) {
        StringBuilder sb = new StringBuilder();
        for (int val : pixels) {
            sb.append((char) val);
        }
        return sb.toString();
    }

    // Convert string back to pixels
    public static int[] stringToPixels(String str) {
        int[] pixels = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            pixels[i] = (int) str.charAt(i);
        }
        return pixels;
    }

    // Write pixels back to image
    public static void writeImage(String path, int[] pixels, int width, int height) throws Exception {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int val = pixels[index++];
                int rgb = (val << 16) | (val << 8) | val;
                img.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(img, "png", new File(path));
    }
}