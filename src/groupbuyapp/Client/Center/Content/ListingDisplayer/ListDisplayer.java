package groupbuyapp.Client.Center.Content.ListingDisplayer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
// import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.Center.Content.ProductContainers.ListingViewer;
import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Client.Center.Content.ProductContainers.ProductPanel;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundedButton;
import groupbuyapp.Misc.CustomComponents.RoundedPanel;
import groupbuyapp.Misc.Fonts.GbuyFont;
import groupbuyapp.Misc.Interface.Refreshable;
import net.miginfocom.swing.MigLayout;

/**
 * The <code>MyListings</code> class is a JPanel that displays a list of products. 
 * It uses a {@code CardLayout} to switch between the list view and the product view.
 * The class also provides methods to update the list of products and handle user interactions.
 */
public class ListDisplayer extends JPanel implements Refreshable{
    public static final int MY_LISTING_PANEL = 1;
    public static final int MY_GROUPBUYS_PANEL = 2;
    public static final int SEE_ALL_PANEL = 3;
    public static final int SEARCH_PANEL = 4;

    public static final String LIST_VIEW = "my listing";
    public static final String PRODUCT_VIEW = "product view";

    private MyListingPanel myListingPanel;
    private List<ProductPanel> allContainers;
    private ListingViewer productView;
    private int typeOfPanel;
    private CardLayout cLayout;
    private JPanel cardContainer;
    private List<Product> dbProducts;
    
    public List<Product> getDbProducts() {
        return dbProducts;
    }

    public void setDbProducts(List<Product> dbProducts) {
        this.dbProducts = dbProducts;
        refresh();
    }

    public JPanel getCardContainer() {
        return cardContainer;
    }
    
    public CardLayout getcLayout() {
        return cLayout;
    }
    
    public ListingViewer getProductView() {
        return productView;
    }
    
    public List<ProductPanel> getAllContainers() {
        return allContainers;
    }
    

    public JButton getHeaderButton(){
        return myListingPanel.getMyListingHeader().getHeaderButton();
    }

    public void setContentName(String contentName){
        myListingPanel.getMyListingHeader().getContentName().setText(contentName);
    }


    public ListDisplayer() {
        this.allContainers = new ArrayList<>();
        this.dbProducts = null;

        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        myListingPanel = new MyListingPanel();

        cLayout = new CardLayout();
        cardContainer = new JPanel(new BorderLayout());
        cardContainer.setOpaque(false);
        cardContainer.setLayout(cLayout);
        cardContainer.add(myListingPanel, LIST_VIEW);

        add(cardContainer, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 40));

        // if(typeOfPanel == MY_LISTING_PANEL){            
        //     var createButtonRef = myListingPanel.getMyListingHeader().getHeaderButton();
            // createButtonRef.addActionListener(e -> new ListingCreator(ListDisplayer.this, currentUser));
        // }
        // else if(typeOfPanel == MY_GROUPBUYS_PANEL){
        //     var browseButtonRef = myListingPanel.getMyListingHeader().getHeaderButton();
        //     browseButtonRef.addActionListener(new ActionListener() {
        //         @Override
        //         public void actionPerformed(ActionEvent e) {
        //             content.getMyGroupbuys().refresh();
        //             sideBar.getButtons().setSelected(Buttons.BROWSE_GROUPBUYS);
                    // content.showBrowseGroupbuys();
        //         }
        //     });
        // }
    }
    
    @Override
    public void refresh() {        
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
        ProductPanel pSmall = new ProductPanel(product);
        // pSmall.addMouseListener(new ContainerListener(pSmall, ListDisplayer.this, typeOfPanel, content));
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
        private JLabel contentName;

        private RoundedButton headerButton;

        public JLabel getContentName() {
            return contentName;
        }

        public RoundedButton getHeaderButton() {
            return headerButton;
        }

        public MyListingHeader(){
            this.contentName = new JLabel();
            this.headerButton = new RoundedButton();
            contentName.setFont(GbuyFont.MULI_BOLD.deriveFont(32f));
            if(typeOfPanel != SEARCH_PANEL){
                headerButton.setButtonColor(GbuyColor.MAIN_COLOR);
                headerButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
                headerButton.setDrawBorder(false);
                headerButton.setButtonFont(GbuyFont.MULI_LIGHT.deriveFont(16f));
                headerButton.setIconTextGap(20);
                headerButton.setCornerRadius(10);
            }
            setLayout(new BorderLayout());
            add(contentName, BorderLayout.WEST);

            if(typeOfPanel != SEARCH_PANEL){
                add(headerButton, BorderLayout.EAST);
            }

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
            scrollPane.getVerticalScrollBar().setUnitIncrement(8);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(8);

            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.CENTER);
            setBackground(Color.pink);
        }
        
        public void addDummyContainers(){
            for(int i = 0; i < 13; i++){
                scrollablePanel.add(new ProductPanel());
            }
        }
    }

    // private static class ContainerListener extends MouseAdapter {
    //     private final ProductPanel pSmall;
    //     private final Color oldColor;
    //     private ListDisplayer myListings;
    //     private int fromWhere;
    //     private Content content;

    //     public ContainerListener(ProductPanel pSmall, ListDisplayer myListings, int fromTypeOfPanel, Content content) {
    //         this.pSmall = pSmall;
    //         this.oldColor = pSmall.getDetailsContainer().getNameLabel().getForeground();
    //         this.myListings = myListings;
    //         this.content = content;

    //         //determine what type of listing viewer to create
    //         switch (fromTypeOfPanel) {
    //             case ListDisplayer.MY_LISTING_PANEL:
    //                 fromWhere = ListingViewer.FROM_MY_LISTING;
    //                 break;
    //             case ListDisplayer.MY_GROUPBUYS_PANEL:
    //                 fromWhere = ListingViewer.FROM_MY_GROUPBUYS;
    //                 break;
    //             case ListDisplayer.SEARCH_PANEL:
    //                 fromWhere = ListingViewer.FROM_SEARCH;
    //                 break;
    //             default:
    //                 fromWhere = -1;
    //                 break;
    //         }
    //     }

    //     @Override
    //     public void mouseClicked(MouseEvent e){ 
    //         ListingViewer pView = new ListingViewer(pSmall.getProduct(), fromWhere, myListings.currentUser, content);
    //         pView.getBackButton().addActionListener(new ActionListener() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                 myListings.refresh();
    //                 myListings.getcLayout().show(myListings.getCardContainer(), ListDisplayer.LIST_VIEW);
    //             }
    //         });

    //         pView.getEditButton().addActionListener(new ActionListener() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                 // new ListingCreator(myListings, pSmall.getProduct(), myListings.currentUser);
    //                 myListings.refresh();
    //                 myListings.getcLayout().show(myListings.getCardContainer(), ListDisplayer.LIST_VIEW);
    //             }
    //         });
    //         myListings.getCardContainer().add(pView, ListDisplayer.PRODUCT_VIEW);
    //         myListings.revalidate();
    //         myListings.repaint();
    //         myListings.getcLayout().show(myListings.getCardContainer(), ListDisplayer.PRODUCT_VIEW);
    //     }

    //     @Override
    //     public void mouseEntered(MouseEvent e) {
    //         pSmall.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    //         pSmall.getDetailsContainer().getNameLabel().setForeground(GbuyColor.MAIN_COLOR);
    //         pSmall.revalidate();
    //         pSmall.repaint();
    //     }

    //     @Override
    //     public void mouseExited(MouseEvent e) {
    //         pSmall.getDetailsContainer().getNameLabel().setForeground(oldColor);
    //         pSmall.setCursor(Cursor.getDefaultCursor());
    //         pSmall.revalidate();
    //         pSmall.repaint();
    //     }
    // }

}