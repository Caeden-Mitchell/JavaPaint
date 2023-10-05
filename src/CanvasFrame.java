import javax.swing.*;
import java.awt.*;

public class CanvasFrame extends JFrame {
    CanvasFrame() {
        this.add(new CanvasPanel());
        this.setTitle("Etch-a-Sketch");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(500,500));
        this.pack();
        this.setVisible(true);

        this.setLocationRelativeTo(null);

    }

}
