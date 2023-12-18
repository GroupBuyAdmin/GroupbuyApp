package groupbuyapp.Admin.Misc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

public class BottomLineBorder implements Border {

    private final Color lineColor;

    public BottomLineBorder(Color lineColor) {
        this.lineColor = lineColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(lineColor);
        g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 1, 0); // Only the bottom border is 1 pixel
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
