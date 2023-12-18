package groupbuyapp.Admin;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import groupbuyapp.Admin.AdminContent.AContent;
import groupbuyapp.Admin.AdminContent.ContentDisplayers.ContentContainer;
import groupbuyapp.Admin.AdminContent.ContentDisplayers.ContentDisplayer;
import groupbuyapp.Admin.AdminSideBar.ASidebar;
import groupbuyapp.Admin.AdminTopNav.AtopNavbar;
import groupbuyapp.SystemFiles.Database.GbuyDatabase;
import groupbuyapp.SystemFiles.Database.Product;


public class AdminController {
    private AtopNavbar atopNavbar;
    private AContent aContent;
    private ASidebar aSidebar;
    private AdminFrame adminFrame;

    public AdminFrame getAdminFrame() {
        return adminFrame;
    }

    public AtopNavbar getAtopNavbar() {
        return atopNavbar;
    }

    public AContent getaContent() {
        return aContent;
    }

    public ASidebar getaSidebar() {
        return aSidebar;
    }


    public AdminController(){}

    public AdminController(AtopNavbar atopNavbar, AContent aContent, ASidebar aSidebar, AdminFrame adminFrame){
        this.atopNavbar = atopNavbar;
        this.aContent = aContent;
        this.aSidebar = aSidebar;
        this.adminFrame = adminFrame;
    }

    public void init(){
        var forPurchaseButton = aSidebar.getForPurchaseButton();
        var forDeliveryButton = aSidebar.getForDeliveryButton();
        var deliveredButton = aSidebar.getDeliveredButton();
        var logOut = aSidebar.getLogoutIcon();

        forPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var currentDisplay = aContent.getCurrentDisplayer();
                currentDisplay.getHeaderName().setText("For Purchasing");
                currentDisplay.setAllcontentContainers(generateDefault(currentDisplay));
                currentDisplay.revalidate();
                currentDisplay.repaint();
                aSidebar.getButtons().setSelected(ASidebar.FOR_PURCHASE);
            }
            
        });

        forDeliveryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var currentDisplay = aContent.getCurrentDisplayer();
                currentDisplay.getHeaderName().setText("For Delivery");
                currentDisplay.setAllcontentContainers(generateForDelivery(currentDisplay));
                currentDisplay.revalidate();
                currentDisplay.repaint();
                aSidebar.getButtons().setSelected(ASidebar.FOR_DELIVERY);
            }   
            
        });

        deliveredButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var currentDisplay = aContent.getCurrentDisplayer();
                currentDisplay.getHeaderName().setText("Delivered");
                currentDisplay.setAllcontentContainers(generateDelivered(currentDisplay));
                currentDisplay.revalidate();
                currentDisplay.repaint();
                aSidebar.getButtons().setSelected(ASidebar.DELIVERED);
            }
            
        });

        logOut.addMouseListener(new LogOutAction());
    }


    public List<ContentContainer> generateDefault(ContentDisplayer currentDisplay){
        return generateForPurchaseContentContainers(GbuyDatabase.getInstance().getProductsForAdmin(GbuyDatabase.FOR_PURCHASING), currentDisplay);
    }

    public List<ContentContainer> generateForDelivery(ContentDisplayer currentDisplay){
        return generateForDeliveryContentContainers(GbuyDatabase.getInstance().getProductsForAdmin(GbuyDatabase.FOR_DELIVERY), currentDisplay);
    }

    public List<ContentContainer> generateDelivered(ContentDisplayer currentDisplayer){
        return generateDeliveredContentContainers(GbuyDatabase.getInstance().getProductsForAdmin(GbuyDatabase.DELIVERED), currentDisplayer);
    }


    public List<ContentContainer> generateForPurchaseContentContainers(List<Product> products, ContentDisplayer contentDisplayer){
        List<ContentContainer> madeContentContainers = new ArrayList<>();

        for(Product p : products){
            ContentContainer c = new ContentContainer(ContentContainer.FOR_PURCHASING);
            Image resizedImage = resizeImage(p.getImageIcon());
            c.setProduct(p);
            c.getProductImage().setIcon(new ImageIcon(resizedImage));
            c.getProductName().setText(p.getName());
            c.getProductPrice().setText(p.getPrice());
            c.getProductLocation().setText(p.getLocation());
            c.getButton().addActionListener(new ForPurchaseListener(p, contentDisplayer));
            madeContentContainers.add(c);
        }

        return madeContentContainers;
    }

    public List<ContentContainer> generateForDeliveryContentContainers(List<Product> products, ContentDisplayer contentDisplayer){
        List<ContentContainer> madeContentContainers = new ArrayList<>();

        for(Product p : products){
            ContentContainer c = new ContentContainer(ContentContainer.FOR_DELIVERY);
            Image resizedImage = resizeImage(p.getImageIcon());
            c.setProduct(p);
            c.getProductImage().setIcon(new ImageIcon(resizedImage));
            c.getProductName().setText(p.getName());
            c.getProductPrice().setText(p.getPrice());
            c.getProductLocation().setText(p.getLocation());
            c.getButton().addActionListener(new ForDeliveryListener(p, contentDisplayer));
            madeContentContainers.add(c);
        }

        return madeContentContainers;
    }

    public List<ContentContainer> generateDeliveredContentContainers(List<Product> products, ContentDisplayer contentDisplayer){
        List<ContentContainer> madeContentContainers = new ArrayList<>();

        for(Product p : products){
            ContentContainer c = new ContentContainer(ContentContainer.DELIVERED);
            Image resizedImage = resizeImage(p.getImageIcon());
            c.setProduct(p);
            c.getProductImage().setIcon(new ImageIcon(resizedImage));
            c.getProductName().setText(p.getName());
            c.getProductPrice().setText(p.getPrice());
            c.getProductLocation().setText(p.getLocation());
            madeContentContainers.add(c);
        }

        return madeContentContainers;
    }


    private Image resizeImage(ImageIcon imageIcon){
        int newWidth = 0;
        int preferredHeight = 130;

        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();
        
        newWidth = (iconWidth * preferredHeight) / iconHeight;
        
        Image image = imageIcon.getImage().getScaledInstance(newWidth, preferredHeight, Image.SCALE_SMOOTH);
        return image;
    }

    public static final int ADD_FOR_PURCHASE_LISTENER = 1;
    public static final int ADD_FOR_DELIVERY_LISTENER = 2;


    //listeners for buttons
    class ForPurchaseListener implements ActionListener{
        private Product product;
        private ContentDisplayer contentDisplayer;

        public ForPurchaseListener(Product product, ContentDisplayer contentDisplayer){
            this.product = product;
            this.contentDisplayer = contentDisplayer;
        } 

        @Override
        public void actionPerformed(ActionEvent e) {
            GbuyDatabase.getInstance().updateStatusByAdmin(product.getId(), GbuyDatabase.TO_PURCHASING);
            contentDisplayer.setAllcontentContainers(generateDefault(contentDisplayer));
            contentDisplayer.revalidate();
            contentDisplayer.repaint();
        }
        
    }

    class ForDeliveryListener implements ActionListener{
        private Product product;
        private ContentDisplayer contentDisplayer;

        public ForDeliveryListener(Product product, ContentDisplayer contentDisplayer){
            this.product = product;
            this.contentDisplayer = contentDisplayer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GbuyDatabase.getInstance().updateStatusByAdmin(product.getId(), GbuyDatabase.TO_DELIVERED);
            contentDisplayer.setAllcontentContainers(generateForDelivery(contentDisplayer));
            contentDisplayer.revalidate();
            contentDisplayer.repaint();
        }
        
    }

    class LogOutAction extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            adminFrame.dispose();
        }
    }

}
