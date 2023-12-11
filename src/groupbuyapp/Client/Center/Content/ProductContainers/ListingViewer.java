package groupbuyapp.Client.Center.Content.ProductContainers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundedButton;
import groupbuyapp.Misc.CustomComponents.RoundedCornerTextArea;
import groupbuyapp.Misc.CustomComponents.RoundedPanel;
import groupbuyapp.Misc.Fonts.GbuyFont;
import net.miginfocom.swing.MigLayout;

/**
 * The {@code ListingViewer} class is a Java class that extends the {@code RoundedPanel} class.
 * It is used to display the details of a product listing, including an image, product information, and buttons for navigation and interaction.
 * 
 * @author BSCS 2A Group 5
 */
public class ListingViewer extends RoundedPanel{
    private Product product;
    
    private ImagePanel imagePanel;
    private DetailsPanel detailsPanel;
    private boolean isUser;

    private RoundedButton backButton;
    private int fromWhere;
    
    public static final int FROM_MY_LISTING = 1;
    public static final int FROM_MY_GROUPBUYS = 2;
    public static final int FROM_BROWSE = 3;
    
    public int getFromWhere() {return fromWhere;}
    public boolean isUser() {return isUser;}
    public ImagePanel getImagePanel() {return imagePanel;}
    public DetailsPanel getDetailsPanel() {return detailsPanel;}
    public JButton getBackButton(){return backButton;} 
    public JButton getEditButton(){ return detailsPanel.getEditButton();}
    public Product getProduct() {return product;} 
    public void setProduct(Product product) {this.product = product;}

    public ListingViewer(){
        this(null, true);
    }

    public ListingViewer(Product product){
        this(product, true);
    }

    public ListingViewer(Product product, boolean isUser){
        this(product, isUser, FROM_MY_LISTING);
    }

    public ListingViewer(Product product, boolean isUser, int fromWhere){
        this.product = product;
        this.isUser = isUser;
        this.fromWhere = fromWhere;

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
        private RoundedButton joinButton;
        private JLabel countLabel;

        public RoundedButton getEditButton() {return editButton;}
        public JLabel getProductNameLabel() {return productNameLabel;}
        public JLabel getProductPriceLabel() {return productPriceLabel;}
        public JLabel getCreatorLabel() {return creatorLabel;}
        public JLabel getLocationLabel() {return locationLabel;}
        public RoundedCornerTextArea getDescriptionArea() {return descriptionArea;}
        public RoundedButton getJoinButton() {return joinButton;}
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

            this.creatorLabel = new JLabel("By: admin");
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

            this.joinButton = new RoundedButton("Join Groupbuy");
            joinButton.setCornerRadius(30);
            joinButton.setButtonColor(GbuyColor.MAIN_COLOR);
            joinButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            joinButton.setButtonFont(GbuyFont.MULI_BOLD.deriveFont(16f));
            joinButton.setDrawBorder(false);

            
            this.countLabel = new JLabel("Groupbuy count: 0/20");
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
            
            JPanel buttonPanels = new JPanel(new MigLayout("fillx"));
            buttonPanels.setOpaque(false);
            buttonPanels.add(countLabel, "gapleft 0, align left");

            if(fromWhere != FROM_MY_LISTING){
                buttonPanels.add(joinButton, "gapright 10, align right");
            }
            
            setLayout(new BorderLayout(0, 20));
            add(descScrollPanel, BorderLayout.CENTER);
            add(buttonPanels, BorderLayout.SOUTH);
        }

    }
}