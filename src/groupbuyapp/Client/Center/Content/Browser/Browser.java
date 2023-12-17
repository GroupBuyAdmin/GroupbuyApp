package groupbuyapp.Client.Center.Content.Browser;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.Center.Content.Content;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.Misc.Database.GbuyDatabase;
import groupbuyapp.Misc.Fonts.GbuyFont;
import groupbuyapp.Misc.Interface.Refreshable;


public class Browser extends JPanel implements Refreshable{
    User currentUser;
    ScrollablePanel scrollablePanel;
    
    JScrollPane scrollPane;
    JPanel cardContainer;
    CardLayout cardLayout;
    List<CategoryPanel> madeCategoryPanels;
    Content content;
    int fromWhere;
    
    public JPanel getCardContainer() {
        return cardContainer;
    }
    public CardLayout getCardLayout() {
        return cardLayout;
    }
    
    public ScrollablePanel getScrollablePanel() {
        return scrollablePanel;
    }

    public static final String BROWSE_LISTING = "browse listing";
    public static final String VIEW_BROWSED = "view browsed";
    public static final String SEE_ALL_LISTING = "see all listing";
    private static final String[] categories = {"Electronics", "Clothing", "Books", "Home and Kitchen", "Sports"};

    public static final int DEFAULT = 1;
    public static final int HOME_BROWSER = 2;

    public Browser(User currentUser, Content content, SideBar sideBar){
        this(DEFAULT, currentUser, content, sideBar);
    }


    public Browser(int fromWhere, User currentUser, Content content, SideBar sideBar){
        this.content = content;
        this.madeCategoryPanels = new ArrayList<>();
        this.currentUser = currentUser;
        this.fromWhere = fromWhere;

        scrollablePanel = new ScrollablePanel(new GridLayout(0, 1));
        scrollablePanel.setScrollableHeight(ScrollableSizeHint.NONE);
        scrollablePanel.setScrollableWidth(ScrollableSizeHint.FIT);
        scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);

        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);

        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);
        cardContainer.add(scrollPane, BROWSE_LISTING);

        populateBrowse(sideBar);
        setLayout(new BorderLayout());
        setBackground(GbuyColor.PANEL_COLOR);

        BrowserHeader header = new BrowserHeader();

        add(header, BorderLayout.NORTH);
        add(cardContainer, BorderLayout.CENTER);
    }

    public void populateBrowse(SideBar sideBar){
        for(String category : categories){
            if(GbuyDatabase.getInstance().checkForCategory(category, currentUser.getUserID())){   
                CategoryPanel categoryPanel = null;          //check if category has products
                if(fromWhere == DEFAULT){
                    categoryPanel = new CategoryPanel(category, Browser.this, currentUser, content, sideBar);
                } else {
                    categoryPanel = new CategoryPanel(CategoryPanel.HOME_BROWSER, category, Browser.this, currentUser, content, sideBar);
                }
                scrollablePanel.add(categoryPanel);
                madeCategoryPanels.add(categoryPanel);
            }
        }
    }

    @Override
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

    class BrowserHeader extends JPanel{

        public BrowserHeader(){
            JLabel label = new JLabel("Browse Listings");
            label.setFont(GbuyFont.MULI_BOLD.deriveFont(24f));
            label.setForeground(GbuyColor.MAIN_TEXT_COLOR);

            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            add(label, BorderLayout.WEST);

            setBackground(GbuyColor.PANEL_COLOR);


        }


    }
}
