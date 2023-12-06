package groupbuyapp.Client.SideBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import groupbuyapp.Client.SideBar.Buttons.Buttons;
import groupbuyapp.Client.SideBar.Logo.Logo;

public class SideBar extends JPanel{
    private Logo logo;
    private Buttons buttons;
    
    public Logo getLogo() {
        return logo;
    }

    public Buttons getButtons() {
        return buttons;
    }

    public SideBar(){
        setPreferredSize(new Dimension(245, getHeight()));
        setBackground(Color.white);

        this.logo = new Logo();
        this.buttons = new Buttons();

        setLayout(new BorderLayout());
        add(logo, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);


    }
}
