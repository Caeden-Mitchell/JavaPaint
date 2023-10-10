import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CanvasPanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 1000;
    static final int UNIT_SIZE = 10;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    boolean[] xDraw = new boolean[GAME_UNITS];
    boolean[] yDraw = new boolean[GAME_UNITS];

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


    CanvasPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        start();
    }
    
    public void start() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g) {
        if (running) {
            //draws the grid


            for (int i = 0; i < GAME_UNITS-1; i++) {
                g.setColor(Color.BLACK);
                //System.out.println(x[i]);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    public void move() {
        if (direction != 'N' && !brushLifted) {
            for(int i = GAME_UNITS-1; i > 0; i--) {
                x[i] = x[i-1];
                y[i] = y[i-1];
            }
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                System.out.println(x[0]);
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                System.out.println(x[0]);
                break;
        }

        System.out.println("");
    }

    public void checkCollisions() {
        //checks if head collides with left border
        if(x[0] < 0) {
            x[0] = 0;
        }
        //checks if head collides with right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //checks if head collides with top border
        if(y[0] < 0) {
            running = false;
        }
        //checks if head collides with bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
        }
        repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    direction = 'D';
                    break;
                case KeyEvent.VK_Q:
                    brushLifted = true;
                    break;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN:
                    direction = 'N';
                    break;
                case KeyEvent.VK_Q:
                    brushLifted = false;
                    break;

            }

        }
    }
}
