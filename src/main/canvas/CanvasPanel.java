package main.canvas;

import main.color.ColorWheelFrame;
import main.color.ColorWheelPanel;
import main.fileHandling.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class handles the panel that rests inside the canvas frame,
 * here is where the user can draw.
 */

public class CanvasPanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 700;
    static final Color BACKGROUND_COLOR = Color.white;

    static final int DELAY = 75;
    Timer timer;

    final ColorWheelPanel COLOR_WHEEL_PANEL = new ColorWheelPanel();
    final ColorWheelFrame COLOR_WHEEL_FRAME = new ColorWheelFrame();
    final FileHandler FILE_HANDLER = new FileHandler();
    Color color;

    Point point = new Point(0,0);
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    Image brushImage = FILE_HANDLER.getBufferedImageFromStream("brush-icon.png");
    Image eraserImage = FILE_HANDLER.getBufferedImageFromStream("eraser-icon.png");

    Cursor brushCursor = toolkit.createCustomCursor(brushImage, point, "brush");
    Cursor eraserCursor = toolkit.createCustomCursor(eraserImage, point, "eraser");

    boolean running = false;
    boolean eraserActive = false;
    int brushSize = 10;

    CanvasPanel() {
        initPanel();
        initListeners();
        start();
    }

    // initializes the panel on startup
    private void initPanel(){
        this.setCursor(brushCursor);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(BACKGROUND_COLOR);
        this.setFocusable(true);
    }

    // initializes the mouse listeners on startup
    private void initListeners(){
        this.addMouseMotionListener(new MyMouseAdapter());
        this.addMouseListener(new MyMouseAdapter());
    }

    // starts the program
    private void start() {
        running = true;
        timer = new Timer(DELAY, this); // there is a quick delay to this timer to allow everything to load properly
        timer.start();
    }


    // this is a super important method as it handles which frame is in focus
    // if the wrong frame is in focus than listeners will stop working correctly
    private void handleFocus() {
        if (!COLOR_WHEEL_FRAME.hasFocus()) {
            this.requestFocus();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            handleFocus();

            if(!eraserActive) {
                color = COLOR_WHEEL_PANEL.getColor();
            }

            brushSize = ToolbarPanel.getBrushSize();

            if (ToolbarPanel.getButtonType() == 1) { // if the button clicked in toolbar is the colour button
                COLOR_WHEEL_FRAME.setVisible(true);
                COLOR_WHEEL_FRAME.requestFocus();
            } else if (ToolbarPanel.getButtonType() == 2) { // if the button clicked in toolbar is the brush button
                this.setCursor(brushCursor);
                eraserActive = false;
            } else if (ToolbarPanel.getButtonType() == 3) { // if the button clicked in toolbar is the eraser button
                eraserActive = true;
                this.setCursor(eraserCursor);
                color = Color.WHITE;
            }
            ToolbarPanel.setButtonType(0);
        }
    }

    public class MyMouseAdapter extends MouseAdapter {
        private int prevX;
        private int prevY;

        long start;
        long elapsedTimeSec;

        // Grabs the mouse coords and
        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            elapsedTimeSec =  (System.nanoTime() - start)/100000;

            if (prevX >= -100 && prevY >= -100) {
                drawLineBetweenPoints(prevX, prevY, x, y);
            }

            prevX = x;
            prevY = y;

            start = System.nanoTime();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            elapsedTimeSec = 0;
            prevX = -1;
            prevY = -1;
        }

        private void drawLineBetweenPoints(int startX, int startY, int endX, int endY) {
            Graphics2D g = (Graphics2D) getGraphics();
            g.setColor(color);

            int distanceThreshold = 10;

            // this turns off the line drawing algorithm if the distance between points exceeds a certain threshold
            // which is 10
            int distanceBetweenPoints = (int) Math.sqrt(Math.pow((startX - endX),2) + Math.pow((startY - endY),2));

            // if the mouse is moving really fast then it will override the distance threshold
            double mouseSpeed = ((double) distanceBetweenPoints /elapsedTimeSec)*10;

            // this unfortunately is a complicated calc algorithm and I can't fully explain it
            // algorithm being used is the Bresenham line-drawing algorithm
            if (distanceBetweenPoints < distanceThreshold || mouseSpeed > .75) {
                int diffX = Math.abs(endX - startX);

                int diffY = Math.abs(endY - startY);
                int directionX, directionY;

                if (startX < endX) {
                    directionX = 1;
                } else {
                    directionX = -1;
                }

                if (startY < endY) {
                    directionY = 1;
                } else {
                    directionY = -1;
                }

                int error = diffX - diffY;
                int tempError;

                while (true) {
                    g.fillOval(startX - brushSize/2, startY - brushSize/2, brushSize, brushSize);

                    if (startX == endX && startY == endY) {
                        break;
                    }

                    tempError = 2 * error;

                    if (tempError > -diffY) {
                        error = error - diffY;
                        startX = startX + directionX;
                    }

                    if (tempError < diffX) {
                        error = error + diffX;
                        startY = startY + directionY;
                    }
                }
            }
        }
    }
}
