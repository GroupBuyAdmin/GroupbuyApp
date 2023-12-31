package groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.ClientCenter.ClientContent.ClientContainers.ProductPanel;
import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.RoundedButton;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;

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

        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
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
            headerName.setFont(GbuyFont.MULI_BOLD.deriveFont(32f));

            this.headerButton = new RoundedButton("");
            headerButton.setButtonColor(GbuyColor.MAIN_COLOR);
            headerButton.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            headerButton.setDrawBorder(false);
            headerButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);

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
            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
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

        public JPanel getScrollablePanel() {
            return scrollablePanel;
        }

        public BasicContent(int type){
            // setOpaque(false);
            this.scrollablePanel = new ScrollablePanel();
            scrollablePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 25, 25));
            scrollablePanel.setScrollableHeight(ScrollableSizeHint.STRETCH);
            scrollablePanel.setScrollableWidth(ScrollableSizeHint.FIT);
            scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);
            scrollablePanel.setBorder(BorderFactory.createEmptyBorder());
            
            JScrollPane scrollPane = new JScrollPane(scrollablePanel);
            scrollPane.setBackground(GbuyColor.PANEL_COLOR);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            setLayout(new BorderLayout());
            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);

            add(scrollPane);
        }

        public void refresh(){
            scrollablePanel.removeAll();
            for(ProductPanel productPanel : allProductPanels){
                if(allProductPanels.isEmpty()){
                } else {
                    scrollablePanel.add(productPanel);
                }
            }
        }

    }
}
