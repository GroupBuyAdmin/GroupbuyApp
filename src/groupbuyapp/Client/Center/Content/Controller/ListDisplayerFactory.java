package groupbuyapp.Client.Center.Content.Controller;

import javax.swing.ImageIcon;

import groupbuyapp.Client.Center.Content.ListingDisplayer.ListDisplayer;
import groupbuyapp.Client.LogIn.misc.User;
import groupbuyapp.Misc.Database.GbuyDatabase;

public class ListDisplayerFactory {
    public static final int MY_LISTING_PANEL = 1;
    public static final int MY_GROUPBUYS_PANEL = 2;
    public static final int SEE_ALL_PANEL = 3;
    public static final int SEARCH_PANEL = 4;

    private static String iconPath = "src/groupbuyapp/Client/Center/Content/ListingDisplayer/img/plus.app.fill.png";

    private ListDisplayer createdDisplayer;

    public ListDisplayer create(int type, User currentUser){
        return create(type, currentUser, null, null);
    }

    public ListDisplayer create(int type, User currentUser, String search){
        return create(type, currentUser, search, null);
    }

    public ListDisplayer create(int type, String category, User currentUser){
        return create(type, currentUser, null, category);
    }

    public ListDisplayer create(int type, User currentUser, String search, String category){
        createdDisplayer = new ListDisplayer();
        switch (type) {
            case MY_LISTING_PANEL:
                return myListing(currentUser);
        
            case MY_GROUPBUYS_PANEL:
                return myGroupbuy(currentUser);
        
            case SEE_ALL_PANEL:
                return browseAll(category, currentUser);
        
            case SEARCH_PANEL:
                return search(search, currentUser);
        
            default:
                return null;
        }
    }

    private ListDisplayer myListing(User currentUser){
        createdDisplayer.setContentName("My Listings");
        createdDisplayer.getHeaderButton().setText("Create Listing");
        createdDisplayer.getHeaderButton().setIcon(new ImageIcon(iconPath));
        createdDisplayer.setDbProducts(GbuyDatabase.getInstance().getMyListings(currentUser));
        return createdDisplayer;
    }

    private ListDisplayer myGroupbuy(User currentUser){
        createdDisplayer.setContentName("My Groupbuys");
        createdDisplayer.setDbProducts(GbuyDatabase.getInstance().getMyGroupbuys(currentUser));
        createdDisplayer.getHeaderButton().setText("Browse Groupbuys");
        createdDisplayer.getHeaderButton().setIcon(new ImageIcon(iconPath));
        return createdDisplayer;
    }

    private ListDisplayer browseAll(String category, User currentUser){
        createdDisplayer.setContentName("Browse all " + category);
        createdDisplayer.setDbProducts(GbuyDatabase.getInstance().getCategorizedProducts(category, currentUser.getUserID()));
        return createdDisplayer;
    }

    private ListDisplayer search(String search, User currentUser){
        createdDisplayer.setContentName("Search \"" + search + "\"");
        createdDisplayer.setDbProducts(GbuyDatabase.getInstance().getSearchedItem(search, currentUser.getUserID()));
        return createdDisplayer;
    }


}
