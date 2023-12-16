package groupbuyapp.Client.Center.Content.Browser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.Center.Content.Content;
import groupbuyapp.Client.Center.Content.ListingDisplayer.ListingDisplayer;
import groupbuyapp.Client.Center.Content.ProductContainers.ListingViewer;
import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Client.Center.Content.ProductContainers.ProductPanel;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Client.SideBar.Buttons.Buttons;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.Database.GbuyDatabase;
import groupbuyapp.Misc.Fonts.GbuyFont;
import groupbuyapp.Misc.Interface.Refreshable;

public class CategoryPanel extends JPanel implements Refreshable{
    String category;
    Header header;
    SideScrollPanel sideScrollPanel;
    Browser newBrowser;
    User currentUser;
    Content content;
    SideBar sideBar;

    int fromWhere;


    public static final int HOME_MY_LISTING = 1;
    public static final int HOME_MY_GROUPBUYS = 2;
    public static final int HOME_BROWSER = 3;
    public static final int JUST_CATEGORY = 4;

    public CategoryPanel(String category, Browser newBrowser, User currentUser, Content content, SideBar sideBar){
        this(JUST_CATEGORY, category, newBrowser, currentUser, content, sideBar);
    }

    public CategoryPanel(int fromWhere, String category, Browser newBrowser, User currentUser, Content content, SideBar sideBar){
        this.currentUser = currentUser;
        this.content = content;
        this.sideBar = sideBar;
        this.fromWhere = fromWhere;

        this.category = category;
        this.newBrowser = newBrowser;
        this.header = new Header(content, sideBar);
        this.sideScrollPanel = new SideScrollPanel();


        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(sideScrollPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        setBackground(GbuyColor.PANEL_COLOR);
        refresh();
    }

    private void addToList(Product product, JPanel scrollablePanelRef){
         ProductPanel pPanel = null;
        if(fromWhere == JUST_CATEGORY){
            pPanel = new ProductPanel(product, ProductPanel.BROWSER_PANEL);
        } else {
            pPanel = new ProductPanel(product);
        }

        if(fromWhere == JUST_CATEGORY){
            pPanel.addMouseListener(new ContainerListener(pPanel, newBrowser, content, sideBar));
        } else {
            pPanel.addMouseListener(new HomeContainerListener(pPanel, content, this.sideBar, fromWhere));
        }
        scrollablePanelRef.add(pPanel);
    }

    public void refresh(){
        List<Product> dbProducts = null;
        if(fromWhere == JUST_CATEGORY){
            dbProducts = GbuyDatabase.getInstance().getCategorizedProducts(category, currentUser.getUserID());
        } else if( fromWhere == HOME_MY_LISTING){
            dbProducts = GbuyDatabase.getInstance().getMyListings(currentUser);
        } else if(fromWhere == HOME_MY_GROUPBUYS){
            dbProducts = GbuyDatabase.getInstance().getMyGroupbuys(currentUser);
        }

        sideScrollPanel.scrollablePanel.removeAll();
        
        for(Product p : dbProducts){
            addToList(p, sideScrollPanel.scrollablePanel);
        }
        
        sideScrollPanel.scrollablePanel.revalidate();
        sideScrollPanel.scrollablePanel.repaint();
    }
    
    class Header extends JPanel{
        JLabel categoryName;
        JLabel seeAll;
        Content content;
        SideBar sideBar;

        public Header(Content content, SideBar sideBar){
            this.content = content;
            this.sideBar = sideBar;
            setOpaque(false);
            setLayout(new BorderLayout());

            categoryName = new JLabel(category);
            if(fromWhere == JUST_CATEGORY){
                categoryName.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            } else {
                categoryName.setFont(GbuyFont.MULI_BOLD.deriveFont(24f));
            }
            categoryName.setForeground(GbuyColor.MAIN_COLOR);

            seeAll = new JLabel("See All");
            seeAll.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));

            if(fromWhere == JUST_CATEGORY){
                seeAll.addMouseListener(new SeeAllListener(seeAll, category, content, sideBar, newBrowser));
            } else {
                seeAll.addMouseListener(new HomeSeeAllListener(categoryName, content, sideBar, fromWhere));
            }

            categoryName.setHorizontalAlignment(JLabel.LEADING);
            seeAll.setHorizontalAlignment(JLabel.TRAILING);

            add(categoryName, BorderLayout.WEST);
            add(seeAll, BorderLayout.EAST);
        }
    }

    class SideScrollPanel extends JPanel{
        JPanel scrollablePanel;
        JScrollPane scrollpane;

        public SideScrollPanel(){
            setOpaque(false);

            this.scrollablePanel = new JPanel();
            scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);
            scrollablePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            this.scrollpane = new JScrollPane(scrollablePanel);
            scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollpane.getHorizontalScrollBar().setUnitIncrement(16);
            scrollpane.setBorder(BorderFactory.createEmptyBorder());
            setLayout(new BorderLayout());
            add(scrollpane);
        }
    }
    
    private static class ContainerListener extends MouseAdapter {
        private final ProductPanel pPanel;
        private final Color oldColor;
        private Browser newBrowser;
        private Content content;
        private SideBar sideBar;

        public ContainerListener(ProductPanel pPanel, Browser newBrowser, Content content, SideBar sideBar) {
            this.pPanel = pPanel;
            this.oldColor = pPanel.getDetailsContainer().getNameLabel().getForeground();
            this.newBrowser = newBrowser;
            this.content = content;
            this.sideBar = sideBar;
        }

        @Override
        public void mouseClicked(MouseEvent e){ 
            ListingViewer pView = new ListingViewer(pPanel.getProduct(), ListingViewer.FROM_BROWSE, newBrowser.currentUser, newBrowser, content, sideBar);
            pView.getBackButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newBrowser.cardLayout.show(newBrowser.cardContainer, Browser.BROWSE_LISTING);
                }
            });

            newBrowser.cardContainer.add(pView, Browser.VIEW_BROWSED);
            newBrowser.revalidate();
            newBrowser.repaint();
            newBrowser.cardLayout.show(newBrowser.cardContainer, Browser.VIEW_BROWSED);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            pPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pPanel.getDetailsContainer().getNameLabel().setForeground(GbuyColor.MAIN_COLOR);
            pPanel.revalidate();
            pPanel.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            pPanel.getDetailsContainer().getNameLabel().setForeground(oldColor);
            pPanel.setCursor(Cursor.getDefaultCursor());
            pPanel.revalidate();
            pPanel.repaint();
        }
    }

    private static class SeeAllListener extends MouseAdapter{
        private final Color oldColor;
        private final JLabel label;
        private final String category;
        private Content content;
        private SideBar sideBar;
        private Browser newBrowser;

        public SeeAllListener(JLabel label, String category, Content content, SideBar sideBar, Browser newBrowser){
            this.oldColor = label.getForeground();
            this.label = label;
            this.category = category;
            this.content = content;
            this.sideBar = sideBar;
            this.newBrowser = newBrowser;
        }

        @Override
        public void mouseClicked(MouseEvent e){
            ListingDisplayer lDisplayer = new ListingDisplayer(newBrowser.currentUser, ListingDisplayer.SEE_ALL_PANEL, content, sideBar, category);
            
            lDisplayer.getHeaderButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newBrowser.cardLayout.show(newBrowser.cardContainer, Browser.BROWSE_LISTING);
                }
                
            });
            
            newBrowser.cardContainer.add(lDisplayer, Browser.SEE_ALL_LISTING);
            newBrowser.revalidate();
            newBrowser.repaint();
            newBrowser.cardLayout.show(newBrowser.cardContainer, Browser.SEE_ALL_LISTING);
            
        }

        @Override
        public void mouseEntered(MouseEvent e){
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.setForeground(GbuyColor.MAIN_COLOR);
            label.revalidate();
            label.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e){
            label.setCursor(Cursor.getDefaultCursor());
            label.setForeground(oldColor);
            label.revalidate();
            label.repaint();
        }
    }
    private class HomeContainerListener extends MouseAdapter{
        private final ProductPanel pPanel;
        private Color oldColor;
        private Content content;
        private int fromWhere;

        public HomeContainerListener(ProductPanel pPanel, Content content, SideBar sideBar, int fromWhere){
            this.pPanel = pPanel;
            this.content = content;
            this.fromWhere = fromWhere;
        }

        public void mouseClicked(MouseEvent e){
            ListingViewer pView = new ListingViewer(pPanel.getProduct(),fromWhere ,content.getCurrentUser() , content);
            
            pView.getBackButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    content.getHome().refresh();
                    content.getLayout().show(content.getContentContainer(), Content.HOME);
                }
                
            });

            content.getContentContainer().add(pView, Content.HOME_VIEW);
            content.revalidate();
            content.repaint();
            content.getLayout().show(content.getContentContainer(), Content.HOME_VIEW);
        }

        public void mouseEntered(MouseEvent e){
            pPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pPanel.getDetailsContainer().getNameLabel().setForeground(GbuyColor.MAIN_COLOR);
            pPanel.revalidate();
            pPanel.repaint();
        }

        public void mouseExited(MouseEvent e){
            pPanel.getDetailsContainer().getNameLabel().setForeground(oldColor);
            pPanel.setCursor(Cursor.getDefaultCursor());
            pPanel.revalidate();
            pPanel.repaint();
        }
    }

    private static class HomeSeeAllListener extends MouseAdapter{
        private final Color oldColor;
        private final JLabel label;
        private Content content;
        private SideBar sideBar;
        private String destination;
        private int buttonSet;

        public HomeSeeAllListener(JLabel label, Content content, SideBar sideBar, int fromWhere){
            this.oldColor = label.getForeground();
            this.label = label;
            this.content = content;
            this.sideBar = sideBar;
            this.destination = "";
            this.buttonSet = -1;

            switch (fromWhere) {
                case HOME_MY_LISTING:
                    destination = Content.MY_LISTINGS;
                    buttonSet = Buttons.MY_LISTINGS;
                    break;
            
                case HOME_MY_GROUPBUYS:
                    destination = Content.MY_GROUPBUYS;
                    buttonSet = Buttons.MY_GROUPBUYS;
                    break;
            
                default:
                    break;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e){
            sideBar.getButtons().setSelected(buttonSet);
            content.getLayout().show(content.getContentContainer(), destination);
        }

        @Override
        public void mouseEntered(MouseEvent e){
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.setForeground(GbuyColor.MAIN_COLOR);
            label.revalidate();
            label.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e){
            label.setCursor(Cursor.getDefaultCursor());
            label.setForeground(oldColor);
            label.revalidate();
            label.repaint();
        }
        
    }
}
