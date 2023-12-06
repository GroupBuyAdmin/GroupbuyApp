package groupbuyapp.Misc.CustomComponents;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RoundedButton extends JButton {

    protected Color buttonColor;
    protected Color hoverColor;
    protected Color pressedColor;
    private Color borderColor;
    private boolean drawBorder;
    protected int cornerRadius;

    public RoundedButton(String text) { //make button with text only
        super(text);
        this.buttonColor = getBackground();
        this.cornerRadius = 10;
        this.borderColor = Color.black;
        this.drawBorder = true;
        getFont();
        setContentAreaFilled(false); 
        updateHoverColor();
        updatePressedColor();
    }
    public RoundedButton(String text, ImageIcon imageIcon) {    //make button with icon and text
        super(text, imageIcon);
        this.buttonColor = getBackground();
        this.cornerRadius = 10;
        this.borderColor = Color.black;
        this.drawBorder = true;
        getFont();
        setContentAreaFilled(false); 
        updateHoverColor();
        updatePressedColor();
    }

    private void updateHoverColor() {
        float[] hsb = Color.RGBtoHSB(buttonColor.getRed(), buttonColor.getGreen(), buttonColor.getBlue(), null);
        float brightness = Math.max(0, hsb[2] - 0.1f);
        hoverColor = Color.getHSBColor(hsb[0], hsb[1], brightness);
    }

    private void updatePressedColor() {
        float[] hsb = Color.RGBtoHSB(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), null);
        float brightness = Math.max(0, hsb[2] - 0.1f);
        pressedColor = Color.getHSBColor(hsb[0], hsb[1], brightness);
    }

    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
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

    public void setButtonFont(Font buttonFont) {
        setFont(buttonFont);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(pressedColor);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(buttonColor);
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
}
