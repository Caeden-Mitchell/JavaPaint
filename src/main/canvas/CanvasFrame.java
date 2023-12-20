package main.canvas;

import javax.swing.*;
import java.awt.*;

public class CanvasFrame extends JFrame {

    /**
     * This is the frame that shows up upon startup, it contains
     * the canvas panel and the toolbar panel.
     */
    public CanvasFrame() {
        // Everything underneath this comment is done so the two panels
        // added will format correctly within the frame. It's a layout manager.

        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .1;
        c.weighty = 0.1;
        this.add(new ToolbarPanel(), c);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 0;
        c.weighty = 0;
        c.gridy = 1;
        this.add(new CanvasPanel(), c);

        // These define what the frame is capable of doing
        // such as change size and how it should act when the
        // window is closed
        this.setTitle("Etch-a-Sketch");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.pack(); // Packs everything that has been added to frame nicely
        this.setVisible(true);

        this.setLocationRelativeTo(null); // Sets the frames location to the center of the screen

    }

}
