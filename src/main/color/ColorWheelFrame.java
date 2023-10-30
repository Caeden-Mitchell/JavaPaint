package main.color;

import javax.swing.*;

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
