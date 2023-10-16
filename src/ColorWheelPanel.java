import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ColorWheelPanel extends JComponent {
    final String FILE_NAME = "Color_Wheel.png";
    public ColorWheelPanel() {
        setPreferredSize(new Dimension(520, 520));
    }

    public BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getFileFromResourceAsStream());
        } catch (IOException ignored) {

        }
        return image;
    }

    private InputStream getFileFromResourceAsStream() {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + FILE_NAME);
        } else {
            return inputStream;
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.BLACK);
        g2.drawImage(getImage(),10,10,this);
    }
}