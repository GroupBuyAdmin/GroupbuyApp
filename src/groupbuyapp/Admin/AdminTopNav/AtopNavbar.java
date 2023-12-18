package groupbuyapp.Admin.AdminTopNav;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;

public class AtopNavbar extends JPanel{
    private JLabel logoContainer;

    private static final ImageIcon adminLogo = new ImageIcon("src/groupbuyapp/Admin/AdminTopNav/img/admin Logo.png");

    public AtopNavbar(){
        this.logoContainer = new JLabel(adminLogo);

        // Dimension dim = getPreferredSize();
        // dim.height = 80;
        // setPreferredSize(dim);
        setBackground(GbuyColor.MAIN_COLOR);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15,10,10,0));
        add(logoContainer, BorderLayout.WEST);
    }
}
