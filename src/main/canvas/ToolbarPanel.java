package main.canvas;

import main.fileHandling.FileHandler;

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
    JButton brushSizeButton;

    private static int buttonType = 2;

    private static int brushSize = 10;

    final FileHandler FILE_HANDLER = new FileHandler();

    Icon colorIcon =  new ImageIcon(FILE_HANDLER.getBufferedImageFromStream("Color_Wheel_Icon.png"));
    Icon brushIcon =  new ImageIcon(FILE_HANDLER.getBufferedImageFromStream("brush-icon-button.png"));
    Icon eraserIcon =  new ImageIcon(FILE_HANDLER.getBufferedImageFromStream("eraser-icon-button.png"));
    Icon burshSizeIcon =  new ImageIcon(FILE_HANDLER.getBufferedImageFromStream("brush-size-button.png"));

    ToolbarPanel() {
        initPanel();
        initColorButton();
        initBrushButton();
        initBrushSizeButtons();
        initEraserButton();
    }

    private void initPanel(){
        this.setPreferredSize(new Dimension(1000, 50));
        this.setBackground(Color.gray);
        this.setFocusable(true);
    }

    private void initColorButton() {
        colorButton = new JButton(colorIcon);
        colorButton.addActionListener(this);
        colorButton.setPreferredSize(new Dimension(40,40));
        //colorButton.setOpaque(false);
       colorButton.setBorder(null);
        colorButton.setBackground(null);
        this.add(colorButton);
    }

    private void initBrushButton() {
        brushButton = new JButton(brushIcon);
        brushButton.addActionListener(this);
        brushButton.setPreferredSize(new Dimension(40,40));
        brushButton.setBorder(null);
        brushButton.setBackground(null);
        this.add(brushButton);
    }

    private void initBrushSizeButtons() {
        brushSizeButton = new JButton(burshSizeIcon);
        brushSizeButton.addActionListener(this);
        brushSizeButton.setPreferredSize(new Dimension(40,40));
        brushSizeButton.setBorder(null);
        brushSizeButton.setBackground(null);
        this.add(brushSizeButton);
    }

    private void initEraserButton() {
        eraserButton = new JButton(eraserIcon);
        eraserButton.addActionListener(this);
        eraserButton.setPreferredSize(new Dimension(40,40));
        eraserButton.setBorder(null);
        eraserButton.setBackground(null);
        this.add(eraserButton);
    }



    public static int getButtonType() {
        return buttonType;
    }

    public static void setButtonType(int buttonType) {
        ToolbarPanel.buttonType = buttonType;
    }

    public static int getBrushSize() {
        if (brushSize > 800) {
            return brushSize = 800;
        } else if (brushSize <= 1) {
            return brushSize = 2;
        }
        return brushSize;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == colorButton) {
            buttonType = 1;
        } else if (e.getSource() ==  brushButton) {
            buttonType = 2;
        } else if (e.getSource() == eraserButton) {
            buttonType = 3;
        } else if (e.getSource() == brushSizeButton) {
            JTextField userField = new JTextField("" + brushSize);
            JOptionPane.showMessageDialog(this, new Object[] {"Brush size", userField},
                    null, JOptionPane.QUESTION_MESSAGE, burshSizeIcon);
            brushSize = Integer.parseInt(userField.getText());
            System.out.println(brushSize);
            buttonType = 4;
        }
    }
}

