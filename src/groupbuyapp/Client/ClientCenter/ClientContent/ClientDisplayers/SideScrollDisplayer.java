package groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.ClientCenter.ClientContent.ClientContainers.ProductPanel;
import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;
import groupbuyapp.SystemFiles.Interface.Refreshable;

public class SideScrollDisplayer extends JPanel implements Refreshable{
    private Header header;
    private SideScrollPanel sideScrollPanel;
    
    private int fromWhere;

    private List<ProductPanel> allProductsPanels;


    public List<ProductPanel> getAllProductsPanels() {
        return allProductsPanels;
    }

    public void setAllProductsPanels(List<ProductPanel> allProductsPanels) {
        this.allProductsPanels = allProductsPanels;
        refresh();
    }

    public JLabel getHeaderName(){
        return header.getHeaderName();
    }

    public JLabel getSeeAll(){
        return header.getSeeAll();
    }

    public JPanel getScrollablePanel(){
        return sideScrollPanel.getScrollablePanel();
    }

    public static final int HOME_MY_LISTING = 1;
    public static final int HOME_MY_GROUPBUYS = 2;
    public static final int HOME_BROWSER = 3;
    public static final int JUST_CATEGORY = 4;

    public SideScrollDisplayer(int fromWhere, String headerName){
        this.allProductsPanels = new ArrayList<>();
        this.fromWhere = fromWhere;

        this.header = new Header(headerName);
        this.sideScrollPanel = new SideScrollPanel();

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(sideScrollPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        setBackground(GbuyColor.PANEL_COLOR);
    }

    public void refresh(){
        sideScrollPanel.getScrollablePanel().removeAll();
        
        for(ProductPanel p : allProductsPanels){
            sideScrollPanel.getScrollablePanel().add(p);
        }
        
        sideScrollPanel.getScrollablePanel().revalidate();
        sideScrollPanel.getScrollablePanel().repaint();
    }
    
    public class Header extends JPanel{
        private JLabel headerName;
        private JLabel seeAll;

        public JLabel getHeaderName() {
            return headerName;
        }

        public JLabel getSeeAll() {
            return seeAll;
        }

        public Header(String name){
 
            setOpaque(false);
            setLayout(new BorderLayout());

            headerName = new JLabel(name);
            if(fromWhere == JUST_CATEGORY){
                headerName.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            } else {
                headerName.setFont(GbuyFont.MULI_BOLD.deriveFont(24f));
            }
            headerName.setForeground(GbuyColor.MAIN_COLOR);

            seeAll = new JLabel("See All");
            seeAll.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));

            // if(fromWhere == JUST_CATEGORY){
            //     seeAll.addMouseListener(new SeeAllListener(seeAll, category, content, sideBar, newBrowser));
            // } else {
            //     seeAll.addMouseListener(new HomeSeeAllListener(headerName, content, sideBar, fromWhere));
            // }

            headerName.setHorizontalAlignment(JLabel.LEADING);
            seeAll.setHorizontalAlignment(JLabel.TRAILING);

            add(headerName, BorderLayout.WEST);
            add(seeAll, BorderLayout.EAST);
        }
    }

    public class SideScrollPanel extends JPanel{
        private JPanel scrollablePanel;
        private JScrollPane scrollpane;

        public JPanel getScrollablePanel() {
            return scrollablePanel;
        }

        public JScrollPane getScrollpane() {
            return scrollpane;
        }

        public SideScrollPanel(){
            setOpaque(false);

            this.scrollablePanel = new JPanel();
            scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);
            scrollablePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            this.scrollpane = new JScrollPane(scrollablePanel);
            scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollpane.getHorizontalScrollBar().setUnitIncrement(8);
            scrollpane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
            scrollpane.setBorder(BorderFactory.createEmptyBorder());
            setLayout(new BorderLayout());
            add(scrollpane);
        }
    }
    
    // private static class ContainerListener extends MouseAdapter {
    //     private final ProductPanel pPanel;
    //     private final Color oldColor;
    //     private SideBar sideBar;

    //     public ContainerListener(ProductPanel pPanel, Browser newBrowser, Content content, SideBar sideBar) {
    //         this.pPanel = pPanel;
    //         this.oldColor = pPanel.getDetailsContainer().getNameLabel().getForeground();
    //         this.newBrowser = newBrowser;
    //         this.content = content;
    //         this.sideBar = sideBar;
    //     }

    //     @Override
    //     public void mouseClicked(MouseEvent e){ 
    //         ListingViewer pView = new ListingViewer(pPanel.getProduct(), ListingViewer.FROM_BROWSE, newBrowser.currentUser, newBrowser, content, sideBar);
    //         pView.getBackButton().addActionListener(new ActionListener() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                 newBrowser.cardLayout.show(newBrowser.cardContainer, Browser.BROWSE_LISTING);
    //             }
    //         });

    //         newBrowser.cardContainer.add(pView, Browser.VIEW_BROWSED);
    //         newBrowser.revalidate();
    //         newBrowser.repaint();
    //         newBrowser.cardLayout.show(newBrowser.cardContainer, Browser.VIEW_BROWSED);
    //     }

    //     @Override
    //     public void mouseEntered(MouseEvent e) {
    //         pPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    //         pPanel.getDetailsContainer().getNameLabel().setForeground(GbuyColor.MAIN_COLOR);
    //         pPanel.revalidate();
    //         pPanel.repaint();
    //     }

    //     @Override
    //     public void mouseExited(MouseEvent e) {
    //         pPanel.getDetailsContainer().getNameLabel().setForeground(oldColor);
    //         pPanel.setCursor(Cursor.getDefaultCursor());
    //         pPanel.revalidate();
    //         pPanel.repaint();
    //     }
    // }

    // private static class SeeAllListener extends MouseAdapter{
    //     private final Color oldColor;
    //     private final JLabel label;
    //     private final String category;
    //     private Content content;
    //     private SideBar sideBar;
    //     private Browser newBrowser;

    //     public SeeAllListener(JLabel label, String category, Content content, SideBar sideBar, Browser newBrowser){
    //         this.oldColor = label.getForeground();
    //         this.label = label;
    //         this.category = category;
    //         this.content = content;
    //         this.sideBar = sideBar;
    //         this.newBrowser = newBrowser;
    //     }

    //     @Override
    //     public void mouseClicked(MouseEvent e){
    //         ListingDisplayer lDisplayer = new ListingDisplayer(newBrowser.currentUser, ListingDisplayer.SEE_ALL_PANEL, content, sideBar, category);
            
    //         lDisplayer.getHeaderButton().addActionListener(new ActionListener() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                 newBrowser.cardLayout.show(newBrowser.cardContainer, Browser.BROWSE_LISTING);
    //             }
                
    //         });
            
    //         newBrowser.cardContainer.add(lDisplayer, Browser.SEE_ALL_LISTING);
    //         newBrowser.revalidate();
    //         newBrowser.repaint();
    //         newBrowser.cardLayout.show(newBrowser.cardContainer, Browser.SEE_ALL_LISTING);
            
    //     }

    //     @Override
    //     public void mouseEntered(MouseEvent e){
    //         label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    //         label.setForeground(GbuyColor.MAIN_COLOR);
    //         label.revalidate();
    //         label.repaint();
    //     }

    //     @Override
    //     public void mouseExited(MouseEvent e){
    //         label.setCursor(Cursor.getDefaultCursor());
    //         label.setForeground(oldColor);
    //         label.revalidate();
    //         label.repaint();
    //     }
    // }
    // private class HomeContainerListener extends MouseAdapter{
    //     private final ProductPanel pPanel;
    //     private Color oldColor;
    //     private Content content;
    //     private int fromWhere;

    //     public HomeContainerListener(ProductPanel pPanel, Content content, SideBar sideBar, int fromWhere){
    //         this.pPanel = pPanel;
    //         this.content = content;
    //         this.fromWhere = fromWhere;
    //     }

    //     public void mouseClicked(MouseEvent e){
    //         ListingViewer pView = new ListingViewer(pPanel.getProduct(), fromWhere ,content.getCurrentUser() , content);

    //         pView.getEditButton().addActionListener(new ActionListener() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                new ListingCreator(content.getHome(), pPanel.getProduct(), currentUser);
    //                content.getLayout().show(content.getContentContainer(), Content.HOME);
    //             }
                
    //         });

    //         pView.getBackButton().addActionListener(new ActionListener() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                 content.getHome().refresh();
    //                 content.getLayout().show(content.getContentContainer(), Content.HOME);
    //             }
                
    //         });

    //         content.getContentContainer().add(pView, Content.HOME_VIEW);
    //         content.revalidate();
    //         content.repaint();
    //         content.getLayout().show(content.getContentContainer(), Content.HOME_VIEW);
    //     }

    //     public void mouseEntered(MouseEvent e){
    //         pPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    //         pPanel.getDetailsContainer().getNameLabel().setForeground(GbuyColor.MAIN_COLOR);
    //         pPanel.revalidate();
    //         pPanel.repaint();
    //     }

    //     public void mouseExited(MouseEvent e){
    //         pPanel.getDetailsContainer().getNameLabel().setForeground(oldColor);
    //         pPanel.setCursor(Cursor.getDefaultCursor());
    //         pPanel.revalidate();
    //         pPanel.repaint();
    //     }
    // }

    // private static class HomeSeeAllListener extends MouseAdapter{
    //     private final Color oldColor;
    //     private final JLabel label;
    //     private Content content;
    //     private SideBar sideBar;
    //     private String destination;
    //     private int buttonSet;

    //     public HomeSeeAllListener(JLabel label, Content content, SideBar sideBar, int fromWhere){
    //         this.oldColor = label.getForeground();
    //         this.label = label;
    //         this.content = content;
    //         this.sideBar = sideBar;
    //         this.destination = "";
    //         this.buttonSet = -1;

    //         switch (fromWhere) {
    //             case HOME_MY_LISTING:
    //                 destination = Content.MY_LISTINGS;
    //                 buttonSet = Buttons.MY_LISTINGS;
    //                 break;
            
    //             case HOME_MY_GROUPBUYS:
    //                 destination = Content.MY_GROUPBUYS;
    //                 buttonSet = Buttons.MY_GROUPBUYS;
    //                 break;
            
    //             default:
    //                 break;
    //         }
    //     }

    //     @Override
    //     public void mouseClicked(MouseEvent e){
    //         sideBar.getButtons().setSelected(buttonSet);
    //         content.getLayout().show(content.getContentContainer(), destination);
    //     }

    //     @Override
    //     public void mouseEntered(MouseEvent e){
    //         label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    //         label.setForeground(GbuyColor.MAIN_COLOR);
    //         label.revalidate();
    //         label.repaint();
    //     }

    //     @Override
    //     public void mouseExited(MouseEvent e){
    //         label.setCursor(Cursor.getDefaultCursor());
    //         label.setForeground(oldColor);
    //         label.revalidate();
    //         label.repaint();
    //     }
        
    // }
}
