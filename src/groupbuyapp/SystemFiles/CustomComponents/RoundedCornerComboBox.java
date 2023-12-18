package groupbuyapp.SystemFiles.CustomComponents;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * The class {@code RoundedCornerComboBox} defines a custom {@code JComboBox} with rounded corners and a custom dropdown button icon.
 * It overrides the default border and UI of the JComboBox to achieve the desired appearance.
 * 
 * @author BSCS 2A group 5
 */
public class RoundedCornerComboBox extends JComboBox<String> {
    private int cornerRadius = 10; // Adjust the corner radius as needed
    private Icon customDropdownIcon; // Custom icon for the dropdown button

    /**
     * Constructor that creates a new RoundedCornerComboBox with the given array of items.
     * @param items The array of items to be displayed in the combo box.
     */
    public RoundedCornerComboBox(String[] items) {
        super(items);
        setOpaque(false); // Make the combo box transparent
        setBorder(new RoundBorder());
        setUI(new RoundComboBoxUI());
        setFocusable(false);
    }

    /**
     * Sets a custom icon for the dropdown button.
     * @param icon The custom icon to be set for the dropdown button.
     */
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
}
