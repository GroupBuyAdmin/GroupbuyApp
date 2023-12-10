package groupbuyapp.Misc.CustomComponents;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * The {@code RoundedCornerTextArea} class is a custom implementation of the {@code JTextArea} class in Java Swing that adds rounded corners to the text area component.
 * It overrides the paintComponent method to draw a rounded rectangle as the background of the text area, and also provides a custom border with rounded corners.
 * @author BSCS 2A group 5
 */
public class RoundedCornerTextArea extends JTextArea {
    private int cornerRadius = 10; // Adjust the corner radius as needed

    /**
     * Constructs a new RoundedCornerTextArea with default number of rows and columns.
     * The text area is set to be transparent, with line wrapping and word wrapping enabled.
     * It also has a custom border with rounded corners.
     * 
     */
    public RoundedCornerTextArea() {
        super();
        setOpaque(false);   // Make the text area transparent
        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(new RoundBorder());
    }

    /**
     * Constructs a new RoundedCornerTextArea with the specified number of rows and columns.
     * The text area is set to be transparent, with line wrapping and word wrapping enabled.
     * It also has a custom border with rounded corners.
     * 
     * @param rows the number of rows for the text area
     * @param columns the number of columns for the text area
     */
    public RoundedCornerTextArea(int rows, int columns) {
        super(rows, columns);
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(new RoundBorder());
    }

    /**
     * Overrides the paintComponent method to draw a rounded rectangle as the background of the text area.
     * 
     * @param g the Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        // Set the background color
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        super.paintComponent(g2d);

        g2d.dispose();
    }

    /**
     * The RoundBorder class implements the Border interface to provide a custom border with rounded corners for the text area.
     */
    private class RoundBorder implements Border {

        /**
         * Implements the paintBorder method of the Border interface to draw a rounded rectangle as the border of the text area.
         * 
         * @param c the component being painted
         * @param g the Graphics object used for painting
         * @param x the x-coordinate of the top-left corner of the border
         * @param y the y-coordinate of the top-left corner of the border
         * @param width the width of the border
         * @param height the height of the border
         */
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width - 1, height - 1, cornerRadius, cornerRadius);

            g2d.setColor(getBackground());
            g2d.draw(roundedRectangle);

            g2d.dispose();
        }

        /**
         * Implements the getBorderInsets method of the Border interface to return the insets of the border.
         * 
         * @param c the component for which to get the border insets
         * @return the insets of the border
         */
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(cornerRadius/2, cornerRadius/2, cornerRadius/2, cornerRadius/2);
        }

        /**
         * Implements the isBorderOpaque method of the Border interface to indicate that the border is not opaque.
         * 
         * @return false
         */
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}
