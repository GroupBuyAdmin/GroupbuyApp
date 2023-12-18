package groupbuyapp.Admin.AdminSideBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.RoundedButton;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;
import net.miginfocom.swing.MigLayout;

public class ASidebar extends JPanel{
    private Buttons buttons;
    private JLabel logoutIcon;

    private static ImageIcon forPurchaseIconMain = new ImageIcon("src/groupbuyapp/Admin/AdminSideBar/Sidebar buttons/for purchase main.png");
    private static ImageIcon forDeliveryIconMain = new ImageIcon("src/groupbuyapp/Admin/AdminSideBar/Sidebar buttons/for delivery main.png");
    private static ImageIcon deliveredIconMain = new ImageIcon("src/groupbuyapp/Admin/AdminSideBar/Sidebar buttons/delivered main.png");

    private static ImageIcon forPurchaseIconAlt = new ImageIcon("src/groupbuyapp/Admin/AdminSideBar/Sidebar buttons/for purchase alt.png");
    private static ImageIcon forDeliveryIconAlt = new ImageIcon("src/groupbuyapp/Admin/AdminSideBar/Sidebar buttons/for delivery alt.png");
    private static ImageIcon deliveredIconAlt = new ImageIcon("src/groupbuyapp/Admin/AdminSideBar/Sidebar buttons/delivered alt.png");

    private static ImageIcon signOutIcon = new ImageIcon("src/groupbuyapp/Admin/AdminSideBar/Sidebar buttons/Sign out.png");

    private static final Dimension buttonSize = new Dimension(200, 45);

    public static final int FOR_PURCHASE = 1;
    public static final int FOR_DELIVERY = 2;
    public static final int DELIVERED = 3;

    public Buttons getButtons() {
        return buttons;
    }

    public JLabel getLogoutIcon() {
        return logoutIcon;
    }

    public JButton getForPurchaseButton(){
        return buttons.getForPurchaseButton();
    }

    public JButton getForDeliveryButton(){
        return buttons.getForDeliveryButton();
    }

    public JButton getDeliveredButton(){
        return buttons.getDeliveredButton();
    }

    public ASidebar(){
        setBackground(Color.white);
        this.buttons = new Buttons();
        this.logoutIcon = new JLabel(signOutIcon);
        logoutIcon.setHorizontalAlignment(JLabel.LEFT);
        logoutIcon.setToolTipText("Log Out");
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 15, 15, 5));
        add(buttons);
        add(logoutIcon, BorderLayout.SOUTH);
    }

    public class Buttons extends JPanel{
        private RoundedButton forPurchaseButton;
        private RoundedButton forDeliveryButton;
        private RoundedButton deliveredButton;

        public RoundedButton getForPurchaseButton() {
            return forPurchaseButton;
        }
        public RoundedButton getForDeliveryButton() {
            return forDeliveryButton;
        }
        public RoundedButton getDeliveredButton() {
            return deliveredButton;
        }

        public Buttons(){
            setOpaque(false);
            this.forPurchaseButton = new RoundedButton("For Purchasing", forDeliveryIconMain);
            forPurchaseButton.setButtonFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            this.forDeliveryButton = new RoundedButton("For Delivery", forDeliveryIconMain);
            forDeliveryButton.setButtonFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            this.deliveredButton = new RoundedButton("Delivered", deliveredIconMain);
            deliveredButton.setButtonFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));

            
            setLayout(new MigLayout("gap 0 20"));
            add(forPurchaseButton, "wrap, pushx, growx");
            add(forDeliveryButton, "wrap, pushx, growx");
            add(deliveredButton, "wrap, pushx, growx");
            
            initCustomButtons();
        }

        private void initCustomButtons(){
            setToNormalState(forPurchaseButton, forPurchaseIconMain);
            setToSelectedState(forPurchaseButton, forPurchaseIconAlt);
            setToNormalState(forDeliveryButton, forDeliveryIconMain);
            setToNormalState(deliveredButton, deliveredIconMain);
        }

        public void setSelected(int index){
            switch (index) {
                case FOR_PURCHASE:
                    setToSelectedState(forPurchaseButton, forPurchaseIconAlt);
                    setToNormalState(forDeliveryButton, forDeliveryIconMain);
                    setToNormalState(deliveredButton, deliveredIconMain);
                    break;
                    
                case FOR_DELIVERY:
                    setToNormalState(forPurchaseButton, forPurchaseIconMain);
                    setToSelectedState(forDeliveryButton, forDeliveryIconAlt);
                    setToNormalState(deliveredButton, deliveredIconMain);
                    break;
                    
                case DELIVERED:
                    setToNormalState(forPurchaseButton, forPurchaseIconMain);
                    setToNormalState(forDeliveryButton, forDeliveryIconMain);
                    setToSelectedState(deliveredButton, deliveredIconAlt);
                    break;

                default:
                    break;
            }
        }

        private void setToNormalState(RoundedButton rButton, ImageIcon icon){
            rButton.setButtonColor(Color.white);
            rButton.setForeground(GbuyColor.MAIN_TEXT_COLOR);
            rButton.setDrawBorder(false);
            rButton.setHorizontalAlignment(SwingConstants.LEADING);
            rButton.setIconTextGap(30);
            rButton.setPreferredSize(buttonSize);
            rButton.setButtonFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            rButton.setIcon(icon);
            rButton.setCornerRadius(8);
            rButton.revalidate();
            rButton.repaint();
        }

        private void setToSelectedState(RoundedButton rButton, ImageIcon icon){
            rButton.setButtonColor(GbuyColor.MAIN_COLOR);
            rButton.setForeground(Color.white);
            rButton.setDrawBorder(false);
            rButton.setIcon(icon);
            rButton.revalidate();
            rButton.repaint(); 
        }
    }

}
