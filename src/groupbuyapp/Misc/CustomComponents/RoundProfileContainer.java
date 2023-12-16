package groupbuyapp.Misc.CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundProfileContainer extends JFrame {

    public RoundProfileContainer(ImageIcon imageIcon) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Round Profile Container");
        setLayout(new BorderLayout());

        ProfileContainer profileContainer = new ProfileContainer(imageIcon);
        add(profileContainer, BorderLayout.CENTER);

        // pack();
        // setSize(150,150);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Replace "path/to/your/image.jpg" with the actual path to your image file
        ImageIcon imageIcon = new ImageIcon("src/groupbuyapp/Client/LogIn/img/default profile.png");

        SwingUtilities.invokeLater(() -> new RoundProfileContainer(imageIcon));
    }
}

