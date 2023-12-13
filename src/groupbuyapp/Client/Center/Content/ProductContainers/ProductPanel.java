package groupbuyapp.Client.Center.Content.ProductContainers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundedPanel;
import groupbuyapp.Misc.Fonts.GbuyFont;


/**
 * The {@code ProductPanelSmall} class is a custom Swing component that represents a small panel displaying information about a product.
 * It includes an image container, details container, and a status indicator.
 * The class provides methods to set the status of the panel and retrieve the image and details containers.
 * 
 * @author BSCS 2A Group 5
 */
public class ProductPanel extends RoundedPanel{

    private Product product;
    private ImageContainer imageContainer;
    private DetailsContainer detailsContainer;
    private int containerStatus;
    private boolean isBrowserPanel;

    public static final boolean BROWSER_PANEL = true;
    private final Dimension browserPanelDimension = new Dimension(330, 340);
    private final Dimension normalPanelDimension = new Dimension(285, 265);

    public ImageContainer getImageContainer() {
        return imageContainer;
    }

    public DetailsContainer getDetailsContainer() {
        return detailsContainer;
    }

    public int getContainerStatus() {
        return containerStatus;
    }

    public Product getProduct() {
        return product;
    }

    //initialze with empty product
    public ProductPanel(){
        this(null, false);
    }

    public ProductPanel(boolean isBrowserPanel){
        this(null, isBrowserPanel);
    }

    public ProductPanel(Product product){
        this(product, false);
    }

    public ProductPanel(Product product, boolean isBrowserPanel){
        this.product = product;
        this.isBrowserPanel = isBrowserPanel;
        if(product != null){
            initContainer(product.getImageIcon(), product.getLocation(), product.getName(), product.getPrice());
        } else {
            initContainer(null, "No Location", "No Name", "$99.99");
        }
    }

    private void initContainer(ImageIcon image, String location, String name, String price){
        if(isBrowserPanel){
            setPreferredSize(browserPanelDimension);
        } else {
            setPreferredSize(normalPanelDimension);
        }
        setLayout(new BorderLayout(0, 0));
        setBackground(GbuyColor.PANEL_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        setDrawBorder(true);
        setStrokeSize(1);

        this.imageContainer = new ImageContainer(image);
        this.detailsContainer = new DetailsContainer(location, name, price);

        add(imageContainer, BorderLayout.CENTER);
        add(detailsContainer, BorderLayout.SOUTH);

        //default
        setContainerStatus(Status.ONGOING);
    }

    public void setContainerStatus(int status){
        switch (status) {
            case Status.ONGOING:
                setToOngoing();
                break;
        
            case Status.COMPLETED:
                setToCompleted();
                break;
        
            case Status.EXPIRED:
                setToExpired(); 
                break;
        
            default:
                break;
        }
        revalidate();
        repaint();
    }

    private void setToOngoing(){
        setShadowColor(Color.BLACK);
        setShadowGap(5);
        setShadowOffset(0);
        setShadowAlpha(10);
        detailsContainer.setStatus(DetailsContainer.ONGOING_STATUS);
        this.containerStatus = Status.ONGOING;
    }

    private void setToCompleted(){
        setShadowColor(Color.decode("#3EF050"));
        setShadowGap(5);
        setShadowOffset(0);
        setShadowAlpha(35);
        detailsContainer.setStatus(DetailsContainer.COMPLETED_STATUS);
        this.containerStatus = Status.COMPLETED;
    }

    private void setToExpired(){
        setShadowColor(Color.decode("#F20530"));
        setShadowGap(5);
        setShadowOffset(0);
        setShadowAlpha(30);
        detailsContainer.setStatus(DetailsContainer.EXPIRED_STATUS);
        this.containerStatus = Status.EXPIRED;
    }

    class Status{
        public static final int DEFAULT = 1;
        public static final int ONGOING = 1;
        public static final int COMPLETED = 2;
        public static final int EXPIRED = 3;
    }

    class ImageContainer extends JPanel{   
        private JLabel imageLabel;
        private JPanel imagePanel;

        public ImageContainer(ImageIcon imageIcon){
            setBackground(GbuyColor.PANEL_COLOR);
            imageLabel = new JLabel();
            imagePanel = new JPanel();
            imagePanel.setOpaque(false);
            imagePanel.setLayout(new BorderLayout());
            imagePanel.add(imageLabel, BorderLayout.CENTER);

            if(imageIcon == null){
                imageLabel.setIcon(new ImageIcon("src/groupbuyapp/Client/Center/Content/ProductContainers/img/empty image(small).png"));
            } else {
                Image resizedImage = resizeImage(imageIcon);
                ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
                imageLabel.setIcon(resizedImageIcon);
            }

            add(imagePanel);
        }

        private Image resizeImage(ImageIcon imageIcon){
            int newWidth = 0;
            int preferredHeight = 160;
            int iconWidth = imageIcon.getIconWidth();
            int iconHeight = imageIcon.getIconHeight();
            
            newWidth = (iconWidth * preferredHeight) / iconHeight;
            
            Image image = imageIcon.getImage().getScaledInstance(newWidth, preferredHeight, Image.SCALE_SMOOTH);
            return image;
        }
    }

    public class DetailsContainer extends JPanel{
        private JLabel locationLabel;
        private JLabel nameLabel;
        private JLabel priceLabel;
        private RoundedPanel statusContainer;
        private JLabel statusLabel;

        public static final int ONGOING_STATUS = 1;
        public static final int COMPLETED_STATUS = 2;
        public static final int EXPIRED_STATUS = 3;

        private static Color ongoingColor = Color.decode("#79AEF2");
        private static Color completedColor = Color.decode("#3EF050");
        private static Color expiredColor= Color.decode("#F20530");

        public void setStatus(int index){
            switch (index) {
                case ONGOING_STATUS:
                    setToOngoing();
                    break;

                case COMPLETED_STATUS:
                    setToCompleted();
                    break;

                case EXPIRED_STATUS:
                    setToExpired();
                    break;
                default:
                    break;
            }
            revalidate();
            repaint();
        }

        public JLabel getLocationLabel() {
            return locationLabel;
        }

        public JLabel getNameLabel() {
            return nameLabel;
        }

        public JLabel getPriceLabel() {
            return priceLabel;
        }


        public DetailsContainer(String location, String name, String price){
            setBackground(GbuyColor.PANEL_COLOR);
            locationLabel = new JLabel(location);
            locationLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));
            locationLabel.setForeground(Color.lightGray);

            nameLabel = new JLabel(name);
            nameLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
            
            priceLabel = new JLabel(price);
            priceLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));

            initStatus();

            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;

            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridwidth = 2;

            //row 1
            add(locationLabel, gbc);

            //row 2
            gbc.gridy++;
            add(nameLabel, gbc);

            //row 3
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.LINE_START;
            add(priceLabel, gbc);
            
            gbc.gridx++;
            gbc.weightx = 0.1;
            gbc.anchor = GridBagConstraints.LINE_END;
            add(statusContainer, gbc);


        }

        private void initStatus(){
            statusContainer = new RoundedPanel();
            statusLabel = new JLabel();
            statusLabel.setHorizontalAlignment(JLabel.CENTER);
            statusLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(10f));
            statusContainer.add(statusLabel);
            statusContainer.setShady(false);
            statusContainer.setDrawBorder(true);
        }

        private void setToOngoing(){
            statusLabel.setText("ongoing");
            statusLabel.setForeground(ongoingColor);
            statusContainer.setBackground(GbuyColor.PANEL_COLOR);
            statusContainer.setBorderColor(ongoingColor);
        }

        private void setToCompleted(){
            statusLabel.setText("complete");
            statusLabel.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            statusContainer.setBorderColor(completedColor);
            statusContainer.setBackground(completedColor);
        }

        private void setToExpired(){
            statusLabel.setText("expired");
            statusLabel.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            statusContainer.setBorderColor(expiredColor);
            statusContainer.setBackground(expiredColor);
        }
    }



}