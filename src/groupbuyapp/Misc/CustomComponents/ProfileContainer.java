package groupbuyapp.Misc.CustomComponents;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ProfileContainer extends JPanel {
    private ImageIcon profileImageIcon;

    public ProfileContainer(ImageIcon profileImageIcon) {
        this.profileImageIcon = profileImageIcon;
        setPreferredSize(new Dimension(5, 5)); // Set your preferred size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Create a round clip for the profile image
        Shape clip = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        g2d.setClip(clip);

        // Draw the profile image
        profileImageIcon.paintIcon(this, g2d, 0, 0);

        // Dispose the Graphics2D object
        g2d.dispose();
    }
}
