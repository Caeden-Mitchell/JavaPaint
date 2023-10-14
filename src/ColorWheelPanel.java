import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ColorWheelPanel extends JPanel{
    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 500;

    static int WIDTH = 150;
    static int HEIGHT = 150;
    static int X = 250-(WIDTH/2);
    static int Y = 250-(HEIGHT/2);


    ColorWheelPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawLine(0,250,500,250);
        g.drawLine(250,0,250,500);



        g.drawOval(X, Y, WIDTH, HEIGHT);
    }
}
