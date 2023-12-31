package groupbuyapp.Client.ClientSidebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.RoundedButton;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;

public class ClientSidebar extends JPanel{
    private LogoContainer logoContainer;
    private ClientButtons clientButtons;
    
    public LogoContainer getLogoContainer() {
        return logoContainer;
    }

    public ClientButtons getClientButtons() {
        return clientButtons;
    }

    public JLabel getLogo(){
        return logoContainer.getLogo();
    }

    public JButton getHomeButton(){
        return clientButtons.getHomeButton();
    }

    public JButton getMyListingsButton(){
        return clientButtons.getMyListingsButton();
    }

    public JButton getMyGroupbuysButton(){
        return clientButtons.getMyGroupbuysButton();
    }

    public JButton getBrowseGroupbuysButton(){
        return clientButtons.getBrowseGroupbuysButton();
    }

    //constructor
    public ClientSidebar(){
        setBackground(GbuyColor.PANEL_COLOR);
        this.logoContainer = new LogoContainer();
        this.clientButtons = new ClientButtons();
        setLayout(new BorderLayout());
        setBackground(GbuyColor.PANEL_COLOR);
        add(logoContainer, BorderLayout.NORTH);
        add(clientButtons, BorderLayout.CENTER);
    }

    //inner classes
    public class LogoContainer extends JPanel{  
        private JLabel logo;

        public JLabel getLogo() {
            return logo;
        }

        private static final ImageIcon gbuyLogo = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/gbuy vector sidebar.png");

        public LogoContainer(){
            this.logo = new JLabel(gbuyLogo);
            logo.setOpaque(false);
            JPanel logoPanel = new JPanel();
            logoPanel.setOpaque(false);
            logoPanel.add(logo);
            setBackground(GbuyColor.PANEL_COLOR);
            add(logoPanel);
        }

    }

    public class ClientButtons extends JPanel{
        private JPanel buttonContainer;
        private RoundedButton homeButton;
        private RoundedButton myListingsButton;
        private RoundedButton myGroupbuysButton;
        private RoundedButton browseGroupbuysButton;

        private static ImageIcon homeIcon = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/home.png");
        private static ImageIcon myListingsIcon = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/my listings.png");
        private static ImageIcon myGroupbuysIcon = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/my groupbuys.png");
        private static ImageIcon browseGroupbuysIcon = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/browse groupbuys.png");
        
        private static ImageIcon homeIconAlt = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/home alt.png");
        private static ImageIcon myListingsIconAlt  = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/my listings alt.png");
        private static ImageIcon myGroupbuysIconAlt  = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/my groupbuys alt.png");
        private static ImageIcon browseGroupbuysIconAlt  = new ImageIcon("src/groupbuyapp/Client/ClientSidebar/img/browse groupbuys alt.png");

        private static Dimension buttonSize = new Dimension(200, 45);

        public static final int HOME = 1;
        public static final int MY_LISTINGS = 2;
        public static final int MY_GROUPBUYS = 3;
        public static final int BROWSE_GROUPBUYS = 4;

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

        public ClientButtons() {
            setOpaque(false);
            this.buttonContainer = new JPanel();
            this.homeButton = new RoundedButton("Home", homeIcon);
            this.myListingsButton = new RoundedButton("My Listings", myListingsIcon);
            this.myGroupbuysButton = new RoundedButton("My Groupbuys", myGroupbuysIcon);
            this.browseGroupbuysButton = new RoundedButton("Browse", browseGroupbuysIcon);

            homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            myListingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            myGroupbuysButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            browseGroupbuysButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            allButtons = new ArrayList<>();
            allButtons.addAll(List.of(homeButton, myListingsButton, myGroupbuysButton, browseGroupbuysButton));

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
                case HOME:
                    setToSelectedState(homeButton, homeIconAlt);
                    setToNormalState(myListingsButton,  myListingsIcon);
                    setToNormalState(myGroupbuysButton, myGroupbuysIcon);
                    setToNormalState(browseGroupbuysButton, browseGroupbuysIcon);
                    break;
                    
                case MY_LISTINGS:
                    setToNormalState(homeButton, homeIcon);
                    setToSelectedState(myListingsButton, myListingsIconAlt);
                    setToNormalState(myGroupbuysButton, myGroupbuysIcon);
                    setToNormalState(browseGroupbuysButton, browseGroupbuysIcon);
                    break;
                    
                case MY_GROUPBUYS:
                    setToNormalState(homeButton, homeIcon);
                    setToNormalState(myListingsButton,  myListingsIcon);
                    setToSelectedState(myGroupbuysButton, myGroupbuysIconAlt);
                    setToNormalState(browseGroupbuysButton, browseGroupbuysIcon);
                    break;
            
                case BROWSE_GROUPBUYS:
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
}
