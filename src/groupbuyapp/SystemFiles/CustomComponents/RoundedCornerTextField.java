package groupbuyapp.SystemFiles.CustomComponents;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * The {@code RoundedCornerTextField} class is a custom implementation of the {@code JTextField} class in Java Swing that creates a text field with rounded corners.
 * It overrides the paintComponent method to draw a rounded rectangle as the background of the text field, and uses a custom RoundBorder class to draw the border of the text field as a rounded rectangle.
 * @author BSCS 2A group 5
 */
public class RoundedCornerTextField extends JTextField {
    private int cornerRadius = 10; // Adjust the corner radius as needed
    private Color borderColor;

    /**
     * Constructs a new RoundedCornerTextField with default number of columns.
     * It sets the text field to be transparent and applies a RoundBorder as the border.
     */
    public RoundedCornerTextField() {
        super();
        borderColor = null;
        setOpaque(false); // Make the text field transparent
        setBorder(new RoundBorder());
    }

    /**
     * Constructs a new RoundedCornerTextField with the specified number of columns.
     * It sets the text field to be transparent and applies a RoundBorder as the border.
     * @param columns the number of columns for the text field
     */
    public RoundedCornerTextField(int columns) {
        super(columns);
        borderColor = null;
        setOpaque(false);
        setBorder(new RoundBorder());
    }

    public RoundedCornerTextField(Color borderColor) {
        this.borderColor = borderColor;
        setOpaque(false);
        setBorder(new RoundBorder());
    }

    /**
     * Overrides the paintComponent method of JTextField to draw a rounded rectangle as the background of the text field.
     * @param g the Graphics object to paint on
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
     * The RoundBorder class implements the Border interface to draw a rounded rectangle as the border of the text field.
     */
    private class RoundBorder implements Border {
        /**
         * Implements the paintBorder method of the Border interface to draw a rounded rectangle as the border of the text field.
         * @param c the component to paint the border on
         * @param g the Graphics object to paint with
         * @param x the x-coordinate of the border
         * @param y the y-coordinate of the border
         * @param width the width of the border
         * @param height the height of the border
         */
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width - 1, height - 1, cornerRadius, cornerRadius);
            
            if(borderColor == null){
                g2d.setColor(getBackground());
            } else {
                g2d.setColor(borderColor);                
            }
            
            
            g2d.draw(roundedRectangle);

            g2d.dispose();
        }

        /**
         * Implements the getBorderInsets method of the Border interface to return the insets of the border.
         * @param c the component to get the border insets for
         * @return the insets of the border
         */
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(cornerRadius/2, cornerRadius/2, cornerRadius/2, cornerRadius/2);
        }

        /**
         * Implements the isBorderOpaque method of the Border interface to indicate whether the border is opaque.
         * @return true if the border is opaque, false otherwise
         */
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    /**
     * The main method creates a JFrame and adds a RoundedCornerTextField to it for demonstration purposes.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("RoundedCornerTextField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            RoundedCornerTextField textField = new RoundedCornerTextField();
            textField.setColumns(20);

            JPanel panel = new JPanel();
            panel.add(textField);

            frame.add(panel);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
