package main.color;

import main.fileHandling.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This is the panel that displays the colour wheel.
 * It also does the calculations, so it can grab the
 * hsv value from the mouse coordinates.
 * It does use trig.
 */

public class ColorWheelPanel extends JComponent {
    static final int RADIUS = 200; // radius of the colour wheel.
    int angle = 0; // angle where the mouse is on the colour wheel.
    int circleX = 200; // the x coord of the circle that follows the mouse on the colour wheel.
    int circleY = 200; // the y coord of the circle that follows the mouse on the colour wheel.
    int sliderX = 10; // the x coord of the line that follows the mouse on the slider. Y is not needed.

    Color initialColor = Color.white;

    static final ColorHandler COLOR_HANDLER = new ColorHandler();
    static final FileHandler FILE_HANDLER = new FileHandler();

    public ColorWheelPanel() {
        setPreferredSize(new Dimension(400, 450));
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());
        setFocusable(false);
    }


    // This method updates the panel constantly
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(FILE_HANDLER.getBufferedImageFromStream("Color_Wheel.png"),0,0,this);
        drawColorPreview(g2);
        drawBrightnessSlider(g2);

        drawColorSelector(g2);
        drawBrightnessSelector(g2);
        repaint();
    }


    // method that draws the brightness slider at the bottom of the frame
    private void drawBrightnessSlider(Graphics2D g) {
        GradientPaint paint = new GradientPaint(10, 405, Color.black, 390, 445, initialColor, false);
        g.setPaint(paint);
        g.fillRect(10,405, 380, 40);
        g.setColor(Color.BLACK);
        g.drawRect(9,404,381,41);
    }

    //method to draw the circle that follows the mouse on the colour wheel
    private void drawColorSelector(Graphics2D g) {
        g.setColor(Color.black);
        g.fillOval(circleX -5, circleY -5, 10,10);
    }

    //method to draw the rectangle that follows the mouse when choosing brightness
    private void drawBrightnessSelector(Graphics2D g){
        g.setColor(Color.black);
        g.fillRect(sliderX, 400, 5, 60);
    }


    // draws the colour preview in the top right of the frame
    private void drawColorPreview(Graphics2D g) {
        g.setColor(Color.black);
        g.drawRect(1,1,56,56);
        g.setColor(getColor());
        g.fillRect(2,2,55,55);
    }

    // gets the angle of the mouse on the colour circle
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

            if(distanceFromRadius <= RADIUS){ // this checks if the mouse is actually in the colour circle
                // if it is it will get the hsv value from that coord
                circleX = x;
                circleY = y;

                angle = checkAngle(yCoord, xCoord);
                COLOR_HANDLER.setHue(angle);
                COLOR_HANDLER.setSaturation(distanceFromRadius/RADIUS);
                COLOR_HANDLER.setRgbColor();

                initialColor = COLOR_HANDLER.hsvToRgb(angle,distanceFromRadius/RADIUS, 1.0);
            } else if (x >= 10 && x <= 390 && y >= 405) { // checks if the mouse is in the brightness slider
                sliderX = x;
                COLOR_HANDLER.setValue((double) (x - 10)/380);
                COLOR_HANDLER.setRgbColor();
            }
        }
    }
}
