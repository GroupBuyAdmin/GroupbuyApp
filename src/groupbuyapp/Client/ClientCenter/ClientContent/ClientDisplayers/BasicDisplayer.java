package groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.ClientCenter.ClientContent.ClientContainers.ProductPanel;
import groupbuyapp.SystemFiles.CustomComponents.RoundedButton;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import net.miginfocom.swing.MigLayout;

//has a button
public class BasicDisplayer extends JPanel{
    private BasicHeader basicHeader;
    private BasicContent basicContent;

    public static final int MY_LISTINGS = 1;
    public static final int MY_GROUPBUYS = 2;

    public JLabel getHeaderName(){
        return basicHeader.getHeaderName();
    }

    public JButton getHeaderButton(){
        return basicHeader.getHeaderButton();
    }

    public void setProductPanels(List<ProductPanel> productPanels){
        basicContent.setAllProductPanels(productPanels);
        basicContent.refresh();
    }


    public BasicDisplayer(int type){
        this.basicHeader = new BasicHeader(type);
        this.basicContent = new BasicContent(type);

        setLayout(new BorderLayout());
        add(basicHeader, BorderLayout.NORTH);
        add(basicContent, BorderLayout.CENTER);
    }

    public class BasicHeader extends JPanel{
        private JLabel headerName;
        private RoundedButton headerButton;

        public void setHeaderButton(RoundedButton headerButton) {
            this.headerButton = headerButton;
        }

        public RoundedButton getHeaderButton() {
            return headerButton;
        }

        public JLabel getHeaderName() {
            return headerName;
        }

        public BasicHeader(int type){
            this.headerName = new JLabel("");
            this.headerButton = new RoundedButton("");

            switch (type) {
                case MY_LISTINGS:
                    headerName.setText("My Listings");
                    headerButton.setText("Create Listing");
                    break;
            
                case MY_GROUPBUYS:
                    headerName.setText("My Groupbuys");
                    headerButton.setText("Browse Groupbuys");
                    break;
            
                default:
                    break;
            }
            
            setLayout(new BorderLayout());
            add(headerName, BorderLayout.WEST);
            add(headerButton, BorderLayout.EAST);
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

        public BasicContent(int type){
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
                scrollablePanel.add(productPanel, "pushx, growx");
            }
        }

    }
}
