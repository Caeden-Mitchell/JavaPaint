package main.fileHandling;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class FileHandler {
    public InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    public BufferedImage getBufferedImageFromStream(String fileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getFileFromResourceAsStream(fileName));
        } catch (IOException ignored) {

        }
        return image;
    }
}
