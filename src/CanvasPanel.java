import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.Arrays;

public class CanvasPanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 1000;
    static final int CANVAS_WIDTH = 1000;
    static final int CANVAS_HEIGHT = 950;
    static final int UNIT_SIZE = 10;
    static final int GAME_UNITS = (CANVAS_WIDTH*CANVAS_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    int xCoord;
    int yCoord;
    Color[] color = new Color[GAME_UNITS];
    Color[] wallColors = {Color.BLACK, Color.blue, Color.green, Color.red,Color.orange, Color.pink, Color.yellow, new Color(139, 0, 255), new Color(255, 255, 255)};

    int lineLength = 1;

    /*
    R = right
    L = left
    U = up
    D = down
     */
    char direction = 'N';
    boolean brushLifted = false;
    boolean running = false;
    Timer timer;
    int startAngle = 270;
    static int ARC_ANGLE = 180;
    int xTemp = -5;
    int yTemp = 50;

    ColorWheelPanel colorWheel = new ColorWheelPanel();
    ColorWheelFrame colorWheelFrame = new ColorWheelFrame();
    JButton colorButton;



    CanvasPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseMotionListener(new MyMouseAdapter());
        initColorButton();
        this.add(colorButton);
        Arrays.fill(color, Color.BLACK);
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
       // repaint();
    }


    public void drawColorWall(Graphics g) {
        g.setColor(new Color(104, 104, 104));
        g.fillRect(0,0,SCREEN_WIDTH, (SCREEN_HEIGHT-CANVAS_HEIGHT));
        for (int i = 0; i < wallColors.length; i++){
            g.setColor(wallColors[i]);
            g.fillRect(i*UNIT_SIZE*10 + 80, 15, 20, 20);
        }

    }

    public void move() {
        if (direction != 'N' && !brushLifted) {
            lineLength++;
            for(int i = lineLength; i > 0; i--) {
                x[i] = x[i-1];
                y[i] = y[i-1];

               color[i] = color[i-1];
            }
        }
        switch (direction) {
            case 'U' -> { y[0] = y[0] - UNIT_SIZE;
                startAngle = 360;}
            case 'D' -> { y[0] = y[0] + UNIT_SIZE;
                startAngle = 180;}
            case 'L' -> { x[0] = x[0] - UNIT_SIZE;
                startAngle = 90;}
            case 'R' -> { x[0] = x[0] + UNIT_SIZE;
                startAngle = 270;}
        }
    }

    public void checkCollisions() {
        //if the head of the line cross any part of the line it will
        // change the color to that of the head
        for(int i = lineLength; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                color[i] = color[0];
            }
        }
        //checks if head collides with left border
        if(x[0] < (SCREEN_WIDTH-CANVAS_WIDTH)) {
            x[0] = (SCREEN_WIDTH-CANVAS_WIDTH);
        }
        //checks if head collides with right border
        if(x[0] > SCREEN_WIDTH) {
            x[0] = SCREEN_WIDTH-2;
        }
        //checks if head collides with top border
        if(y[0] < (SCREEN_HEIGHT-CANVAS_HEIGHT)) {
            y[0] = (SCREEN_HEIGHT-CANVAS_HEIGHT);
        }
        //checks if head collides with bottom border
        if(y[0] > SCREEN_HEIGHT) {
            y[0] =SCREEN_HEIGHT-2;
        }
    }

    public void handleFocus() {
        if (!colorWheelFrame.hasFocus()) {
            this.requestFocus();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleFocus();

        color[0] = colorWheel.getColor();

        if (e.getSource() == colorButton) {
            colorWheelFrame.setVisible(true);
            colorWheelFrame.requestFocus();
        }
        if(running) {
            move();
            checkCollisions();
        }
        //repaint();
    }

    public class MyMouseAdapter extends MouseAdapter {
        private int prevX;
        private int prevY;

        boolean isDrawing = false;

        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            System.out.println(isDrawing);

            if (prevX >= 0 && prevY >= 0) {
                drawLineBetweenPoints(prevX, prevY, x, y);
            }
            isDrawing = true;
            //prevX = x;
            //prevY = y;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            prevX = e.getX();
            prevY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            prevX = -1;
            prevY = -1;
            isDrawing = false;
        }

        private void drawLineBetweenPoints(int startX, int startY, int endX, int endY) {
            Graphics g = getGraphics();
            g.setColor(color[0]);


             int distanceThreshold = 50;

             int distanceBetweenPoints = (int) Math.sqrt(Math.pow((startX - endX),2) + Math.pow((startY - endY),2));

            System.out.println(isDrawing);

            if (distanceBetweenPoints < distanceThreshold || isDrawing) {
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
                case KeyEvent.VK_LEFT -> direction = 'L';
                case KeyEvent.VK_RIGHT -> direction = 'R';
                case KeyEvent.VK_UP -> direction = 'U';
                case KeyEvent.VK_DOWN -> direction = 'D';
                case KeyEvent.VK_Q -> brushLifted = true;
                case KeyEvent.VK_1 -> color[0] = Color.BLACK;
                case KeyEvent.VK_2 -> color[0] = Color.blue;
                case KeyEvent.VK_3 -> color[0] = Color.green;
                case KeyEvent.VK_4 -> color[0] = Color.red;
                case KeyEvent.VK_5 -> color[0] = Color.orange;
                case KeyEvent.VK_6 -> color[0] = Color.pink;
                case KeyEvent.VK_7 -> color[0] = Color.yellow;
                case KeyEvent.VK_8 -> color[0] = new Color(139, 0, 255);//purple
                case KeyEvent.VK_9 -> color[0] = new Color(255, 255, 255);//white
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN -> direction = 'N';
                case KeyEvent.VK_Q -> brushLifted = false;
            }
        }
    }
}
