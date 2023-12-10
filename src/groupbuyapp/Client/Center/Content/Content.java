package groupbuyapp.Client.Center.Content;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import groupbuyapp.Client.Center.Content.BrowseGroupbuys.BrowseGroupbuys;
import groupbuyapp.Client.Center.Content.Home.Home;
import groupbuyapp.Client.Center.Content.MyGroupbuys.MyGroupbuys;
import groupbuyapp.Client.Center.Content.MyListings.MyListings;
import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class Content extends JPanel{
    private JPanel contentContainer;

    
    private Home home;
    private MyListings myListings;
    private MyGroupbuys myGroupbuys;
    private BrowseGroupbuys browseGroupbuys;
    private CardLayout layout;

    public Home getHome() {
        return home;
    }

    public JPanel getContentContainer() {
        return contentContainer;
    }

    public MyListings getMyListings() {
        return myListings;
    }

    public MyGroupbuys getMyGroupbuys() {
        return myGroupbuys;
    }

    public BrowseGroupbuys getBrowseGroupbuys() {
        return browseGroupbuys;
    }

    private static final String HOME = "home";
    private static final String MY_LISTINGS = "my listings";
    private static final String MY_GROUPBUYS = "my groupbuys";
    private static final String BROWSE_GROUPBUYS = "browse groupbuys";

    public Content(){
        home = new Home();
        myListings = new MyListings();
        myGroupbuys = new MyGroupbuys();
        browseGroupbuys = new BrowseGroupbuys();

        contentContainer = new JPanel();
        layout = new CardLayout();

        contentContainer.setLayout(layout);
        contentContainer.add(home, HOME);
        contentContainer.add(myListings, MY_LISTINGS);
        contentContainer.add(myGroupbuys, MY_GROUPBUYS);
        contentContainer.add(browseGroupbuys, BROWSE_GROUPBUYS);

        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        add(contentContainer, BorderLayout.CENTER);
    }

    public void showHome(){
        layout.show(contentContainer, HOME);
    }


    public void showMyListings(){
        // myListings.updateListings();
        layout.show(contentContainer, MY_LISTINGS);
    }

    public void showMyGroupBuys(){
        layout.show(contentContainer, MY_GROUPBUYS);
    }

    public void showBrowseGroupbuys(){
        layout.show(contentContainer, BROWSE_GROUPBUYS);
    }


}
