package groupbuyapp.Client.Center.Content.BrowseGroupbuys.newBrowserImplementation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.Database.GbuyDatabase;
import groupbuyapp.Misc.Interface.Refreshable;

public class CategoryPanel extends JPanel implements Refreshable{
    String category;
    Header header;
    SideScrollPanel sideScrollPanel;
    NewBrowser newBrowser;
    User currentUser;
    Content content;
    SideBar sideBar;

    public CategoryPanel(String category, NewBrowser newBrowser, User currentUser, Content content, SideBar sideBar){
        this.category = category;
        this.newBrowser = newBrowser;
        this.header = new Header();
        this.sideScrollPanel = new SideScrollPanel();
        this.currentUser = currentUser;
        this.content = content;
        this.sideBar = sideBar;

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(sideScrollPanel, BorderLayout.CENTER);

        refresh();
    }

    private void addToList(Product product, JPanel scrollablePanelRef){
        ProductPanel pPanel = new ProductPanel(product, ProductPanel.BROWSER_PANEL);
        pPanel.addMouseListener(new ContainerListener(pPanel, newBrowser, content, sideBar));
        scrollablePanelRef.add(pPanel);
    }

    public void refresh(){
        List<Product> dbProducts = GbuyDatabase.getInstance().getCategorizedProducts(category, currentUser.getUserID());
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

        public Header(){
            setOpaque(false);
            setLayout(new BorderLayout());

            categoryName = new JLabel(category);
            seeAll = new JLabel("See All");
            seeAll.addMouseListener(new SeeAllListener(seeAll, category));

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
            scrollablePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            this.scrollpane = new JScrollPane(scrollablePanel);
            scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollpane.getHorizontalScrollBar().setUnitIncrement(16);
            setLayout(new BorderLayout());
            add(scrollpane);
        }
    }
    
    private static class ContainerListener extends MouseAdapter {
        private final ProductPanel pPanel;
        private final Color oldColor;
        private NewBrowser newBrowser;
        private Content content;
        private SideBar sideBar;

        public ContainerListener(ProductPanel pPanel, NewBrowser newBrowser, Content content, SideBar sideBar) {
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
                    newBrowser.cardLayout.show(newBrowser.cardContainer, NewBrowser.BROWSE_LISTING);
                }
            });

            newBrowser.cardContainer.add(pView, NewBrowser.VIEW_BROWSED);
            newBrowser.revalidate();
            newBrowser.repaint();
            newBrowser.cardLayout.show(newBrowser.cardContainer, NewBrowser.VIEW_BROWSED);
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

        public SeeAllListener(JLabel label, String category){
            this.oldColor = label.getForeground();
            this.label = label;
            this.category = category;
        }

        @Override
        public void mouseClicked(MouseEvent e){
            ListingDisplayer lDisplayer = new ListingDisplayer(null, ABORT, null, null);
            //to pass in 
            //category
            
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
