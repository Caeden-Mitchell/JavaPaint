package main.color;

import javax.swing.*;

/**
 * Similar idea to the canvas frame here.
 * just doesn't need to set a layout as there
 * is only 1 panel.
 */

public class ColorWheelFrame extends JFrame {
    public ColorWheelFrame(){
        this.add(new ColorWheelPanel());
        this.setTitle("Etch-a-Sketch");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setResizable(false);
        this.pack();

        this.setLocationRelativeTo(null);

    }
}
