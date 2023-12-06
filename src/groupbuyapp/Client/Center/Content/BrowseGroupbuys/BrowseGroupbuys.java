package groupbuyapp.Client.Center.Content.BrowseGroupbuys;


import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class BrowseGroupbuys extends JPanel{
    public BrowseGroupbuys(){
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        add(new JLabel("Browse Groupbuys"));
    }
}
