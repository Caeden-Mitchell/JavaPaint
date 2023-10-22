package main.canvas;

import javax.swing.*;

public class CanvasFrame extends JFrame {
    public CanvasFrame() {
        this.add(new CanvasPanel());
        this.setTitle("Etch-a-Sketch");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

        this.setLocationRelativeTo(null);

    }

}
