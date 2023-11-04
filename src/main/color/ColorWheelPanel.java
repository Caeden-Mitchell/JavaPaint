package main.color;

import main.fileHandling.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorWheelPanel extends JComponent {
    final String FILE_NAME = "Color_Wheel.png";
    static final int RADIUS = 200;
    int angle = 0;
    int circleX = 200;
    int circleY = 200;
    int sliderX = 10;

    Color initialColor = Color.white;

    static final ColorHandler COLOR_HANDLER = new ColorHandler();
    static final FileHandler FILE_HANDLER = new FileHandler();

    public ColorWheelPanel() {
        setPreferredSize(new Dimension(400, 450));
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());
        setFocusable(false);
    }

    public void paint(Graphics g) {
        g.drawImage(FILE_HANDLER.getBufferedImageFromStream("Color_Wheel.png"),0,0,this);
        drawColorPreview(g);
        drawBrightnessSlider(g);

        drawColorSelector(g);
        drawBrightnessSelector(g);
        repaint();
    }


    private void drawBrightnessSlider(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        GradientPaint paint = new GradientPaint(10, 405, Color.black, 390, 445, initialColor, false);
        g2.setPaint(paint);
        g2.fillRect(10,405, 380, 40);
        g2.setColor(Color.BLACK);
        g2.drawRect(9,404,381,41);
    }

    //method to draw the circle that follows the mouse on the colour wheel
    private void drawColorSelector(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(circleX -5, circleY -5, 10,10);
    }

    //method to draw the rectangle that follows the mouse when choosing brightness
    private void drawBrightnessSelector(Graphics g){
        g.setColor(Color.black);
        g.fillRect(sliderX, 400, 5, 60);
    }


    private void drawColorPreview(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(1,1,56,56);
        g.setColor(getColor());
        g.fillRect(2,2,55,55);
    }

    private int checkAngle(int y, int x) {
        angle = (int) Math.toDegrees(Math.atan2(y, x));
        if (angle < 0) {
            return angle += 360;
        }
        return angle;
    }

    public Color getColor() {
        return COLOR_HANDLER.getRgbColor();
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
                COLOR_HANDLER.setHue(angle);
                COLOR_HANDLER.setSaturation(distanceFromRadius/RADIUS);
                COLOR_HANDLER.setRgbColor();

                initialColor = COLOR_HANDLER.hsvToRgb(angle,distanceFromRadius/RADIUS, 1.0);
            } else if (x >= 10 && x <= 390 && y >= 405) {
                sliderX = x;
                COLOR_HANDLER.setValue((double) (x - 10)/380);
                COLOR_HANDLER.setRgbColor();
            }
        }
    }
}
