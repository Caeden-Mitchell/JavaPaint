package main.canvas;

import main.color.ColorWheelFrame;
import main.color.ColorWheelPanel;
import main.fileHandling.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasPanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 1000;
    static final Color BACKGROUND_COLOR = Color.white;

    static final int DELAY = 75;
    Timer timer;

    final ColorWheelPanel COLOR_WHEEL_PANEL = new ColorWheelPanel();
    final ColorWheelFrame COLOR_WHEEL_FRAME = new ColorWheelFrame();
    final FileHandler FILE_HANDLER = new FileHandler();

    JButton colorButton;
    JButton eraserButton;
    JButton brushButton;
    Color color;

    Point point = new Point(0,0);
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    Image brushImage = FILE_HANDLER.getBufferedImageFromStream("brush-icon.png");
    Image eraserImage = FILE_HANDLER.getBufferedImageFromStream("eraser-icon.png");

    Cursor brushCursor = toolkit.createCustomCursor(brushImage, point, "brush");
    Cursor eraserCursor = toolkit.createCustomCursor(eraserImage, point, "eraser");

    boolean running = false;
    boolean eraserActive = false;

    CanvasPanel() {
        initPanel();
        initListeners();
        initColorButton();
        initBrushButton();
        initEraserButton();

        start();
    }

    private void initPanel(){
        this.setCursor(brushCursor);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(BACKGROUND_COLOR);
        this.setFocusable(true);
    }

    private void initListeners(){
        this.addMouseMotionListener(new MyMouseAdapter());
        this.addMouseListener(new MyMouseAdapter());
    }

    private void initBrushButton() {
        brushButton = new JButton("Brush");
        brushButton.addActionListener(this);
        brushButton.setPreferredSize(new Dimension(100,40));
        brushButton.setOpaque(true);
        this.add(brushButton);
    }

    private void initEraserButton() {
        eraserButton = new JButton("eraser");
        eraserButton.addActionListener(this);
        eraserButton.setPreferredSize(new Dimension(100,40));
        eraserButton.setOpaque(true);
        this.add(eraserButton);
    }

    private void initColorButton() {
        colorButton = new JButton("Colour");
        colorButton.addActionListener(this);
        colorButton.setPreferredSize(new Dimension(100,40));
        colorButton.setOpaque(true);
        this.add(colorButton);
    }
    
    private void start() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

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

            if (e.getSource() == colorButton) {
                COLOR_WHEEL_FRAME.setVisible(true);
                COLOR_WHEEL_FRAME.requestFocus();
            } else if (e.getSource() == eraserButton) {
                eraserActive = true;
                this.setCursor(eraserCursor);
                color = Color.WHITE;
            } else if (e.getSource() == brushButton) {
                this.setCursor(brushCursor);
                eraserActive = false;
            }
        }
    }

    public class MyMouseAdapter extends MouseAdapter {
        private int prevX;
        private int prevY;

        long start;
        long elapsedTimeSec;

        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            elapsedTimeSec =  (System.nanoTime() - start) /100000;

            if (prevX >= 0 && prevY >= 0) {
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
            Graphics g = getGraphics();
            g.setColor(color);

            int distanceThreshold = 10;

            int distanceBetweenPoints = (int) Math.sqrt(Math.pow((startX - endX),2) + Math.pow((startY - endY),2));

            double mouseSpeed = ((double) distanceBetweenPoints /elapsedTimeSec)*10;

            if (distanceBetweenPoints < distanceThreshold || mouseSpeed > .75) {
                int dx = Math.abs(endX - startX);

                int dy = Math.abs(endY - startY);
                int sx, sy;

                if (startX < endX) {
                    sx = 1;
                } else {
                    sx = -1;
                }

                if (startY < endY) {
                    sy = 1;
                } else {
                    sy = -1;
                }

                int err = dx - dy;
                int e2;

                while (true) {
                    g.fillOval(startX, startY, 10, 10);

                    if (startX == endX && startY == endY) {
                        break;
                    }

                    e2 = 2 * err;

                    if (e2 > -dy) {
                        err = err - dy;
                        startX = startX + sx;
                    }

                    if (e2 < dx) {
                        err = err + dx;
                        startY = startY + sy;
                    }
                }
            }
        }
    }
}
