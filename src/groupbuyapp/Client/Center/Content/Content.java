package groupbuyapp.Client.Center.Content;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import groupbuyapp.Client.Center.Content.BrowseGroupbuys.BrowseGroupbuys;
import groupbuyapp.Client.Center.Content.Home.Home;
import groupbuyapp.Client.Center.Content.MyGroupbuys.MyGroupbuys;
import groupbuyapp.Client.Center.Content.MyListings.MyListings;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class Content extends JPanel{
    private User currentUser;


    private JPanel contentContainer;

    
    private Home home;
    private MyListings myListings;
    private MyGroupbuys myGroupbuys;
    private BrowseGroupbuys browseGroupbuys;
    private CardLayout layout;
    
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

    public MyListings getMyListings() {
        return myListings;
    }

    public MyGroupbuys getMyGroupbuys() {
        return myGroupbuys;
    }

    public BrowseGroupbuys getBrowseGroupbuys() {
        return browseGroupbuys;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private static final String HOME = "home";
    private static final String MY_LISTINGS = "my listings";
    private static final String MY_GROUPBUYS = "my groupbuys";
    private static final String BROWSE_GROUPBUYS = "browse groupbuys";

    public Content(User currentUser){
        home = new Home();
        myListings = new MyListings(currentUser);
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

        this.currentPanel = IN_HOME;
    }

    public void showHome(){
        check_If_Current_Panel_Is(IN_HOME);

        layout.show(contentContainer, HOME);
    }

    public void showMyListings(){
        check_If_Current_Panel_Is(IN_MY_LISTINGS);

        layout.show(contentContainer, MY_LISTINGS);
    }

    public void showMyGroupBuys(){
        check_If_Current_Panel_Is(IN_MY_GROUPBUYS);

        layout.show(contentContainer, MY_GROUPBUYS);
    }

    public void showBrowseGroupbuys(){
        check_If_Current_Panel_Is(IN_BROWSE_GROUPBUYS);

        layout.show(contentContainer, BROWSE_GROUPBUYS);
    }

    private void check_If_Current_Panel_Is(int desiredPanel){        
        if(currentPanel == desiredPanel){
            myListings.updateListings();
        } else {
            currentPanel = desiredPanel;
        }
    }
    
}
