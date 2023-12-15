package groupbuyapp.Client.Center.Content;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import groupbuyapp.Client.Center.Content.Browser.Browser;
import groupbuyapp.Client.Center.Content.Home.Home;
import groupbuyapp.Client.Center.Content.ListingDisplayer.ListingDisplayer;
import groupbuyapp.Client.Center.TopNavBar.TopNavBar;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class Content extends JPanel{
    private User currentUser;


    private JPanel contentContainer;

    
    private Home home;
    private ListingDisplayer myListings;
    private ListingDisplayer myGroupbuys;
    private CardLayout layout;

    private Browser browser;
    
    private int currentPanel;

    private TopNavBar topNavBar;
    
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
    
    public Browser getNewBrowser() {
        return browser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private static final String HOME = "home";
    private static final String MY_LISTINGS = "my listings";
    private static final String MY_GROUPBUYS = "my groupbuys";
    private static final String BROWSE_GROUPBUYS = "browse groupbuys";
    private static final String SEARCH = "search";

    public Content(User currentUser, SideBar sideBar, TopNavBar topNavBar){
        
        this.topNavBar = topNavBar;
        var searchButtonref = this.topNavBar.getSearchBar().getSearchButton();
        searchButtonref.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchItem = topNavBar.getSearchBar().getSearchedItem();
                System.out.println(searchItem);
                ListingDisplayer searchedDisplayer = new ListingDisplayer(currentUser, ListingDisplayer.SEARCH_PANEL, Content.this, sideBar, null, searchItem);
                contentContainer.add(searchedDisplayer, SEARCH);
                searchedDisplayer.refresh();
                layout.show(contentContainer, SEARCH);
            }
        });
        
        home = new Home();
        myListings = new ListingDisplayer(currentUser, ListingDisplayer.MY_LISTING_PANEL, Content.this, sideBar);
        myGroupbuys = new ListingDisplayer(currentUser, ListingDisplayer.MY_GROUPBUYS_PANEL, Content.this, sideBar);
        browser = new Browser(currentUser, Content.this, sideBar);

        contentContainer = new JPanel();
        layout = new CardLayout();

        contentContainer.setLayout(layout);
        contentContainer.add(home, HOME);
        contentContainer.add(myListings, MY_LISTINGS);
        contentContainer.add(myGroupbuys, MY_GROUPBUYS);
        contentContainer.add(browser, BROWSE_GROUPBUYS);

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
        myGroupbuys.refresh();
        layout.show(contentContainer, MY_GROUPBUYS);
    }

    public void showBrowseGroupbuys(){
        if(Current_Panel_Is(IN_BROWSE_GROUPBUYS)){
            browser.refresh();
        }
        browser.refresh();
        browser.showBrowserPage();
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
