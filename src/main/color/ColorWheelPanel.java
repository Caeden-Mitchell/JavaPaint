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

    int sliderX;
    int sliderY;
    int circleX;
    int circleY;

    static ColorHandler colorHandler = new ColorHandler();

    public ColorWheelPanel() {
        setPreferredSize(new Dimension(400, 450));
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());
        setFocusable(false);
    }

    private BufferedImage getImage() {
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
        drawColorSelector(g);
        drawBrightnessSelector(g);
        drawColorPreview(g);
        drawBrightnessSlider(g);
        repaint();
    }

    private void drawBrightnessSlider(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        GradientPaint paint = new GradientPaint(10, 405, Color.black, 390, 445, getColor(), false);
        g2.setPaint(paint);
        g2.fillRect(10,405, 380, 40);
    }

    //method to draw the circle that follows the mouse on the colour wheel
    private void drawColorSelector(Graphics g) {
        g.fillOval(circleX -5, circleY -5, 10,10);
    }

    //method to draw the rectangle that follows the mouse when choosing brightness
    private void drawBrightnessSelector(Graphics g){
        g.setColor(Color.black);
        g.drawLine(sliderX, 400, sliderX, 455);
    }

    private void drawColorPreview(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0,0,56,56);
        g.setColor(getColor());
        g.fillRect(1,1,55,55);

    }

    private int checkAngle(int y, int x) {
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
        public void mouseDragged(MouseEvent e) {
             int x = e.getX();
             int y = e.getY();

            int xCoord = x - RADIUS;
            int yCoord = y - RADIUS;

            double distanceFromRadius = Math.sqrt((xCoord)*(xCoord) + (yCoord)*(yCoord));
            
            if(distanceFromRadius <= RADIUS) {
                circleX = x;
                circleY = y;

                angle = checkAngle(yCoord, xCoord);
                colorHandler.setHue(angle);
                colorHandler.setSaturation(distanceFromRadius/RADIUS);
                colorHandler.setRgbColor();
            } else if (x >= 10 && x <= 390 && y >= 405) {
                sliderX = x;
                sliderY = y;
                colorHandler.setValue((double) (x - 10)/380);
            }
        }
    }
}
