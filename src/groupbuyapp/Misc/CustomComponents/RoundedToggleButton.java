package groupbuyapp.Misc.CustomComponents;

import javax.swing.*;

import groupbuyapp.Misc.ColorPalette.GbuyColor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoundedToggleButton extends JToggleButton {

    private Color defaultColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color borderColor;
    private boolean drawBorder;
    private int cornerRadius;

    public RoundedToggleButton(String text) {
        super(text);
        this.defaultColor = getBackground();
        this.cornerRadius = 10;
        this.borderColor = Color.black;
        this.drawBorder = true;
        setContentAreaFilled(false);
        updateHoverColor();
        updatePressedColor();

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }

    private void updateHoverColor() {
        float[] hsb = Color.RGBtoHSB(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue(), null);
        float brightness = Math.max(0, hsb[2] - 0.1f);
        hoverColor = Color.getHSBColor(hsb[0], hsb[1], brightness);
    }

    private void updatePressedColor() {
        float[] hsb = Color.RGBtoHSB(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), null);
        float brightness = Math.max(0, hsb[2] - 0.1f);
        pressedColor = Color.getHSBColor(hsb[0], hsb[1], brightness);
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
        updateHoverColor();
        updatePressedColor();
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isSelected()) {
            g2.setColor(pressedColor);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(defaultColor);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        if (drawBorder) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        }
    }

    @Override
    public boolean isFocusPainted() {
        return false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Customizable Color ToggleButton Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        RoundedToggleButton toggleButton = new RoundedToggleButton("Toggle");
        toggleButton.setDefaultColor(GbuyColor.MAIN_COLOR);
        toggleButton.setPressedColor(GbuyColor.ONGOING_COLOR);
        toggleButton.setCornerRadius(15);
        toggleButton.setDrawBorder(false);
        toggleButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);


        frame.getContentPane().add(toggleButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
