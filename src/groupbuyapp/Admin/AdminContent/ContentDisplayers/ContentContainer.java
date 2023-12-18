package groupbuyapp.Admin.AdminContent.ContentDisplayers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import groupbuyapp.Admin.Misc.BottomLineBorder;
import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.RoundedButton;
import groupbuyapp.SystemFiles.Database.Product;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;
import net.miginfocom.swing.MigLayout;

public class ContentContainer extends JPanel{
    public static final int FOR_PURCHASING = 1;
    public static final int FOR_DELIVERY = 2;
    public static final int DELIVERED = 3;
    
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    private Product product;
    private InformationPanel informationPanel;
    private ButtonPanel buttonPanel;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public InformationPanel getInformationPanel() {
        return informationPanel;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public JLabel getProductImage(){
        return informationPanel.getImage();
    }

    public JLabel getProductName(){
        return informationPanel.getProductName();
    }

    public JLabel getProductPrice(){
        return informationPanel.getProductPrice();
    }

    public JLabel getProductLocation(){
        return informationPanel.getProductLocation();
    }

    public JButton getButton(){
        return buttonPanel.getButton();
    }

    public void setButton(RoundedButton rButton){
        buttonPanel.setButton(rButton);
    }

    public ContentContainer(int type){
        setBackground(GbuyColor.PANEL_COLOR);
        this.type = type;
        this.informationPanel = new InformationPanel();
        this.buttonPanel = new ButtonPanel(this.type);

        setLayout(new BorderLayout());
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border customBorder = new BottomLineBorder(GbuyColor.PANEL_BACKGROUND_COLOR);
        Border compoundOBorder = BorderFactory.createCompoundBorder(customBorder, emptyBorder);
        setBorder(compoundOBorder);
        add(informationPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);
    }

    class InformationPanel extends JPanel{
        private JLabel image;
        private JLabel productName;
        private JLabel productPrice;
        private JLabel productLocation;

        public JLabel getImage() {
            return image;
        }

        public JLabel getProductName() {
            return productName;
        }

        public JLabel getProductPrice() {
            return productPrice;
        }

        public JLabel getProductLocation() {
            return productLocation;
        }

        public InformationPanel(){
            setOpaque(false);
            this.image = new JLabel();
            this.productName = new JLabel("null");
            productName.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            this.productPrice = new JLabel("null");
            productPrice.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            this.productLocation = new JLabel("null");
            productLocation.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            productLocation.setForeground(GbuyColor.MAIN_COLOR);

            JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            imagePanel.add(image);
            imagePanel.setOpaque(false);

            JPanel details = new JPanel(new MigLayout("insets 5 5 5 5"));
            details.setBorder(BorderFactory.createEmptyBorder(25, 25, 0, 0));
            details.setOpaque(false);
            details.add(productName, "wrap");
            details.add(productPrice, "wrap");
            details.add(productLocation, "wrap");

            setLayout(new GridLayout(1,0));
            add(imagePanel);
            add(details);
            
        }
    }

    class ButtonPanel extends JPanel{
        private RoundedButton button;

        public void setButton(RoundedButton button) {
            this.button = button;
        }

        public RoundedButton getButton() {
            return button;
        }

        public ButtonPanel(int type){
            setOpaque(false);
            switch (type) {
                case FOR_PURCHASING:
                    this.button = renderPurchaseButton();
                    break;
                case FOR_DELIVERY:
                    this.button = renderDeliverButton();
                    break;
                case DELIVERED:
                    this.button = renderCompletedButton();
                    break;
                default:
                    break;
            }
            button.setPreferredSize(new Dimension(200, 25));
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(45, 0, 45, 15));
            add(button);
        }
    }

    private RoundedButton renderPurchaseButton(){
        RoundedButton rButton = new RoundedButton("PURCHASE");
        rButton.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
        rButton.setButtonColor(GbuyColor.COMPLETED_COLOR);
        rButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
        rButton.setDrawBorder(false);
        return rButton;
    }

    private RoundedButton renderDeliverButton(){
        RoundedButton rButton = new RoundedButton("DELIVER");
        rButton.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
        rButton.setButtonColor(GbuyColor.DELIVER_COLOR);
        rButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
        rButton.setDrawBorder(false);
        return rButton;
    }

    private RoundedButton renderCompletedButton(){
        RoundedButton rButton = new RoundedButton("COMPLETED");
        rButton.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
        rButton.setButtonColor(Color.decode("#EFEFEF"));
        rButton.setForeground(GbuyColor.MAIN_TEXT_COLOR);
        rButton.setBorderColor(Color.decode("#D9D9D9"));
        return rButton;
    }
}
