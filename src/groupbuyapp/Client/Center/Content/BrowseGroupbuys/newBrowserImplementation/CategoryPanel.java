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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.Center.Content.ProductContainers.ListingViewer;
import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Client.Center.Content.ProductContainers.ProductPanel;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.Database.GbuyDatabase;

public class CategoryPanel extends JPanel{
    String category;
    Header header;
    SideScrollPanel sideScrollPanel;
    NewBrowser newBrowser;
    User currentUser;

    public CategoryPanel(String category, NewBrowser newBrowser, User currentUser){
        this.category = category;
        this.newBrowser = newBrowser;
        this.header = new Header();
        this.sideScrollPanel = new SideScrollPanel();
        this.currentUser = currentUser;

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(sideScrollPanel, BorderLayout.CENTER);
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
            scrollablePanel.setLayout(new FlowLayout());
            
            for(int i = 0; i < 10; i++){
                ProductPanel p = new ProductPanel(ProductPanel.BROWSER_PANEL);
                p.addMouseListener(new ContainerListener(p, newBrowser));
                scrollablePanel.add(p);
            }

            this.scrollpane = new JScrollPane(scrollablePanel);
            scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollpane.getHorizontalScrollBar().setUnitIncrement(16);
            setLayout(new BorderLayout());
            add(scrollpane);
        }
    }

    private List<Product> getProductCategory(String category){
        return GbuyDatabase.getInstance().getCategorizedProducts(category);
    }

    private void addToList(Product product, JPanel scrollablePanelRef){
        ProductPanel pPanel = new ProductPanel(product, ProductPanel.BROWSER_PANEL);
        pPanel.addMouseListener(new ContainerListener(pPanel, newBrowser));
        scrollablePanelRef.add(pPanel);
        scrollablePanelRef.revalidate();
        scrollablePanelRef.repaint();
    }
    
    private static class ContainerListener extends MouseAdapter {
        private final ProductPanel pPanel;
        private final Color oldColor;
        private NewBrowser newBrowser;

        public ContainerListener(ProductPanel pPanel, NewBrowser newBrowser) {
            this.pPanel = pPanel;
            this.oldColor = pPanel.getDetailsContainer().getNameLabel().getForeground();
            this.newBrowser = newBrowser;
        }

        @Override
        public void mouseClicked(MouseEvent e){ 
            ListingViewer pView = new ListingViewer(pPanel.getProduct(), ListingViewer.FROM_BROWSE , newBrowser.currentUser);
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
