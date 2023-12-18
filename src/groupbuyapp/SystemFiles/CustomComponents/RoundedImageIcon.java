package groupbuyapp.SystemFiles.CustomComponents;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;

public class RoundedImageIcon extends ImageIcon {
    private int radius;

    public RoundedImageIcon(Image image){
        super(image);
        this.radius = 10;
    }

    public RoundedImageIcon(String filename, int radius) {
        super(filename);
        this.radius = radius;
    }

    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        Image image = getImage();
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            RoundRectangle2D roundRect = new RoundRectangle2D.Float(x, y, getIconWidth() - 1, getIconHeight() - 1, radius, radius);
            g2d.clip(roundRect);

            g2d.drawImage(image, x, y, c);
            // g2d.setColor(Color.BLACK); // Set the border color
            // g2d.draw(roundRect);

            g2d.dispose();
        }
    }
}