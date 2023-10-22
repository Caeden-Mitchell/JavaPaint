package main.color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ColorWheelPanel extends JComponent {
    final String FILE_NAME = "Color_Wheel.png";
    static final int RADIUS = 200;
    int angle = 0;

    static ColorHandler colorHandler = new ColorHandler();


    public ColorWheelPanel() {
        setPreferredSize(new Dimension(400, 400));
        addMouseListener(new MyMouseListener());
        setFocusable(false);
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
        g.drawImage(getImage(),0,0,this);
    }

    public int checkAngle(int y, int x) {
        angle = (int) Math.toDegrees(Math.atan2(y, x));
        if (angle < 0) {
            return angle += 360;
        }
        return angle;
    }

    public Color getColor() {
        return colorHandler.getRgbColor();
    }

    public class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int xCoord = x - RADIUS;
            int yCoord = y - RADIUS;

            double distanceFromRadius = Math.sqrt((xCoord)*(xCoord) + (yCoord)*(yCoord));
            
            if(distanceFromRadius <= RADIUS) {
                angle = checkAngle(yCoord, xCoord);
                colorHandler.setHue(angle);
                colorHandler.setSaturation(distanceFromRadius/RADIUS);
                colorHandler.setRgbColor();
            }
        }
    }
}
