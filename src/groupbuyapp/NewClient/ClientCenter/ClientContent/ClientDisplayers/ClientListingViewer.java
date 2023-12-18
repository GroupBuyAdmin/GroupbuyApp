package groupbuyapp.NewClient.ClientCenter.ClientContent.ClientDisplayers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundedButton;
import groupbuyapp.Misc.CustomComponents.RoundedCornerTextArea;
import groupbuyapp.Misc.CustomComponents.RoundedPanel;
import groupbuyapp.Misc.CustomComponents.RoundedToggleButton;
import groupbuyapp.Misc.Fonts.GbuyFont;

/**
 * The {@code ListingViewer} class is a Java class that extends the {@code RoundedPanel} class.
 * It is used to display the details of a product listing, including an image, product information, and buttons for navigation and interaction.
 * 
 * @author BSCS 2A Group 5
 */
public class ClientListingViewer extends RoundedPanel{
    private ImagePanel imagePanel;
    private DetailsPanel detailsPanel;

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public void setImagePanel(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
    }

    public DetailsPanel getDetailsPanel() {
        return detailsPanel;
    }

    public void setDetailsPanel(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

    private Product product;

    public Product getProduct() {
        return product;
    }

    private RoundedButton backButton;
    
    private int fromWhere;
    
    public static final int FROM_MY_LISTING = 1;
    public static final int FROM_MY_GROUPBUYS = 2;
    public static final int FROM_BROWSE = 3;
    public static final int FROM_SEARCH = 4;
    
    public RoundedButton getBackButton() {
        return backButton;
    }

    public JLabel getImage(){
        return imagePanel.getImageLabel();
    }

    public JButton getEditButton(){
        return detailsPanel.getEditButton();
    }

    public JLabel getProductNameLabel(){
        return detailsPanel.getProductNameLabel();
    }

    public JLabel getProductPriceLabel(){
        return detailsPanel.getProductPriceLabel();
    }

    public JLabel getCreatorLabel(){
        return detailsPanel.getCreatorLabel();
    }

    public JLabel getLocationLabel(){
        return detailsPanel.getLocationLabel();
    }

    public JTextArea getDescriptionArea(){
        return detailsPanel.getDescriptionArea();
    }

    public RoundedToggleButton getToggleJoinButton(){
        return detailsPanel.getToggleJoinButton();
    }

    public JLabel getDeadlineLabel(){
        return detailsPanel.getDeadlineLabel();
    }

    public JLabel getCountLabel(){
        return detailsPanel.getCountLabel();
    }


    public ClientListingViewer(Product product, boolean isUser, int fromWhere){
        this.fromWhere = fromWhere;
        this.product = product;
        this.imagePanel = new ImagePanel(product.getImageIcon());
        imagePanel.setPreferredSize(new Dimension(700, 645));
        this.detailsPanel = new DetailsPanel(product);
        detailsPanel.setPreferredSize(new Dimension(670, 645));
        
        setShady(false);
        setBackground(GbuyColor.PANEL_COLOR);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        this.backButton = new RoundedButton("Back <-");
        backButton.setButtonColor(GbuyColor.PANEL_COLOR);
        backButton.setForeground(GbuyColor.MAIN_COLOR);
        backButton.setButtonFont(GbuyFont.MULI_LIGHT.deriveFont(14f));
        backButton.setDrawBorder(false);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.LEADING));
        buttonContainer.setOpaque(false);
        buttonContainer.add(backButton);

        add(buttonContainer, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.EAST);
    }

    class ImagePanel extends JPanel{
        private JPanel imageContainer;
        private JLabel imageLabel;

        public JPanel getImageContainer() {
            return imageContainer;
        }
        public JLabel getImageLabel() {
            return imageLabel;
        }
        
        public ImagePanel(ImageIcon imageIcon){
            imageContainer = new JPanel(new BorderLayout());
            imageContainer.setOpaque(false);
            imageLabel = new JLabel(new ImageIcon(resizeImage(imageIcon)));

            imageLabel.setHorizontalAlignment(JLabel.CENTER);

            imageContainer.add(imageLabel, BorderLayout.CENTER);
            
            setLayout(new BorderLayout());
            add(imageContainer, BorderLayout.CENTER);
            setOpaque(false);
        }

        private Image resizeImage(ImageIcon selectedImage) {
            int newHeight = 0;
            int preferredWidth = 700;
            int iconWidth = selectedImage.getIconWidth();
            int iconHeight = selectedImage.getIconHeight();
            
            newHeight = (iconHeight * preferredWidth) / iconWidth;

            Image image = selectedImage.getImage().getScaledInstance(preferredWidth, newHeight, Image.SCALE_SMOOTH);

            return image;
        }
    }

    class DetailsPanel extends JPanel{
        private RoundedButton editButton;
        private JLabel productNameLabel;
        private JLabel productPriceLabel;
        private JLabel creatorLabel;
        private JLabel locationLabel;
        private RoundedCornerTextArea descriptionArea;
        private RoundedToggleButton toggleJoinButton;
        private JLabel countLabel;
        private JLabel deadlineLabel;

        public JLabel getDeadlineLabel() {
            return deadlineLabel;
        }

        public RoundedToggleButton getToggleJoinButton() {
            return toggleJoinButton;
        }

        public RoundedButton getEditButton() {return editButton;}
        public JLabel getProductNameLabel() {return productNameLabel;}
        public JLabel getProductPriceLabel() {return productPriceLabel;}
        public JLabel getCreatorLabel() {return creatorLabel;}
        public JLabel getLocationLabel() {return locationLabel;}
        public RoundedCornerTextArea getDescriptionArea() {return descriptionArea;}
        public JLabel getCountLabel() {return countLabel;}


        public DetailsPanel(Product product){
            setOpaque(false);
            
            this.editButton = new RoundedButton("edit");
            editButton.setButtonColor(GbuyColor.PANEL_COLOR);
            editButton.setForeground(GbuyColor.MAIN_COLOR);
            editButton.setDrawBorder(false);
            editButton.setButtonFont(GbuyFont.MULI_LIGHT.deriveFont(14f));
            editButton.setPreferredSize(new Dimension(80, 25));
            
            this.productNameLabel = new JLabel(product.getName());
            productNameLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(36f));

            this.productPriceLabel = new JLabel(product.getPrice());
            productPriceLabel.setForeground(GbuyColor.MAIN_COLOR);
            productPriceLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(36f));

            this.creatorLabel = new JLabel("");

        
            creatorLabel.setForeground(Color.lightGray);
            creatorLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
            
            this.locationLabel = new JLabel("Location: " + product.getLocation());
            locationLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
            
            this.descriptionArea = new RoundedCornerTextArea();
            descriptionArea.setFocusable(false);
            descriptionArea.setText(product.getDescription());
            descriptionArea.setFont(GbuyFont.MULI_LIGHT.deriveFont(14f));
            descriptionArea.setBorder(BorderFactory.createEmptyBorder(15, 0, 30, 30));
            descriptionArea.setBackground(GbuyColor.PANEL_COLOR);
            descriptionArea.setEditable(false);
            descriptionArea.setCaretPosition(0);


            this.toggleJoinButton = new RoundedToggleButton("");
            toggleJoinButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            toggleJoinButton.setDrawBorder(false);

            this.countLabel = new JLabel("");
            countLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(16f));

            JPanel scrollablePanel = new JPanel();
            scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);
            
            scrollablePanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.01;            
            gbc.weighty = 0.05;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            scrollablePanel.add(productNameLabel, gbc);

            if(fromWhere == FROM_MY_LISTING){
                gbc.gridx++;
                gbc.weightx = 1.0;    
                gbc.anchor = GridBagConstraints.LAST_LINE_START;
                scrollablePanel.add(editButton, gbc);
            }

            gbc.gridx = 0;
            gbc.weightx = 1.0;  
            gbc.gridy++;
            gbc.gridwidth = 2;
            scrollablePanel.add(productPriceLabel, gbc);

            gbc.gridy++;
            scrollablePanel.add(creatorLabel, gbc);

            gbc.gridy++;
            scrollablePanel.add(locationLabel, gbc);
            
            //description area
            gbc.gridy++;
            gbc.insets = new Insets(20, 0, 0, 0);
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            scrollablePanel.add(descriptionArea, gbc);
            // descriptionArea.setCaretPosition(0);

            JScrollPane descScrollPanel = new JScrollPane(scrollablePanel);
            descScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            descScrollPanel.setBorder(BorderFactory.createEmptyBorder());
            descScrollPanel.setPreferredSize(getPreferredSize());
            descScrollPanel.setOpaque(false);
            descScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
            
            JPanel buttonPanels = new JPanel(new BorderLayout(10, 10));
            buttonPanels.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            
            // String formattedTime = formatTimestamp(product.getDeadlineStamp());
            deadlineLabel = new JLabel("");

            buttonPanels.setOpaque(false);

            buttonPanels.add(deadlineLabel, BorderLayout.NORTH);
            buttonPanels.add(countLabel, BorderLayout.WEST);

            if(product.getProductStatus().equals("expired")){
                RoundedPanel expiredPanel = new RoundedPanel();
                expiredPanel.setBackground(GbuyColor.EXPIRED_COLOR);
                expiredPanel.setShady(false);
                JLabel label = new JLabel("expired");
                label.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
                label.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
                expiredPanel.add(label);
                buttonPanels.add(expiredPanel, BorderLayout.EAST);

            } else if (product.getProductStatus().equals("completed")){
                RoundedPanel completedPanel = new RoundedPanel();
                completedPanel.setBackground(GbuyColor.COMPLETED_COLOR);
                completedPanel.setShady(false);
                JLabel label = new JLabel("completed");
                label.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
                label.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
                completedPanel.add(label);
                buttonPanels.add(completedPanel, BorderLayout.EAST);

            } else if(product.getProductStatus().equals("purchasing")){
                RoundedPanel purchasingPanel = new RoundedPanel();
                purchasingPanel.setBackground(GbuyColor.COMPLETED_COLOR);
                purchasingPanel.setShady(false);
                JLabel label = new JLabel("purchasing");
                label.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
                label.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
                purchasingPanel.add(label);
                buttonPanels.add(purchasingPanel, BorderLayout.EAST);

            } else if(product.getProductStatus().equals("delivered")){
                RoundedPanel deliveredPanel = new RoundedPanel();
                deliveredPanel.setBackground(GbuyColor.DELIVER_COLOR);
                deliveredPanel.setShady(false);
                JLabel label = new JLabel("delivered");
                label.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
                label.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
                deliveredPanel.add(label);
                buttonPanels.add(deliveredPanel, BorderLayout.EAST);
                
            } else if (fromWhere != FROM_MY_LISTING){
                    buttonPanels.add(toggleJoinButton, BorderLayout.EAST);

            }
            
            setLayout(new BorderLayout(0, 20));
            add(descScrollPanel, BorderLayout.CENTER);
            add(buttonPanels, BorderLayout.SOUTH);
            
        }

        public static String formatTimestamp(Timestamp timestamp) {
            try {
                // Format the timestamp to a custom format
                SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy @h:mm a");
                String formattedDateTime = formatter.format(new Date(timestamp.getTime()));
    
                return formattedDateTime;
            } catch (IllegalArgumentException e) {
                // Handle formatting errors
                System.err.println("Error formatting timestamp: " + e.getMessage());
                return null;
            }
        }


    }
}