package main.color;

import java.awt.*;

public class ColorHandler {
    int hue;
    double saturation;
    double value = 1.0;
    int redValue;
    int greenValue;
    int blueValue;
    Color rgbColor;

    public int getHue() {
        return hue;
    }

    public void setHue(int var) {
        hue = var;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double var) {
        saturation = var;
    }

    public void setRgbColor() {
        System.out.println("red: " + redValue );
        System.out.println("green: " + greenValue);
        System.out.println("blue: " + blueValue);
        rgbColor = hsvToRgb();

    }

    public Color getRgbColor() {
        return rgbColor = hsvToRgb();
    }


    public Color hsvToRgb() {
        double hp = hue/60.0;
        double chroma = saturation * value;
        double x = chroma * (1 - Math.abs(hp % 2.0 - 1));
        double m = value - chroma;
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
        red+= m;
        green += m;
        blue += m;

        redValue = (int)(red * 255);
        greenValue = (int)(green * 255);
        blueValue = (int)(blue * 255);

        return new Color(redValue, greenValue, blueValue);
    }
}
