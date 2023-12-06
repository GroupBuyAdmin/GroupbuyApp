package groupbuyapp.Misc.CustomComponents;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedCornerTextArea extends JTextArea {
    private int cornerRadius = 10; // Adjust the corner radius as needed

    public RoundedCornerTextArea() {
        super();
        setOpaque(false);   // Make the text area transparent
        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(new RoundBorder());
    }

    public RoundedCornerTextArea(int rows, int columns) {
        super(rows, columns);
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(new RoundBorder());
    }

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

    private class RoundBorder implements Border {

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width - 1, height - 1, cornerRadius, cornerRadius);

            g2d.setColor(getBackground());
            g2d.draw(roundedRectangle);

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(cornerRadius/2, cornerRadius/2, cornerRadius/2, cornerRadius/2);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("RoundedCornerTextArea Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            RoundedCornerTextArea textArea = new RoundedCornerTextArea();
            textArea.setColumns(20);
            textArea.setRows(5);

            JPanel panel = new JPanel();
            JScrollPane s = new JScrollPane(textArea);
            panel.add(s);
            s.setBorder(BorderFactory.createEmptyBorder());

            frame.add(panel);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
