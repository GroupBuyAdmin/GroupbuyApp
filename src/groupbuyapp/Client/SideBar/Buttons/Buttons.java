package groupbuyapp.Client.SideBar.Buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundedButton;
import groupbuyapp.Misc.Fonts.GbuyFont;

public class Buttons extends JPanel{
    private JPanel buttonContainer;

    private RoundedButton homeButton;
    private RoundedButton myListingsButton;
    private RoundedButton myGroupbuysButton;
    private RoundedButton browseGroupbuysButton;

    private static ImageIcon homeIcon = new ImageIcon("src/groupbuyapp/Client/SideBar/Buttons/img/home.png");
    private static ImageIcon myListingsIcon = new ImageIcon("src/groupbuyapp/Client/SideBar/Buttons/img/my listings.png");
    private static ImageIcon myGroupbuysIcon = new ImageIcon("src/groupbuyapp/Client/SideBar/Buttons/img/my groupbuys.png");
    private static ImageIcon browseGroupbuysIcon = new ImageIcon("src/groupbuyapp/Client/SideBar/Buttons/img/browse groupbuys.png");
    
    private static ImageIcon homeIconAlt = new ImageIcon("src/groupbuyapp/Client/SideBar/Buttons/img/home alt.png");
    private static ImageIcon myListingsIconAlt  = new ImageIcon("src/groupbuyapp/Client/SideBar/Buttons/img/my listings alt.png");
    private static ImageIcon myGroupbuysIconAlt  = new ImageIcon("src/groupbuyapp/Client/SideBar/Buttons/img/my groupbuys alt.png");
    private static ImageIcon browseGroupbuysIconAlt  = new ImageIcon("src/groupbuyapp/Client/SideBar/Buttons/img/browse groupbuys alt.png");

    private static Dimension buttonSize = new Dimension(200, 45);

    private ArrayList<RoundedButton> allButtons;

    public RoundedButton getHomeButton() {
        return homeButton;
    }

    public RoundedButton getMyListingsButton() {
        return myListingsButton;
    }

    public RoundedButton getMyGroupbuysButton() {
        return myGroupbuysButton;
    }

    public RoundedButton getBrowseGroupbuysButton() {
        return browseGroupbuysButton;
    }

    public Buttons() {
        this.buttonContainer = new JPanel();
        this.homeButton = new RoundedButton("Home", homeIcon);
        this.myListingsButton = new RoundedButton("My Listings", myListingsIcon);
        this.myGroupbuysButton = new RoundedButton("My Groupbuys", myGroupbuysIcon);
        this.browseGroupbuysButton = new RoundedButton("Browse", browseGroupbuysIcon);
        allButtons = new ArrayList<>();
        allButtons.addAll(List.of(homeButton, myListingsButton, myGroupbuysButton, browseGroupbuysButton));

        setBackground(Color.white);
        initCustomButtons();

        //-----------------------------------------------------------------------
        buttonContainer.setLayout(new GridLayout(0,1, 0, 20));
        buttonContainer.add(homeButton);
        buttonContainer.add(myListingsButton);
        buttonContainer.add(myGroupbuysButton);
        buttonContainer.add(browseGroupbuysButton);
        buttonContainer.setBackground(Color.white);
    
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 0, 20);
        add(buttonContainer, gbc);
        gbc.gridy++;
        gbc.weighty = 10;
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        add(emptyPanel, gbc);

    }

    private void initCustomButtons(){
        setToNormalState(homeButton, homeIcon);
        setToSelectedState(homeButton, homeIconAlt);
        
        setToNormalState(myListingsButton, myListingsIcon);
        setToNormalState(myGroupbuysButton, myGroupbuysIcon);
        setToNormalState(browseGroupbuysButton, browseGroupbuysIcon);
    }
        
    
    public void setSelected(int index){
        switch (index) {
            case 1:
                setToSelectedState(homeButton, homeIconAlt);
                setToNormalState(myListingsButton,  myListingsIcon);
                setToNormalState(myGroupbuysButton, myGroupbuysIcon);
                setToNormalState(browseGroupbuysButton, browseGroupbuysIcon);
                break;
                
            case 2:
                setToNormalState(homeButton, homeIcon);
                setToSelectedState(myListingsButton, myListingsIconAlt);
                setToNormalState(myGroupbuysButton, myGroupbuysIcon);
                setToNormalState(browseGroupbuysButton, browseGroupbuysIcon);
                break;
                
            case 3:
                setToNormalState(homeButton, homeIcon);
                setToNormalState(myListingsButton,  myListingsIcon);
                setToSelectedState(myGroupbuysButton, myGroupbuysIconAlt);
                setToNormalState(browseGroupbuysButton, browseGroupbuysIcon);
                break;
        
            case 4:
                setToNormalState(homeButton, homeIcon);
                setToNormalState(myListingsButton,  myListingsIcon);
                setToNormalState(myGroupbuysButton, myGroupbuysIcon);
                setToSelectedState(browseGroupbuysButton, browseGroupbuysIconAlt);
                break;

            default:
                break;
        }
        
    }

    private void setToNormalState(RoundedButton rButton, ImageIcon imageIcon){
        rButton.setButtonColor(Color.white);
        rButton.setForeground(GbuyColor.MAIN_TEXT_COLOR);
        rButton.setDrawBorder(false);
        rButton.setHorizontalAlignment(SwingConstants.LEADING);
        rButton.setIconTextGap(20);
        rButton.setPreferredSize(buttonSize);
        rButton.setButtonFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
        rButton.setIcon(imageIcon);
        rButton.revalidate();
        rButton.setCornerRadius(8);
        rButton.repaint();
    }

    private void setToSelectedState(RoundedButton rButton, ImageIcon imageIcon){
        rButton.setButtonColor(GbuyColor.MAIN_COLOR);
        rButton.setForeground(Color.white);
        rButton.setIcon(imageIcon);
        rButton.revalidate();
        rButton.repaint();
    }
    
}
