package groupbuyapp.Misc.CustomComponents;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedCornerComboBox extends JComboBox<String> {
    private int cornerRadius = 10; // Adjust the corner radius as needed
    private Icon customDropdownIcon; // Custom icon for the dropdown button

    public RoundedCornerComboBox(String[] items) {
        super(items);
        setOpaque(false); // Make the combo box transparent
        setBorder(new RoundBorder());
        setUI(new RoundComboBoxUI());
        setFocusable(false);
    }

    public void setCustomDropdownIcon(Icon icon) {
        this.customDropdownIcon = icon;
        // Trigger a repaint of the combo box to update the icon
        if (getUI() instanceof RoundComboBoxUI) {
            ((RoundComboBoxUI) getUI()).updateButtonIcon();
        }
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
            return new Insets(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    private class RoundComboBoxUI extends BasicComboBoxUI {
        private RoundedButton arrowButton;

        @Override
        protected JButton createArrowButton() {
            arrowButton = new RoundedButton("");
            arrowButton.setBackground(getBackground());
            arrowButton.setDrawBorder(false);
            updateButtonIcon(); // Set the custom icon
            return arrowButton;
        }

        public void updateButtonIcon() {
            if (customDropdownIcon != null) {
                arrowButton.setIcon(customDropdownIcon);
            } else {
                // Set the default arrow icon if no custom icon is provided
                arrowButton.setIcon(UIManager.getIcon("ComboBox.arrowIcon"));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("RoundedCornerComboBox Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] items = {"Option 1", "Option 2", "Option 3", "Option 4"};

            RoundedCornerComboBox comboBox = new RoundedCornerComboBox(items);
            
            // Create a custom icon for the dropdown button (you can replace it with your own icon)
            ImageIcon img = new ImageIcon("src/gbuysytem/GUI/Body/DashboardPanels/ProductsPanel/img/down.png");
            Icon customDropdownIcon = new ImageIcon(img.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
            comboBox.setCustomDropdownIcon(customDropdownIcon);

            JPanel panel = new JPanel();
            panel.add(comboBox);

            frame.add(panel);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
