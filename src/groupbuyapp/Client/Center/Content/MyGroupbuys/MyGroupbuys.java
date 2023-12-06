package groupbuyapp.Client.Center.Content.MyGroupbuys;


import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class MyGroupbuys extends JPanel{
    public MyGroupbuys(){
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        add(new JLabel("My Groupbuys"));
    }
}
