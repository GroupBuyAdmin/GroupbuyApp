package groupbuyapp.Client.Center.Content.BrowseGroupbuys.newBrowserImplementation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.Center.Content.Content;
import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.Misc.Database.GbuyDatabase;
import groupbuyapp.Misc.Interface.Refreshable;


public class NewBrowser extends JPanel implements Refreshable{
    User currentUser;
    ScrollablePanel scrollablePanel;
    JScrollPane scrollPane;
    JPanel cardContainer;
    CardLayout cardLayout;
    List<CategoryPanel> madeCategoryPanels;
    Content content;
    
    public JPanel getCardContainer() {
        return cardContainer;
    }
    public CardLayout getCardLayout() {
        return cardLayout;
    }



    Product lastSelected;

    public static final String BROWSE_LISTING = "browse listing";
    public static final String VIEW_BROWSED = "view browsed";

    static final String[] categories = {"Electronics", "Clothing", "Books", "Home and Kitchen", "Sports"};


    public NewBrowser(User currentUser, Content content, SideBar sideBar){
        this.content = content;
        this.madeCategoryPanels = new ArrayList<>();
        this.currentUser = currentUser;
        scrollablePanel = new ScrollablePanel(new GridLayout(0, 1));
        scrollablePanel.setScrollableHeight(ScrollableSizeHint.NONE);
        scrollablePanel.setScrollableWidth(ScrollableSizeHint.FIT);
        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);
        cardContainer.add(scrollPane, BROWSE_LISTING);

        populateBrowse(sideBar);
        setLayout(new BorderLayout());
        add(cardContainer);
    }

    public void populateBrowse(SideBar sideBar){
        for(String category : categories){
            if(GbuyDatabase.getInstance().checkForCategory(category, currentUser.getUserID())){             //check if category has products
                CategoryPanel categoryPanel = new CategoryPanel(category, NewBrowser.this, currentUser, content, sideBar);
                scrollablePanel.add(categoryPanel);
                madeCategoryPanels.add(categoryPanel);
            }
        }
    }

    public void refresh(){
        for(CategoryPanel c : madeCategoryPanels){
            c.refresh();
        }
        
        revalidate();
        repaint();
    }

    public void showBrowserPage(){
        cardLayout.show(cardContainer, BROWSE_LISTING);
    }
}
