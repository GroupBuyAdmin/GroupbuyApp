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
import groupbuyapp.Client.Center.Content.ProductContainers.ListingViewer;
import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Client.Center.Content.ProductContainers.ProductPanel;
import groupbuyapp.Client.LogIn.User;
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

    public CategoryPanel(String category, NewBrowser newBrowser, User currentUser, Content content){
        this.category = category;
        this.newBrowser = newBrowser;
        this.header = new Header();
        this.sideScrollPanel = new SideScrollPanel();
        this.currentUser = currentUser;
        this.content = content;

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(sideScrollPanel, BorderLayout.CENTER);

        refresh();
    }

    private void addToList(Product product, JPanel scrollablePanelRef){
        ProductPanel pPanel = new ProductPanel(product, ProductPanel.BROWSER_PANEL);
        pPanel.addMouseListener(new ContainerListener(pPanel, newBrowser, content));
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

        public ContainerListener(ProductPanel pPanel, NewBrowser newBrowser, Content content) {
            this.pPanel = pPanel;
            this.oldColor = pPanel.getDetailsContainer().getNameLabel().getForeground();
            this.newBrowser = newBrowser;
            this.content = content;
        }

        @Override
        public void mouseClicked(MouseEvent e){ 
            ListingViewer pView = new ListingViewer(pPanel.getProduct(), ListingViewer.FROM_BROWSE, newBrowser.currentUser, newBrowser, content);
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
    // void testPanel(){
    //     JFrame f = new JFrame();
    //     f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     f.add(this);
    //     f.pack();
    //     f.setVisible(true);
    // }

    // public static void main(String[] args) {
    //     CategoryPanel p = new CategoryPanel(null, null);
    //     p.testPanel();
    // }
}
