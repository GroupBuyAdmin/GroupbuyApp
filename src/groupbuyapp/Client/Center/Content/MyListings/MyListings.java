package groupbuyapp.Client.Center.Content.MyListings;


import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class MyListings extends JPanel{
    public MyListings(){
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        add(new JLabel("My Listings"));
    }        
}
