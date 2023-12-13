package main.canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is the panel that holds the buttons that allow
 * the user to access color changing and the eraser. It also
 * rests within the canvas frame.
 */

public class ToolbarPanel extends JPanel implements ActionListener {
    JButton colorButton;
    JButton eraserButton;
    JButton brushButton;

    private static int buttonType = 2;

    ToolbarPanel() {
        initPanel();
        initColorButton();
        initBrushButton();
        initEraserButton();
    }

    private void initPanel(){
        this.setPreferredSize(new Dimension(1000, 50));
        this.setBackground(Color.black);
        this.setFocusable(true);
    }

    private void initBrushButton() {
        brushButton = new JButton("Brush");
        brushButton.addActionListener(this);
        brushButton.setPreferredSize(new Dimension(100,40));
        brushButton.setOpaque(true);
        this.add(brushButton);
    }

    private void initEraserButton() {
        eraserButton = new JButton("eraser");
        eraserButton.addActionListener(this);
        eraserButton.setPreferredSize(new Dimension(100,40));
        eraserButton.setOpaque(true);
        this.add(eraserButton);
    }

    private void initColorButton() {
        colorButton = new JButton("Colour");
        colorButton.addActionListener(this);
        colorButton.setPreferredSize(new Dimension(100,40));
        colorButton.setOpaque(true);
        this.add(colorButton);
    }

    public static int getButtonType() {
        return buttonType;
    }

    public static void setButtonType(int buttonType) {
        ToolbarPanel.buttonType = buttonType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == colorButton) {
            buttonType = 1;
        } else if (e.getSource() ==  brushButton) {
            buttonType = 2;
        } else if (e.getSource() == eraserButton) {
            buttonType = 3;
        }
    }
}

