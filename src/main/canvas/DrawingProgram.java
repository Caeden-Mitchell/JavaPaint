package main.canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawingProgram extends JFrame {
    private JPanel drawingPanel;
    private BufferedImage image;
    private Graphics2D g2d;

    private int currentX, currentY, oldX, oldY;

    public DrawingProgram() {
        setTitle("Drawing Program");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, this);
                }
            }
        };

        add(drawingPanel);

        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);

        // Add mouse listeners for drawing
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                if (g2d != null) {
                    g2d.drawLine(oldX, oldY, currentX, currentY);
                    drawingPanel.repaint();
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });

        // Resize listener to handle resizing of the frame
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Create a new image with the updated size
                BufferedImage newImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D newG2d = newImage.createGraphics();
                // Draw the existing image onto the new image
                newG2d.drawImage(image, 0, 0, null);
                // Update references to the new image and graphics
                image = newImage;
                g2d = newG2d;
                drawingPanel.repaint();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawingProgram drawingProgram = new DrawingProgram();
            drawingProgram.setVisible(true);
        });
    }
}
