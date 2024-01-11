package main.canvas;

import javax.swing.*;
import java.awt.*;

public class CanvasFrame extends JFrame {

    /**
     * This is the frame that shows up upon startup, it contains
     * the canvas panel and the toolbar panel.
     */
    GridBagConstraints c = new GridBagConstraints();
    public CanvasFrame() {
        // Everything underneath this comment is done so the two panels
        // added will format correctly within the frame. It's a layout manager.
        this.setLayout(new GridBagLayout());
        setGridBagConstraints(GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH, 0, 0, 0.1,0.1);
        this.add(new ToolbarPanel(), c);
        setGridBagConstraints(GridBagConstraints.NONE, GridBagConstraints.PAGE_START, 0, 1, 0,0);
        this.add(new CanvasPanel(), c);

        // These define what the frame is capable of doing
        // such as change size and how it should act when the window is closed
        this.setTitle("Etch-a-Sketch");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.pack(); // Packs everything that has been added to frame nicely
        this.setVisible(true);

        this.setLocationRelativeTo(null); // Sets the frames location to the center of the screen
    }

    private void setGridBagConstraints(int fill, int anchor, int gridx, int gridy, double weightx, double weighty) {
        c.fill = fill;
        c.anchor = anchor;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = weightx;
        c.weighty = weighty;
    }

}
