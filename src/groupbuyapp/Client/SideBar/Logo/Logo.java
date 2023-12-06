package groupbuyapp.Client.SideBar.Logo;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Logo extends JPanel{
    private JLabel logoContainer;
    public JLabel getLogoContainer() {
        return logoContainer;
    }

    private final String logoPath = "src/groupbuyapp/Client/SideBar/Logo/img/gbuy vector sidebar.png";

    public Logo(){
        setPreferredSize(new Dimension(getWidth(), 200));
        logoContainer = new JLabel();
        logoContainer.setIcon(new ImageIcon(logoPath));
        logoContainer.setHorizontalAlignment(JLabel.CENTER);
        setOpaque(false);
        setLayout(new BorderLayout());
        add(logoContainer,BorderLayout.CENTER);
    }
}
