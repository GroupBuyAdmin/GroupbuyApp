package groupbuyapp.Client.ClientCenter.ClientContent;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers.BasicDisplayer;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers.ClientBrowser;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers.ClientHomeDisplayer;
import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;

public class ClientContent extends JPanel{
    private BasicDisplayer myListings;
    private BasicDisplayer myGroupbuys;
    private ClientBrowser clientBrowser;
    private ClientHomeDisplayer clienthome;
    
    private CardLayout cardLayout;
    private JPanel cardContainer;
    
    public static final String MY_LISTINGS = "MY_LISTINGS";
    public static final String MY_GROUPBUYS = "MY_GROUPBUYS";
    public static final String BROWSE_GROUPBUYS = "BROWSE_GROUPBUYS";
    public static final String HOME = "HOME";
    
    public ClientHomeDisplayer getClienthome() {
        return clienthome;
    }
    
    public BasicDisplayer getMyListings() {
        return myListings;
    }
    
    public BasicDisplayer getMyGroupbuys() {
        return myGroupbuys;
    }
    
    public CardLayout getCardLayout() {
        return cardLayout;
    }
    
    public ClientBrowser getClientBrowser() {
        return clientBrowser;
    }

    public JPanel getCardContainer() {
        return cardContainer;
    }

    public ClientContent(){
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        this.myListings = new BasicDisplayer(BasicDisplayer.MY_LISTINGS);
        this.myGroupbuys = new BasicDisplayer(BasicDisplayer.MY_GROUPBUYS);
        this.clientBrowser = new ClientBrowser();
        this.clienthome = new ClientHomeDisplayer();
        this.cardLayout = new CardLayout();
        this.cardContainer = new JPanel(cardLayout);

        cardContainer.add(clienthome, HOME);
        cardContainer.add(myListings, MY_LISTINGS);
        cardContainer.add(myGroupbuys, MY_GROUPBUYS);
        cardContainer.add(clientBrowser, BROWSE_GROUPBUYS);

        setLayout(new BorderLayout());

        add(cardContainer);

    }


}
