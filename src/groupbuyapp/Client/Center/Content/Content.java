package groupbuyapp.Client.Center.Content;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import groupbuyapp.Client.Center.Content.Browser.Browser;
import groupbuyapp.Client.Center.Content.Controller.ContentController;
import groupbuyapp.Client.Center.Content.Controller.ListDisplayerFactory;
import groupbuyapp.Client.Center.Content.Home.Home;
import groupbuyapp.Client.Center.Content.ListingDisplayer.ListDisplayer;
import groupbuyapp.Client.Center.Content.ListingDisplayer.ListingDisplayer;
import groupbuyapp.Client.Center.Content.ProductContainers.ListDisplayerController;
import groupbuyapp.Client.Center.TopNavBar.TopNavBar;
import groupbuyapp.Client.LogIn.misc.User;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class Content extends JPanel{
    private User currentUser;
    private JPanel contentContainer;

    private Home home;

    private ListDisplayer newMyListing;
    private ListDisplayer newMyGroupbuys;


    private Browser browser;
    private TopNavBar topNavBar;
    private CardLayout layout;
    private ContentController contentController;

    public ContentController getContentController() {
        return contentController;
    }

    public CardLayout getLayout() {
        return layout;
    }

    public Home getHome() {
        return home;
    }
    
    public JPanel getContentContainer() {
        return contentContainer;
    }
    
    public ListDisplayer getMyListings() {
        return newMyListing;
    }
    
    public ListDisplayer getMyGroupbuys() {
        return newMyGroupbuys;
    }
    
    public Browser getBrowser() {
        return browser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public static final String HOME = "home";
    public static final String HOME_VIEW = "home view";
    public static final String MY_LISTINGS = "my listings";
    public static final String MY_GROUPBUYS = "my groupbuys";
    public static final String BROWSE_GROUPBUYS = "browse groupbuys";
    public static final String SEARCH = "search";

    public Content(User currentUser, SideBar sideBar, TopNavBar topNavBar){
        this.currentUser = currentUser;

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
        
        home = new Home(browser, currentUser, Content.this, sideBar);

        browser = new Browser(currentUser, Content.this, sideBar);
        
        ListDisplayerFactory l = new ListDisplayerFactory();
        newMyListing = l.create(ListDisplayerFactory.MY_LISTING_PANEL, currentUser);
        l = new ListDisplayerFactory();
        newMyGroupbuys = l.create(ListDisplayerFactory.MY_GROUPBUYS_PANEL, currentUser);


        contentContainer = new JPanel();
        layout = new CardLayout();

        contentContainer.setLayout(layout);
        contentContainer.add(home, HOME);
        contentContainer.add(newMyListing, MY_LISTINGS);
        contentContainer.add(newMyGroupbuys, MY_GROUPBUYS);
        contentContainer.add(browser, BROWSE_GROUPBUYS);

        setBackground(GbuyColor.PANEL_COLOR);
        setLayout(new BorderLayout());

        add(contentContainer, BorderLayout.CENTER);

        contentController = new ContentController(this, ContentController.IN_HOME);
        ListDisplayerController myListingController = new ListDisplayerController(newMyListing, currentUser);
        ListDisplayerController myGroupbuyController = new ListDisplayerController(newMyGroupbuys, currentUser);
    }
}
