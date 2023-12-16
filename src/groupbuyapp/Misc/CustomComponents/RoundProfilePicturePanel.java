package groupbuyapp.Misc.CustomComponents;

import javax.swing.*;

import groupbuyapp.Misc.ColorPalette.GbuyColor;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundProfilePicturePanel extends JPanel {
    private ImageIcon imageIcon;

    public RoundProfilePicturePanel(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        setPreferredSize(new Dimension(50, 50)); // Set your preferred size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw rounded border
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diameter = Math.min(getWidth(), getHeight());
        Ellipse2D.Double ellipse = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2d.setClip(ellipse);
        g2d.setColor(GbuyColor.MAIN_COLOR); // Set the border color
        g2d.setStroke(new BasicStroke(1)); // Set the border thickness
        g2d.draw(ellipse);

        // Draw the image inside the rounded border
        if (imageIcon != null) {
            Image image = imageIcon.getImage();
            g2d.drawImage(image, 0, 0, diameter, diameter, this);
        }

        g2d.dispose();
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         JFrame frame = new JFrame("Round Profile Picture Panel");
    //         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //         // Example ImageIcon (replace with your own image)
    //         ImageIcon imageIcon = new ImageIcon("src/groupbuyapp/Client/LogIn/img/default profile.png");

    //         RoundProfilePicturePanel profilePanel = new RoundProfilePicturePanel(imageIcon);
    //         frame.getContentPane().add(profilePanel);

    //         frame.pack();
    //         frame.setLocationRelativeTo(null);
    //         frame.setVisible(true);
    //     });
    // }
}
