package groupbuyapp.Client.Center.Content.MyListings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.Center.Content.ProductContainers.ListingCreator;
import groupbuyapp.Client.Center.Content.ProductContainers.ListingViewer;
import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Client.Center.Content.ProductContainers.ProductPanelSmall;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundedButton;
import groupbuyapp.Misc.CustomComponents.RoundedPanel;
import groupbuyapp.Misc.Database.GbuyDatabase;
import groupbuyapp.Misc.Fonts.GbuyFont;
import net.miginfocom.swing.MigLayout;

/**
 * The <code>MyListings</code> class is a JPanel that displays a list of products. 
 * It uses a {@code CardLayout} to switch between the list view and the product view.
 * The class also provides methods to update the list of products and handle user interactions.
 */
public class MyListings extends JPanel {
    public static final String MY_LISTING = "my listing";
    public static final String PRODUCT_VIEW = "product view";
    private MyListingPanel myListingPanel;
    private List<ProductPanelSmall> allContainers;
    private ListingViewer productView;
    private User currentUser;

    private CardLayout cLayout;
    private JPanel cardContainer;

    public JPanel getCardContainer() {
        return cardContainer;
    }

    public CardLayout getcLayout() {
        return cLayout;
    }

    public ListingViewer getProductView() {
        return productView;
    }

    public List<ProductPanelSmall> getAllContainers() {
        return allContainers;
    }

    public MyListings(User currentUser) {
        this.currentUser = currentUser;
        this.allContainers = new ArrayList<>();

        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        myListingPanel = new MyListingPanel();

        cLayout = new CardLayout();
        cardContainer = new JPanel(new BorderLayout());
        cardContainer.setOpaque(false);
        cardContainer.setLayout(cLayout);
        cardContainer.add(myListingPanel, MY_LISTING);

        add(cardContainer, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 40));
        updateListings();

        var createButtonRef = myListingPanel.getMyListingHeader().getCreateListingButton();
        createButtonRef.addActionListener(e -> new ListingCreator(MyListings.this, currentUser));
    }
    
    public void updateListings() {
        List<Product> dbProducts = GbuyDatabase.getInstance().getMyListings(currentUser);
        var scrollablePaneRef = myListingPanel.getMyListingScrollable().getScrollablePanel();
        scrollablePaneRef.removeAll();
        allContainers.clear();
    
        for (Product p : dbProducts) {
            addToListing(p, scrollablePaneRef);
        }
    
        scrollablePaneRef.revalidate();
        scrollablePaneRef.repaint();
    }

    private void addToListing(Product product, JPanel scrollablePaneRef) {
        ProductPanelSmall pSmall = new ProductPanelSmall(product);
        pSmall.addMouseListener(new ContainerListener(pSmall, MyListings.this));
        allContainers.add(pSmall);
    
        JPanel p = new JPanel(new FlowLayout());
        p.setOpaque(false);
        p.add(pSmall);
        scrollablePaneRef.add(p);
    }
    
    class MyListingPanel extends RoundedPanel{
        private MyListingHeader myListingHeader;
        private MyListingScrollable myListingScrollable;

        public MyListingHeader getMyListingHeader() {
            return myListingHeader;
        }

        public MyListingScrollable getMyListingScrollable() {
            return myListingScrollable;
        }

        public MyListingPanel(){
            myListingHeader = new MyListingHeader();
            myListingScrollable = new MyListingScrollable();
            setLayout(new BorderLayout(20, 20));
            add(myListingHeader, BorderLayout.NORTH);
            add(myListingScrollable, BorderLayout.CENTER);
            setBackground(GbuyColor.PANEL_COLOR);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
            setShady(false);
            setArcs(new Dimension(20, 20));
        }
    }

    class MyListingHeader extends JPanel{
        private static String iconPath = "src/groupbuyapp/Client/Center/Content/MyListings/img/plus.app.fill.png";
        private JLabel contentName;

        private RoundedButton createListingButton;

        public JLabel getContentName() {
            return contentName;
        }

        public RoundedButton getCreateListingButton() {
            return createListingButton;
        }

        public MyListingHeader(){
            contentName = new JLabel("My Listings");
            contentName.setFont(GbuyFont.MULI_BOLD.deriveFont(32f));
            
            createListingButton = new RoundedButton("Create Listing", new ImageIcon(iconPath));
            createListingButton.setPreferredSize(new Dimension(200, 45));
            createListingButton.setButtonColor(GbuyColor.MAIN_COLOR);
            createListingButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            createListingButton.setDrawBorder(false);
            createListingButton.setButtonFont(GbuyFont.MULI_LIGHT.deriveFont(16f));
            createListingButton.setIconTextGap(20);
            createListingButton.setCornerRadius(10);

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.LINE_START;
            add(contentName, gbc);

            gbc.gridx++;
            gbc.anchor = GridBagConstraints.LINE_END;
            add(createListingButton, gbc);

            setBackground(GbuyColor.PANEL_COLOR);

        }

    }

    class MyListingScrollable extends JPanel{
        private JScrollPane scrollPane;
        private JPanel scrollablePanel;

        public JScrollPane getScrollPane() {
            return scrollPane;
        }

        public JPanel getScrollablePanel() {
            return scrollablePanel;
        }

        public MyListingScrollable(){
            scrollablePanel = new JPanel();
            scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);
            scrollPane = new JScrollPane(scrollablePanel);
            scrollPane.setBackground(GbuyColor.PANEL_COLOR);
            scrollPane.setPreferredSize(getSize());
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            scrollablePanel.setLayout(new MigLayout("wrap 5"));                   //mig layout pre kayata kani ra nga linya mu grid na sya
            
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.CENTER);
            setBackground(Color.pink);
        }
        
        public void addDummyContainers(){
            for(int i = 0; i < 13; i++){
                scrollablePanel.add(new ProductPanelSmall());
            }
        }
    }

    private static class ContainerListener extends MouseAdapter {
        private final ProductPanelSmall pSmall;
        private final Color oldColor;
        private MyListings myListings;

        public ContainerListener(ProductPanelSmall pSmall, MyListings myListings) {
            this.pSmall = pSmall;
            this.oldColor = pSmall.getDetailsContainer().getNameLabel().getForeground();
            this.myListings = myListings;
        }

        @Override
        public void mouseClicked(MouseEvent e){ 
            ListingViewer pView = new ListingViewer(pSmall.getProduct(), myListings.currentUser);
            pView.getBackButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    myListings.getcLayout().show(myListings.getCardContainer(), MyListings.MY_LISTING);
                }
            });

            pView.getEditButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ListingCreator(myListings, pSmall.getProduct(), myListings.currentUser);
                    myListings.updateListings();
                    myListings.getcLayout().show(myListings.getCardContainer(), MyListings.MY_LISTING);
                }
            });
            // myListings.updateListings();
            myListings.getCardContainer().add(pView, MyListings.PRODUCT_VIEW);
            myListings.revalidate();
            myListings.repaint();
            myListings.getcLayout().show(myListings.getCardContainer(), MyListings.PRODUCT_VIEW);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            pSmall.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pSmall.getDetailsContainer().getNameLabel().setForeground(GbuyColor.MAIN_COLOR);
            pSmall.revalidate();
            pSmall.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            pSmall.getDetailsContainer().getNameLabel().setForeground(oldColor);
            pSmall.setCursor(Cursor.getDefaultCursor());
            pSmall.revalidate();
            pSmall.repaint();
        }
    }

}