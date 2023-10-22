package main.canvas;

import main.color.ColorWheelFrame;
import main.color.ColorWheelPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
public class CanvasPanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 1000;

    static final int DELAY = 75;
    Timer timer;

    ColorWheelPanel colorWheel = new ColorWheelPanel();
    ColorWheelFrame colorWheelFrame = new ColorWheelFrame();
    JButton colorButton;
    Color color;

    boolean running = false;

    CanvasPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseMotionListener(new MyMouseAdapter());
        this.addMouseListener(new MyMouseAdapter());
        initColorButton();
        this.add(colorButton);
        start();
    }


    public void initColorButton() {
        colorButton = new JButton("Colour");
        colorButton.addActionListener(this);
        colorButton.setPreferredSize(new Dimension(100,40));
    }
    
    public void start() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void handleFocus() {
        if (!colorWheelFrame.hasFocus()) {
            this.requestFocus();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            handleFocus();

            color = colorWheel.getColor();

            if (e.getSource() == colorButton) {
                colorWheelFrame.setVisible(true);
                colorWheelFrame.requestFocus();
            }
        }
    }

    public class MyMouseAdapter extends MouseAdapter {
        private int prevX;
        private int prevY;

        boolean isDrawing = false;
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
            isDrawing = true;

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

            int distanceThreshold = 5;


            int distanceBetweenPoints = (int) Math.sqrt(Math.pow((startX - endX),2) + Math.pow((startY - endY),2));

            double mouseSpeed = ((double) distanceBetweenPoints /elapsedTimeSec)*10;

            if (distanceBetweenPoints < distanceThreshold || mouseSpeed > 3.0) {
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


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1 -> color = Color.BLACK;
                case KeyEvent.VK_2 -> color = Color.blue;
                case KeyEvent.VK_3 -> color = Color.green;
                case KeyEvent.VK_4 -> color = Color.red;
                case KeyEvent.VK_5 -> color = Color.orange;
                case KeyEvent.VK_6 -> color = Color.pink;
                case KeyEvent.VK_7 -> color = Color.yellow;
                case KeyEvent.VK_8 -> color = new Color(139, 0, 255);//purple
                case KeyEvent.VK_9 -> color = new Color(255, 255, 255);//white
            }
        }
    }
}
