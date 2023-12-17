package groupbuyapp.Admin.AdminSideBar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ASidebar extends JPanel{
    private Buttons buttons;
    private JLabel logoutIcon;

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
        this.logoutIcon = new JLabel("logout");
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 5, 5, 5));
        add(buttons);
        add(logoutIcon, BorderLayout.SOUTH);
    }

    class Buttons extends JPanel{
        private JButton forPurchaseButton;
        private JButton forDeliveryButton;
        private JButton deliveredButton;

        public JButton getForPurchaseButton() {
            return forPurchaseButton;
        }
        public JButton getForDeliveryButton() {
            return forDeliveryButton;
        }
        public JButton getDeliveredButton() {
            return deliveredButton;
        }

        public Buttons(){
            setOpaque(false);
            this.forPurchaseButton = new JButton("For Purchasing");
            this.forDeliveryButton = new JButton("For Delivery");
            this.deliveredButton = new JButton("Delivered");

            setLayout(new MigLayout());
            add(forPurchaseButton, "wrap, pushx, growx");
            add(forDeliveryButton, "wrap, pushx, growx");
            add(deliveredButton, "wrap, pushx, growx");
        }

    }

}
