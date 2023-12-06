package groupbuyapp.Client.Center.Content.Home;


import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class Home extends JPanel{
    public Home(){
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        add(new JLabel("Home"));
    }
}
