package groupbuyapp.Admin.AdminContent.ContentDisplayers;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Admin.AdminController;
import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;
import net.miginfocom.swing.MigLayout;

public class ContentDisplayer extends JPanel{
    public static final int FOR_PURCHASING = 1;
    public static final int FOR_DELIVERY = 2;
    public static final int DELIVERED = 3;

    private HeaderPanel headerPanel;
    private ScrollPanel scrollPanel;
    private boolean isDefault;

    public static final boolean DEFAULT = true;

    public boolean isDefault() {
        return isDefault;
    }

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public ScrollPanel getScrollPanel() {
        return scrollPanel;
    }

    public JLabel getHeaderName(){
        return headerPanel.getHeaderName();
    }

    public ScrollablePanel getScrollablePanel(){
        return scrollPanel.getScrollablePanel();
    }

    public List<ContentContainer> getAllContentContainers(){
        return scrollPanel.getAllcontentContainers();
    }

    public void setAllcontentContainers(List<ContentContainer> allContainers){
        scrollPanel.setAllcontentContainers(allContainers);

    }

    public ContentDisplayer(){
        this(false);
    }

    public ContentDisplayer(boolean isDefault){
        this.headerPanel = new HeaderPanel();
        this.scrollPanel = new ScrollPanel(isDefault);
        this.isDefault = isDefault;

        setLayout(new BorderLayout());

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPanel, BorderLayout.CENTER);
        setOpaque(false);
    }


    class HeaderPanel extends JPanel{
        private JLabel headerName;

        public JLabel getHeaderName() {
            return headerName;
        }

        public HeaderPanel(){
            this.headerName = new JLabel("For Purchasing");
            headerName.setFont(GbuyFont.MULI_BOLD.deriveFont(32f));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 25, 0));
            setLayout(new BorderLayout());
            setBackground(GbuyColor.PANEL_COLOR);
            add(headerName, BorderLayout.WEST);
        }
    }

    class ScrollPanel extends JPanel{
        private ScrollablePanel scrollablePanel;
        private List<ContentContainer> allcontentContainers;
        private JScrollPane scrollPane;

        public JScrollPane getScrollPane() {
            return scrollPane;
        }

        public void setAllcontentContainers(List<ContentContainer> allcontentContainers) {
            this.allcontentContainers = allcontentContainers;
            refresh();
        }

        public List<ContentContainer> getAllcontentContainers() {
            return allcontentContainers;
        }

        public ScrollablePanel getScrollablePanel() {
            return scrollablePanel;
        }

        public ScrollPanel(boolean isDefault){
            setOpaque(false);
            this.allcontentContainers = new ArrayList<>();
            this.scrollablePanel = new ScrollablePanel();
            scrollablePanel.setScrollableHeight(ScrollableSizeHint.FIT);
            scrollablePanel.setScrollableWidth(ScrollableSizeHint.FIT);
            scrollablePanel.setLayout(new MigLayout("insets 0"));
            scrollablePanel.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
            scrollablePanel.setBorder(BorderFactory.createEmptyBorder());

            if(isDefault){
                generateForPurchaseDefault();
            }

            this.scrollPane = new JScrollPane(scrollablePanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setOpaque(false);
            
            JPanel j = new JPanel(new BorderLayout());
            j.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
    
            j.add(scrollPane);

            setLayout(new BorderLayout());


            add(j);
        }

        public void refresh(){
            scrollablePanel.removeAll();
            for(ContentContainer c : allcontentContainers){
                scrollablePanel.add(c, "wrap, pushx, growx");
            }
        }


        public void generateForPurchaseDefault(){
            AdminController a = new AdminController();
            setAllcontentContainers(a.generateDefault(ContentDisplayer.this));
        }

    }
}
