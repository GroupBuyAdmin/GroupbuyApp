package groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.ClientCenter.ClientContent.ClientContainers.ProductPanel;
import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;

public class CategoryDisplayer extends JPanel{
    private BasicHeader basicHeader;
    private BasicContent basicContent;

    public static final int MY_LISTINGS = 1;
    public static final int MY_GROUPBUYS = 2;

    public JLabel getHeaderName(){
        return basicHeader.getHeaderName();
    }

    public void setProductPanels(List<ProductPanel> productPanels){
        var scrollable = basicContent.getScrollablePanel();
        if(!productPanels.isEmpty()){
            scrollable.setScrollableHeight(ScrollableSizeHint.STRETCH);
            basicContent.setAllProductPanels(productPanels);
            basicContent.refresh();
        } else {
            scrollable.setScrollableHeight(ScrollableSizeHint.STRETCH);
        }
        scrollable.revalidate();
        scrollable.repaint();
    }

    public JLabel getBackLabel(){
        return basicHeader.getBackLabel();
    }

    public CategoryDisplayer(){
        this.basicHeader = new BasicHeader();
        this.basicContent = new BasicContent();

        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        add(basicHeader, BorderLayout.NORTH);
        add(basicContent, BorderLayout.CENTER);
    }

    public class BasicHeader extends JPanel{
        private JLabel headerName;
        private JLabel backLabel;

        public JLabel getBackLabel() {
            return backLabel;
        }

        public JLabel getHeaderName() {
            return headerName;
        }

        public BasicHeader(){
            this.headerName = new JLabel("");
            headerName.setFont(GbuyFont.MULI_BOLD.deriveFont(26f));

            this.backLabel = new JLabel("Back");
            backLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(20f));
            backLabel.setForeground(GbuyColor.MAIN_COLOR);

            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
            setLayout(new BorderLayout());
            add(backLabel, BorderLayout.EAST);
            add(headerName, BorderLayout.WEST);
        }

    }

    public class BasicContent extends JPanel{
        private ScrollablePanel scrollablePanel;
        private List<ProductPanel> allProductPanels;

        public List<ProductPanel> getAllProductPanels() {
            return allProductPanels;
        }

        public void setAllProductPanels(List<ProductPanel> allProductPanels) {
            this.allProductPanels = allProductPanels;
        }

        public ScrollablePanel getScrollablePanel() {
            return scrollablePanel;
        }

        public BasicContent(){
            this.scrollablePanel = new ScrollablePanel();
            scrollablePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 25, 25));
            scrollablePanel.setScrollableHeight(ScrollableSizeHint.NONE);
            scrollablePanel.setScrollableWidth(ScrollableSizeHint.FIT);
            scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);
            scrollablePanel.setBorder(BorderFactory.createEmptyBorder());

            JScrollPane scrollPane = new JScrollPane(scrollablePanel);
            scrollPane.setBackground(GbuyColor.PANEL_COLOR);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            setLayout(new BorderLayout());
            
            add(scrollPane);
        }

        public void refresh(){
            scrollablePanel.removeAll();
            for(ProductPanel productPanel : allProductPanels){
                scrollablePanel.add(productPanel);
            }
        }

    }
}
