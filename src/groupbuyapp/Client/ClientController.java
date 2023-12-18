package groupbuyapp.Client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import groupbuyapp.Client.ClientCenter.ClientCenter;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientContent;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientContainers.ProductPanel;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers.CategoryDisplayer;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers.ClientBrowser;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers.ClientListingViewer;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers.SideScrollDisplayer;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientPopUp.ClientListingCreator;
import groupbuyapp.Client.ClientCenter.ClientContent.ClientPopUp.CreatorController;
import groupbuyapp.Client.ClientSidebar.ClientSidebar;
import groupbuyapp.Client.ClientSidebar.ClientSidebar.ClientButtons;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.RoundedToggleButton;
import groupbuyapp.SystemFiles.Database.GbuyDatabase;
import groupbuyapp.SystemFiles.Database.Product;
import groupbuyapp.SystemFiles.Database.SingleProductContainer;

public class ClientController {
    private ClientSidebar clientSidebar;
    private ClientCenter clientCenter;
    private ClientFrame clientFrame;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public ClientSidebar getClientSidebar() {
        return clientSidebar;
    }

    public ClientCenter getClientCenter() {
        return clientCenter;
    }

    public ClientFrame getClientFrame() {
        return clientFrame;
    }

    public ClientController(ClientSidebar clientSidebar, ClientCenter clientCenter, ClientFrame clientFrame, User currentUser) {
        this.clientSidebar = clientSidebar;
        this.clientCenter = clientCenter;
        this.clientFrame = clientFrame;
        this.currentUser = currentUser;
    }

    public void init(){
        initSidebarControls();
        initNavBarControls();
    }

    private void initNavBarControls(){
        var searchButton = clientCenter.getClientNavBar().getSearchButton();
        var profileIcon = clientCenter.getClientNavBar().getProfileIcon();
        var signOutIcon = clientCenter.getClientNavBar().getSignOutIcon();

        searchButton.addActionListener(new Search());
        profileIcon.setToolTipText(currentUser.getUserName());
        signOutIcon.addMouseListener(new SignOutAction());

    }

    private class SignOutAction extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            int option = JOptionPane.showConfirmDialog(
                null, // parent component (null for default)
                "You are about to be sign out", // message
                "Confirmation", // title
                JOptionPane.YES_NO_OPTION); // option type

            // Check the user's choice
            if (option == JOptionPane.YES_OPTION) {
                clientFrame.dispose();
            } 
        }
    }

    private class Search implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String searchedItem = clientCenter.getClientNavBar().getSearchedItem();

            CategoryDisplayer categoryDisplayer = new CategoryDisplayer();
            categoryDisplayer.getHeaderName().setText("Search \"" + searchedItem + "\"");

            categoryDisplayer.setProductPanels(createSearchedPanels(searchedItem));

            categoryDisplayer.revalidate();
            categoryDisplayer.repaint();

            categoryDisplayer.getBackLabel().addMouseListener(new ReturnToHome());

            var content = clientCenter.getClientContent();

            content.getCardContainer().add(categoryDisplayer, "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }

    private class ReturnToHome extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            refreshHome();
            var content = clientCenter.getClientContent();

            content.getCardLayout().show(content.getCardContainer(), ClientContent.HOME);
        }
    }

    private List<ProductPanel> createSearchedPanels(String searchedItem){
        List<Product> searchedProducts = GbuyDatabase.getInstance().getSearchedItem(searchedItem, currentUser.getUserID());
        List<ProductPanel> madePanels = new ArrayList<>();

        for(Product product : searchedProducts){
            ProductPanel productPanel = new ProductPanel(product);
            productPanel.addMouseListener(new CreateViewerForSearch(product));
            madePanels.add(productPanel);
        }
        return madePanels;
    }

    private class CreateViewerForSearch extends MouseAdapter{
        private Product product;
        public CreateViewerForSearch(Product product){
            this.product = product;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();

            content.getCardContainer().add(createViewerForSearched(product), "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }

    private ClientListingViewer createViewerForSearched(Product product){
        var content = clientCenter.getClientContent();
        ClientListingViewer viewer = new ClientListingViewer(product, false, ClientListingViewer.FROM_MY_LISTING);
        viewer.getCreatorLabel().setText("Creator: " + currentUser.getUserName());
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        viewer.getCountLabel().setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));
        String formattedTime = formatTimestamp(product.getDeadlineStamp());
        viewer.getDeadlineLabel().setText(formattedTime);
        
        viewer.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshHome();
                content.getCardLayout().show(content.getCardContainer(), ClientContent.HOME);
            }
        });

        viewer.revalidate();
        viewer.repaint();

        return viewer;

    }

    private void initSidebarControls(){
        var homeButton = clientSidebar.getHomeButton();
        var myListingsButton = clientSidebar.getMyListingsButton();
        var myGroupbuysButton = clientSidebar.getMyGroupbuysButton();
        var browseGroupbuysButton = clientSidebar.getBrowseGroupbuysButton();

        var content = clientCenter.getClientContent();
        content.getMyListings().getHeaderButton().addActionListener(new EnableListingCreator());
        content.getMyGroupbuys().getHeaderButton().addActionListener(new JumpToBrowseGroupbuys());

        refreshHome();

        homeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshHome();
                content.getCardLayout().show(content.getCardContainer(), ClientContent.HOME);
                clientSidebar.getClientButtons().setSelected(ClientButtons.HOME);
            }
            
        });

        myListingsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshMyListings(content);
                content.getCardLayout().show(content.getCardContainer(), ClientContent.MY_LISTINGS);
                clientSidebar.getClientButtons().setSelected(ClientButtons.MY_LISTINGS);
            }
        });

        myGroupbuysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshMyGroupbuys(content);
                content.getCardLayout().show(content.getCardContainer(), ClientContent.MY_GROUPBUYS);
                clientSidebar.getClientButtons().setSelected(ClientButtons.MY_GROUPBUYS);
            }
        });

        browseGroupbuysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshBrowse(content);
                content.getCardLayout().show(content.getCardContainer(), ClientContent.BROWSE_GROUPBUYS);
                clientSidebar.getClientButtons().setSelected(ClientButtons.BROWSE_GROUPBUYS);
            }


            
        });
    }

    //refresh Browser
    private void refreshBrowse(ClientContent content) {
        content.getClientBrowser().setMadeSideScrollables(createScrollablesForBrowser());
        content.getClientBrowser().revalidate();
        content.getClientBrowser().refresh();
    }

    //refresh MyGroupbuys
    private void refreshMyGroupbuys(ClientContent content) {
        content.getMyGroupbuys().setProductPanels(createMyGroupbuysPanels());
        content.getMyListings().revalidate();
        content.getMyGroupbuys().repaint();
    }
            
    //refresh mylistings
    private void refreshMyListings(ClientContent content) {
        content.getMyListings().setProductPanels(createMyListingPanels());
        content.getMyListings().revalidate();
        content.getMyGroupbuys().repaint();
    }

    //refreshhome
    private void refreshHome(){
        var content = clientCenter.getClientContent();
        var myListing = content.getClienthome().getMyListingScroller();
        var myGroupbuy = content.getClienthome().getMyGroupbuysScroller();
        var minibrowser = content.getClienthome().getMiniBrowser();

        var retreivedHomeListingPanels = createProductPanelsForHomeListing();
        if(retreivedHomeListingPanels.isEmpty()){
            content.getClienthome().getMyListingPanel().setVisible(false);
        } else{
            content.getClienthome().getMyListingPanel().setVisible(true);
            myListing.setAllProductsPanels(retreivedHomeListingPanels);
            myListing.getSeeAll().addMouseListener(new JumptoMyListing());
        }
        myListing.revalidate();
        myListing.repaint();

        var retrievedMyGroupbuyPanels = createProductPanelsForHomeGroupbuys();
        if(retrievedMyGroupbuyPanels.isEmpty()){
            content.getClienthome().getMyGroupbuysPanel().setVisible(false);
        } else {
            content.getClienthome().getMyGroupbuysPanel().setVisible(true);
            myGroupbuy.setAllProductsPanels(retrievedMyGroupbuyPanels);
            myGroupbuy.getSeeAll().addMouseListener(new JumptoMyGroupbuys());
        }
        myGroupbuy.revalidate();
        myGroupbuy.repaint();

        minibrowser.setMadeSideScrollables(createScrollablesForBrowser());
        minibrowser.getScrollPane().getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        minibrowser.revalidate();
        minibrowser.repaint();
    }


    //controls for home
    private List<ProductPanel> createProductPanelsForHomeListing(){
        List<Product> myListings = GbuyDatabase.getInstance().getMyListings(currentUser);

        List<ProductPanel> madePanels = new ArrayList<>();

        for(Product product : myListings){
            ProductPanel productPanel = new ProductPanel(product);
            productPanel.addMouseListener(new HomeMyListingViewer(product));
            madePanels.add(productPanel);
        }
        
        return madePanels;
    }

    class HomeMyListingViewer extends MouseAdapter{
        private Product product;

        HomeMyListingViewer(Product product){
            this.product = product;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();

            content.getCardContainer().add(createViewerForHomeListing(product), "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }


    private ClientListingViewer createViewerForHomeListing(Product product){
        var content = clientCenter.getClientContent();
        ClientListingViewer viewer = new ClientListingViewer(product, true, ClientListingViewer.FROM_MY_LISTING);
        viewer.getCreatorLabel().setText("Creator: " + currentUser.getUserName());
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        viewer.getCountLabel().setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));
        String formattedTime = formatTimestamp(product.getDeadlineStamp());
        viewer.getDeadlineLabel().setText(formattedTime);
        
        viewer.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshHome();
                content.getCardLayout().show(content.getCardContainer(), ClientContent.HOME);
            }
        });

        viewer.getEditButton().addActionListener(new EnableEditListing(product, viewer));

        viewer.revalidate();
        viewer.repaint();

        return viewer;
    }

    public class JumptoMyListing extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();
            content.getMyListings().setProductPanels(createMyListingPanels());
            content.getMyListings().revalidate();
            content.getMyListings().repaint();
            content.getCardLayout().show(content.getCardContainer(), ClientContent.MY_LISTINGS);
            clientSidebar.getClientButtons().setSelected(ClientButtons.MY_LISTINGS);
        }
    }

    public List<ProductPanel> createProductPanelsForHomeGroupbuys(){
        List<Product> myListings = GbuyDatabase.getInstance().getMyGroupbuys(currentUser);

        List<ProductPanel> madePanels = new ArrayList<>();

        for(Product product : myListings){
            ProductPanel productPanel = new ProductPanel(product);
            productPanel.addMouseListener(new HomeGroupbuysView(product));
            madePanels.add(productPanel);
        }
        
        return madePanels;
    }

    public class HomeGroupbuysView extends MouseAdapter{
        private Product product;

        HomeGroupbuysView(Product product){
            this.product = product;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();

            content.getCardContainer().add(createViewerForHomeGroupbuys(product), "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }

    private ClientListingViewer createViewerForHomeGroupbuys(Product product){
        var content = clientCenter.getClientContent();
        ClientListingViewer viewer = new ClientListingViewer(product, true, ClientListingViewer.FROM_MY_GROUPBUYS);
        viewer.getCreatorLabel().setText("Creator: " + GbuyDatabase.getInstance().getUserName(product.getCreatorID()));
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        viewer.getCountLabel().setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));
        String formattedTime = formatTimestamp(product.getDeadlineStamp());
        viewer.getDeadlineLabel().setText(formattedTime);

        viewer.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshHome();
                content.getCardLayout().show(content.getCardContainer(), ClientContent.HOME);
            }
        });

        viewer.getToggleJoinButton().addActionListener(new ToggleJoin(product, viewer.getToggleJoinButton(), viewer.getCountLabel()));

        viewer.revalidate();
        viewer.repaint();

        return viewer;
    }

    public class JumptoMyGroupbuys extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();
            content.getMyGroupbuys().setProductPanels(createMyGroupbuysPanels());
            content.getMyGroupbuys().revalidate();
            content.getMyGroupbuys().repaint();
            content.getCardLayout().show(content.getCardContainer(), ClientContent.MY_GROUPBUYS);
            clientSidebar.getClientButtons().setSelected(ClientButtons.MY_GROUPBUYS);
        }
    }


    public List<SideScrollDisplayer> createScrollablesForBrowser(){
        final String[] categories = {"Electronics", "Clothing", "Books", "Home and Kitchen", "Sports"};

        List<SideScrollDisplayer> madeScrollables = new ArrayList<>();

        for(String category : categories){
            if(GbuyDatabase.getInstance().checkForCategory(category, currentUser.getUserID())){
                SideScrollDisplayer scrollable = new SideScrollDisplayer(SideScrollDisplayer.JUST_CATEGORY, category);
                scrollable.setAllProductsPanels(createLargeProductPanels(category));
                scrollable.getSeeAll().addMouseListener(new EnableSeeAll(category));
                madeScrollables.add(scrollable);
            }
        }

        return madeScrollables;
    }

    class JumpToBrowseGroupbuys implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            var content = clientCenter.getClientContent();
            content.getClientBrowser().setMadeSideScrollables(createScrollablesForBrowser());
            content.getClientBrowser().revalidate();
            content.getClientBrowser().refresh();
            content.getCardLayout().show(content.getCardContainer(), ClientContent.BROWSE_GROUPBUYS);
            clientSidebar.getClientButtons().setSelected(ClientButtons.BROWSE_GROUPBUYS);
        }
        
    }

    class EnableSeeAll extends MouseAdapter{
        private String category;

        public EnableSeeAll(String category){
            this.category = category;
        }

        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();
            content.getCardContainer().add(createCategoryDisplayer(category), "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
            clientSidebar.getClientButtons().setSelected(ClientButtons.BROWSE_GROUPBUYS);
        }
    }

    private CategoryDisplayer createCategoryDisplayer(String category){
        CategoryDisplayer catDisplayer = new CategoryDisplayer();
        catDisplayer.getHeaderName().setText(category);
        catDisplayer.setProductPanels(createCategorizedPanels(category));
        catDisplayer.getBackLabel().addMouseListener(new ReturnToBrowser());
        catDisplayer.revalidate();
        catDisplayer.repaint();
        return catDisplayer;
    }

    class ReturnToBrowser extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();
            
            ClientBrowser cBrowser = new ClientBrowser();
            cBrowser.setMadeSideScrollables(createScrollablesForBrowser());
            cBrowser.revalidate();
            cBrowser.repaint();
            content.getCardContainer().add(cBrowser, "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }

    private List<ProductPanel> createCategorizedPanels(String category){
        List<ProductPanel> productPanels = new ArrayList<>();

        List<Product> categorizedProducts = GbuyDatabase.getInstance().getCategorizedProducts(category, currentUser.getUserID());

        for(Product product : categorizedProducts){
            ProductPanel p = new ProductPanel(product);
            p.addMouseListener(new EnableListingViewerInSeeAll(product, category));
            productPanels.add(p);
        }

        return productPanels;
    }
    
    class EnableListingViewerInSeeAll extends MouseAdapter{
        private Product product;
        private String category;

        public EnableListingViewerInSeeAll(Product product, String category){
            this.product = product;
            this.category = category;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();
            content.getCardContainer().add(createViewerForSeeAll(product, category), "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }

    public ClientListingViewer createViewerForSeeAll(Product product, String category){
        var content = clientCenter.getClientContent();
        ClientListingViewer viewer = new ClientListingViewer(product, true, ClientListingViewer.FROM_MY_GROUPBUYS);
        viewer.getCreatorLabel().setText("Creator: " + GbuyDatabase.getInstance().getUserName(product.getCreatorID()));
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        viewer.getCountLabel().setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));
        String formattedTime = formatTimestamp(product.getDeadlineStamp());
        viewer.getDeadlineLabel().setText(formattedTime);

        viewer.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.getCardContainer().add(createCategoryDisplayer(category), "view");
                content.getCardLayout().show(content.getCardContainer(), "view");
            }
        });

        viewer.getToggleJoinButton().addActionListener(new ToggleJoin(product, viewer.getToggleJoinButton(), viewer.getCountLabel()));

        viewer.revalidate();
        viewer.repaint();

        return viewer;
    }

    private List<ProductPanel> createLargeProductPanels(String category){
        List<ProductPanel> madePanels = new ArrayList<>();
        List<Product> categorizedProducts = GbuyDatabase.getInstance().getCategorizedProducts(category, currentUser.getUserID());

        for(Product product : categorizedProducts){
            ProductPanel productPanel = new ProductPanel(product, ProductPanel.BROWSER_PANEL);
            productPanel.addMouseListener(new EnableListingViewerInBrowser(product));
            madePanels.add(productPanel);
        }

        return madePanels;
    }


    class EnableListingViewerInBrowser extends MouseAdapter{
        private Product product;

        public EnableListingViewerInBrowser(Product product){
            this.product = product;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();
            content.getCardContainer().add(createViewerForBrowser(product), "view");
            clientSidebar.getClientButtons().setSelected(ClientButtons.BROWSE_GROUPBUYS);
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }
    
    public ClientListingViewer createViewerForBrowser(Product product){
        var content = clientCenter.getClientContent();
        ClientListingViewer viewer = new ClientListingViewer(product, true, ClientListingViewer.FROM_MY_GROUPBUYS);
        viewer.getCreatorLabel().setText("Creator: " + GbuyDatabase.getInstance().getUserName(product.getCreatorID()));
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        viewer.getCountLabel().setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));
        String formattedTime = formatTimestamp(product.getDeadlineStamp());
        viewer.getDeadlineLabel().setText(formattedTime);

        viewer.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.getClientBrowser().setMadeSideScrollables(createScrollablesForBrowser());
                content.revalidate();
                content.repaint();
                content.getCardLayout().show(content.getCardContainer(), ClientContent.BROWSE_GROUPBUYS);
            }
        });

        viewer.getToggleJoinButton().addActionListener(new ToggleJoin(product, viewer.getToggleJoinButton(), viewer.getCountLabel()));

        viewer.revalidate();
        viewer.repaint();

        return viewer;
    }

    public List<ProductPanel> createMyListingPanels(){
        List<Product> products = GbuyDatabase.getInstance().getMyListings(currentUser);
        List<ProductPanel> productPanels = new ArrayList<>();

        for(Product product : products){
            ProductPanel p = new ProductPanel(product);
            p.addMouseListener(new EnableListingViewerInMyListings(product));
            productPanels.add(p);
        }

        return productPanels;
        
    }

    private List<ProductPanel> createMyGroupbuysPanels(){
        List<Product> products = GbuyDatabase.getInstance().getMyGroupbuys(currentUser);
        List<ProductPanel> productPanels = new ArrayList<>();

        for(Product product : products){
            ProductPanel p = new ProductPanel(product);
            p.addMouseListener(new EnableListingViewerInMyGroupbuys(product));
            productPanels.add(p);
        }

        return productPanels;
        
    }

    class EnableListingCreator implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ClientListingCreator c = new ClientListingCreator();
                    CreatorController controller = new CreatorController(c, currentUser, ClientController.this);
                    controller.init();
                }
                
            });
        }
        
    }

    class EnableListingViewerInMyListings extends MouseAdapter{
        private Product product;
        
        public EnableListingViewerInMyListings(Product product){
            this.product = product;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();
            content.getCardContainer().add(createViewerForMyListing(product), "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }

    public ClientListingViewer createViewerForMyListing(Product product){
        var content = clientCenter.getClientContent();
        ClientListingViewer viewer = new ClientListingViewer(product, true, ClientListingViewer.FROM_MY_LISTING);
        viewer.getCreatorLabel().setText("Creator: " + currentUser.getUserName());
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        viewer.getCountLabel().setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));
        String formattedTime = formatTimestamp(product.getDeadlineStamp());
        viewer.getDeadlineLabel().setText(formattedTime);
        
        viewer.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.getCardLayout().show(content.getCardContainer(), ClientContent.MY_LISTINGS);
            }
        });

        viewer.getEditButton().addActionListener(new EnableEditListing(product, viewer));

        viewer.revalidate();
        viewer.repaint();

        return viewer;
    }

    public ClientListingViewer createViewerForMyGroupbuys(Product product){
        var content = clientCenter.getClientContent();
        ClientListingViewer viewer = new ClientListingViewer(product, true, ClientListingViewer.FROM_MY_GROUPBUYS);
        viewer.getCreatorLabel().setText("Creator: " + GbuyDatabase.getInstance().getUserName(product.getCreatorID()));
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        viewer.getCountLabel().setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));
        String formattedTime = formatTimestamp(product.getDeadlineStamp());
        viewer.getDeadlineLabel().setText(formattedTime);

        viewer.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.getMyGroupbuys().setProductPanels(createMyGroupbuysPanels());
                content.revalidate();
                content.repaint();
                content.getCardLayout().show(content.getCardContainer(), ClientContent.MY_GROUPBUYS);
            }
        });

        viewer.getToggleJoinButton().addActionListener(new ToggleJoin(product, viewer.getToggleJoinButton(), viewer.getCountLabel()));

        viewer.revalidate();
        viewer.repaint();

        return viewer;
    }

    class ToggleJoin implements ActionListener{
        private Product product;
        private RoundedToggleButton toggleButton;
        private JLabel countLabel;
        private boolean joined;

        public ToggleJoin(Product product, RoundedToggleButton toggleButton, JLabel countLabel){
            this.product = product;
            this.toggleButton = toggleButton;
            this.countLabel = countLabel;
            this.joined = GbuyDatabase.getInstance().alreadyJoined(product.getId(), currentUser.getUserID());
            
            if(joined){
                toggleButton.setText("Leave Groupbuy");
                toggleButton.setDefaultColor(GbuyColor.MAIN_COLOR);
                toggleButton.setPressedColor(GbuyColor.ONGOING_COLOR);
            } else {
                toggleButton.setText("Join Groubuy");
                toggleButton.setDefaultColor(GbuyColor.ONGOING_COLOR);
                toggleButton.setPressedColor(GbuyColor.MAIN_COLOR);
            }

            toggleButton.revalidate();
            toggleButton.repaint();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(joined){
                if(toggleButton.isSelected()){
                    GbuyDatabase.getInstance().deleteGroupbuy(product.getId(), currentUser.getUserID());
                    JOptionPane.showMessageDialog(null, "You Left this groupbuy");
                    toggleButton.setText("Join Groubuy");
            
                } else {
                    GbuyDatabase.getInstance().createGroupbuy(product.getId(), currentUser.getUserID());
                    JOptionPane.showMessageDialog(null, "You joined the groupbuy");
                    toggleButton.setText("Leave Groupbuy");
            
                }
            } else {
                if(toggleButton.isSelected()){
                    GbuyDatabase.getInstance().createGroupbuy(product.getId(), currentUser.getUserID());
                    JOptionPane.showMessageDialog(null, "You joined the groupbuy");
                    toggleButton.setText("Leave Groupbuy");

                    
                } else {
                    GbuyDatabase.getInstance().deleteGroupbuy(product.getId(), currentUser.getUserID());
                    JOptionPane.showMessageDialog(null, "You Left this groupbuy");
                    toggleButton.setText("Join Groubuy");

                }
            }
            updateCountLabel(countLabel);
        }

        private void updateCountLabel(JLabel label){
            SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
            label.setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));    

        }
        
    }

    public String formatTimestamp(Timestamp timestamp) {
        try {
            // Format the timestamp to a custom format
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy @h:mm a");
            String formattedDateTime = formatter.format(new Date(timestamp.getTime()));

            return formattedDateTime;
        } catch (IllegalArgumentException e) {
            // Handle formatting errors
            System.err.println("Error formatting timestamp: " + e.getMessage());
            return null;
        }
    }

    class EnableEditListing implements ActionListener{
        private Product product;
        private ClientListingViewer clientListingViewer;

        public EnableEditListing(Product product, ClientListingViewer clientListingViewer){
            this.product = product;
            this.clientListingViewer = clientListingViewer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clientSidebar.getClientButtons().setSelected(ClientButtons.MY_LISTINGS);
            ClientListingCreator c = new ClientListingCreator(product);
            CreatorController controller = new CreatorController(c, currentUser, ClientController.this, clientListingViewer);
            controller.init();
        }
        
    }

    class EnableListingViewerInMyGroupbuys extends MouseAdapter{
        private Product product;

        public EnableListingViewerInMyGroupbuys(Product product){
            this.product = product;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            var content = clientCenter.getClientContent();
            content.getCardContainer().add(createViewerForMyGroupbuys(product), "view");
            content.getCardLayout().show(content.getCardContainer(), "view");
        }
    }



}
