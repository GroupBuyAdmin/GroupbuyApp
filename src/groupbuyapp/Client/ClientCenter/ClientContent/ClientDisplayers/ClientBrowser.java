package groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;


public class ClientBrowser extends JPanel{
    private ScrollablePanel scrollablePanel;
    private JScrollPane scrollPane;
    
    private JPanel cardContainer;
    private CardLayout cardLayout;
    private List<SideScrollDisplayer> madeSideScrollables;
    
    public List<SideScrollDisplayer> getMadeSideScrollables() {
        return madeSideScrollables;
    }

    public void setMadeSideScrollables(List<SideScrollDisplayer> madeSideScrollables) {
        this.madeSideScrollables = madeSideScrollables;
        refresh();
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

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

    public static final int DEFAULT = 1;
    public static final int HOME_BROWSER = 2;

    public ClientBrowser(){
        this.madeSideScrollables = new ArrayList<>();

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

        setLayout(new BorderLayout());
        setBackground(GbuyColor.PANEL_COLOR);

        BrowserHeader header = new BrowserHeader();

        add(header, BorderLayout.NORTH);
        add(cardContainer, BorderLayout.CENTER);
    }

    public void refresh(){
        scrollablePanel.removeAll();

        for(SideScrollDisplayer s : madeSideScrollables){
            scrollablePanel.add(s);
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
