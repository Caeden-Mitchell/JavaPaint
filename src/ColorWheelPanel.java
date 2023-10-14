import javax.swing.*;
import java.awt.*;

public class ColorWheelPanel extends JComponent {
    public ColorWheelPanel() {
        setPreferredSize(new Dimension(400, 400));
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        int margin = 10;
        int radius = (Math.min(w, h) - 2 * margin) / 2;
        int cx = w / 2;
        int cy = h / 2;
        float[] dist = {0.F, 1.0F};
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, w, h);
        for (int angle = 0; angle < 360; ++angle) {
            Color color = hsvToRgb(angle, 1.0, 1.0);
            Color[] colors = {Color.WHITE, color};
            RadialGradientPaint paint = new RadialGradientPaint(cx, cy,
                    radius, dist, colors);
            g2.setPaint(paint);
            g2.fillArc(cx - radius, cy - radius, radius * 2, radius * 2,
                    angle, 1);
        }
    }

    public static Color hsvToRgb(int h, double s, double v) {
        double hp = h/60.0;
        double chroma = s * v;
        double x = chroma * (1 - Math.abs(hp % 2.0 - 1));
        double m = v - chroma;
        double red = 0, green = 0, blue = 0;
        if (hp <= 1) {
            red = chroma;
            green = x;
        } else if (hp <= 2) {
            red = x;
            green = chroma;
        } else if (hp <= 3) {
            green = chroma;
            blue = x;
        } else if (hp <= 4) {
            green = x;
            blue = chroma;
        } else if (hp <= 5) {
            red = x;
            blue = chroma;
        } else {
            red = chroma;
            blue = x;
        }
        red += m;
        green += m;
        blue += m;
        return new Color((int)(red * 255), (int)(green * 255), (int)(blue * 255));
    }
}