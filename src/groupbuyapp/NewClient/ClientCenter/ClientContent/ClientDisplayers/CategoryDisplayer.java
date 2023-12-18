package groupbuyapp.NewClient.ClientCenter.ClientContent.ClientDisplayers;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.Center.Content.ProductContainers.ProductPanel;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.Misc.Fonts.GbuyFont;
import net.miginfocom.swing.MigLayout;

public class CategoryDisplayer extends JPanel{
    private BasicHeader basicHeader;
    private BasicContent basicContent;

    public static final int MY_LISTINGS = 1;
    public static final int MY_GROUPBUYS = 2;

    public JLabel getHeaderName(){
        return basicHeader.getHeaderName();
    }

    public void setProductPanels(List<ProductPanel> productPanels){
        basicContent.setAllProductPanels(productPanels);
        basicContent.refresh();
    }

    public JLabel getBackLabel(){
        return basicHeader.getBackLabel();
    }

    public CategoryDisplayer(){
        this.basicHeader = new BasicHeader();
        this.basicContent = new BasicContent();

        setLayout(new BorderLayout());
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
            this.backLabel = new JLabel("Back");
            backLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(20f));
            backLabel.setForeground(GbuyColor.MAIN_COLOR);

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
            scrollablePanel.setLayout(new MigLayout("wrap 5"));
            scrollablePanel.setScrollableHeight(ScrollableSizeHint.NONE);
            scrollablePanel.setScrollableWidth(ScrollableSizeHint.FIT);
            JScrollPane scrollPane = new JScrollPane(scrollablePanel);
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
