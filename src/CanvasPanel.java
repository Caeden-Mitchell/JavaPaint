import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CanvasPanel extends JPanel{
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 1000;
    static final int UNIT_SIZE = 10;

    final int[] x = new int[SCREEN_WIDTH/UNIT_SIZE];
    final int[] y = new int[SCREEN_HEIGHT/UNIT_SIZE];

    /*
    R = right
    L = left
    U = up
    D = down
     */
    char direction = 'R';
    
    boolean running = false;


    CanvasPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        
        start();
    }
    
    public void start() {
        running = true;
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g) {
        if (running) {
            //draws the grid
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
        }
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
            }
        }
    }
}
