package groupbuyapp.Client.Center.Content.BrowseGroupbuys.newBrowserImplementation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import groupbuyapp.Client.Center.Content.ProductContainers.ListingViewer;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel.ScrollableSizeHint;


public class NewBrowser extends JPanel{
    User currentUser;
    ScrollablePanel scrollablePanel;
    JScrollPane scrollPane;

    ListingViewer listingViewer;

    JPanel cardContainer;

    CardLayout cardLayout;

    public static final String BROWSE_LISTING = "browse listing";
    public static final String VIEW_BROWSED = "view browsed";

    static final String[] categories = {"Electronics", "Clothing", "Books", "Home and Kitchen", "Sports"};

    public NewBrowser(){
        this(null);
    }

    public NewBrowser(User currentUser){
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

        //testing
        // for(int i = 0; i < 7; i++){
        //     CategoryPanel c = new CategoryPanel(NewBrowser.this, currentUser);
        //     scrollablePanel.add(c);
        // }

        for(String category : categories){
            CategoryPanel categoryPanel = new CategoryPanel(category, NewBrowser.this, currentUser);
            scrollablePanel.add(categoryPanel);
        }

        setLayout(new BorderLayout());
        add(cardContainer);
    }


  
    void testPanel(){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) {
        NewBrowser n = new NewBrowser();
        n.testPanel();
    }
}
