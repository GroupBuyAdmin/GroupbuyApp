package groupbuyapp.Client.Center.Content;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import groupbuyapp.Client.Center.Content.BrowseGroupbuys.BrowseGroupbuys;
import groupbuyapp.Client.Center.Content.BrowseGroupbuys.newBrowserImplementation.NewBrowser;
import groupbuyapp.Client.Center.Content.Home.Home;
import groupbuyapp.Client.Center.Content.ListingDisplayer.ListingDisplayer;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class Content extends JPanel{
    private User currentUser;


    private JPanel contentContainer;

    
    private Home home;
    private ListingDisplayer myListings;
    private ListingDisplayer myGroupbuys;
    private BrowseGroupbuys browseGroupbuys;
    private CardLayout layout;

    private NewBrowser n;
    
    private int currentPanel;
    
    private static final int IN_HOME = 1;
    private static final int IN_MY_LISTINGS = 2;
    private static final int IN_MY_GROUPBUYS = 3;
    private static final int IN_BROWSE_GROUPBUYS = 4;
    
    public Home getHome() {
        return home;
    }
    
    public JPanel getContentContainer() {
        return contentContainer;
    }
    
    public ListingDisplayer getMyListings() {
        return myListings;
    }
    
    public ListingDisplayer getMyGroupbuys() {
        return myGroupbuys;
    }
    
    public BrowseGroupbuys getBrowseGroupbuys() {
        return browseGroupbuys;
    }

    public NewBrowser getNewBrowser() {
        return n;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private static final String HOME = "home";
    private static final String MY_LISTINGS = "my listings";
    private static final String MY_GROUPBUYS = "my groupbuys";
    private static final String BROWSE_GROUPBUYS = "browse groupbuys";

    public Content(User currentUser, SideBar sideBar){
        home = new Home();
        myListings = new ListingDisplayer(currentUser, ListingDisplayer.MY_LISTING_PANEL, Content.this, sideBar);
        myGroupbuys = new ListingDisplayer(currentUser, ListingDisplayer.MY_GROUPBUYS_PANEL, Content.this, sideBar);
        browseGroupbuys = new BrowseGroupbuys();
        n = new NewBrowser(currentUser, Content.this, sideBar);

        contentContainer = new JPanel();
        layout = new CardLayout();

        contentContainer.setLayout(layout);
        contentContainer.add(home, HOME);
        contentContainer.add(myListings, MY_LISTINGS);
        contentContainer.add(myGroupbuys, MY_GROUPBUYS);
        contentContainer.add(n, BROWSE_GROUPBUYS);

        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        add(contentContainer, BorderLayout.CENTER);

        this.currentPanel = IN_HOME;
    }

    public void showHome(){
        Current_Panel_Is(IN_HOME);

        layout.show(contentContainer, HOME);
    }

    public void showMyListings(){
        if(Current_Panel_Is(IN_MY_LISTINGS)){
            myListings.refresh();
        }

        layout.show(contentContainer, MY_LISTINGS);
    }

    public void showMyGroupBuys(){
        if(Current_Panel_Is(IN_MY_GROUPBUYS)){
            myGroupbuys.refresh();
        }

        layout.show(contentContainer, MY_GROUPBUYS);
    }

    public void showBrowseGroupbuys(){
        if(Current_Panel_Is(IN_BROWSE_GROUPBUYS)){
            n.refresh();
        }
        n.showBrowserPage();
        layout.show(contentContainer, BROWSE_GROUPBUYS);
    }

    private boolean Current_Panel_Is(int desiredPanel){         //when a sidebar button is clicked twice, the current panel will refresh   
        if(currentPanel == desiredPanel){
            return true;
        } else {
            currentPanel = desiredPanel;
            return false;
        }
    }
    
}
