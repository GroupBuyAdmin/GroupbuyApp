package groupbuyapp.Client.Center.Content.ProductContainers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

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
public class ListViewer extends RoundedPanel{
    private ImagePanel imagePanel;
    private DetailsPanel detailsPanel;
    private RoundedButton backButton;
    private int fromWhere;
 
    public static final int FROM_MY_LISTING = 1;
    public static final int FROM_MY_GROUPBUYS = 2;
    public static final int FROM_BROWSE = 3;
    public static final int FROM_SEARCH = 4;
    
    public int getFromWhere() {return fromWhere;}
    public ImagePanel getImagePanel() {return imagePanel;}
    public DetailsPanel getDetailsPanel() {return detailsPanel;}
    
    public JButton getBackButton(){
        return backButton;
    } 
    
    public JLabel getImageContainer(){
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

    public JToggleButton getToggleButton(){
        return detailsPanel.getToggleJoinButton();
    }

    public JLabel getCountLabel(){
        return detailsPanel.getCountLabel();
    }

    public void enableToggleButton(){
        detailsPanel.enableToggleButton();
    }

    public void showExpired(){
        detailsPanel.showExpired();
    }

    public void showCompleted(){
        detailsPanel.showCompleted();
    }


    public ListViewer(){
        this.imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(new Dimension(700, 645));

        this.detailsPanel = new DetailsPanel();
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
        
        public ImagePanel(){
            imageContainer = new JPanel(new BorderLayout());
            imageContainer.setOpaque(false);
            imageLabel = new JLabel();

            imageLabel.setHorizontalAlignment(JLabel.CENTER);

            imageContainer.add(imageLabel, BorderLayout.CENTER);
            
            setLayout(new BorderLayout());
            add(imageContainer, BorderLayout.CENTER);
            setOpaque(false);
        }

        // private Image resizeImage(ImageIcon selectedImage) {
        //     int newHeight = 0;
        //     int preferredWidth = 700;
        //     int iconWidth = selectedImage.getIconWidth();
        //     int iconHeight = selectedImage.getIconHeight();
            
        //     newHeight = (iconHeight * preferredWidth) / iconWidth;

        //     Image image = selectedImage.getImage().getScaledInstance(preferredWidth, newHeight, Image.SCALE_SMOOTH);

        //     return image;
        // }
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
        private JPanel buttonPanels;
        private JLabel deadlineLabel;
        
        public RoundedButton getEditButton() {return editButton;}
        public JLabel getProductNameLabel() {return productNameLabel;}
        public JLabel getProductPriceLabel() {return productPriceLabel;}
        public JLabel getCreatorLabel() {return creatorLabel;}
        public JLabel getLocationLabel() {return locationLabel;}
        public RoundedCornerTextArea getDescriptionArea() {return descriptionArea;}
        public JLabel getCountLabel() {return countLabel;}
        public RoundedToggleButton getToggleJoinButton() {
            return toggleJoinButton;
        }

        public DetailsPanel(){
            setOpaque(false);
            
            this.editButton = new RoundedButton("edit");
            editButton.setButtonColor(GbuyColor.PANEL_COLOR);
            editButton.setForeground(GbuyColor.MAIN_COLOR);
            editButton.setDrawBorder(false);
            editButton.setButtonFont(GbuyFont.MULI_LIGHT.deriveFont(14f));
            editButton.setPreferredSize(new Dimension(80, 25));
            
            this.productNameLabel = new JLabel();
            productNameLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(36f));

            this.productPriceLabel = new JLabel();
            productPriceLabel.setForeground(GbuyColor.MAIN_COLOR);
            productPriceLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(36f));

            this.creatorLabel = new JLabel();
            // if(fromWhere != ListViewer.FROM_MY_LISTING){
            //     this.creatorLabel = new JLabel("Creator: " + GbuyDatabase.getInstance().getUserName(product.getCreatorID()));
            // } else {
            //     this.creatorLabel = new JLabel("Creator: " + currentUser.getUserName());
            // }

            creatorLabel.setForeground(Color.lightGray);
            creatorLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
            
            this.locationLabel = new JLabel();
            locationLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
            
            this.descriptionArea = new RoundedCornerTextArea();
            descriptionArea.setFocusable(false);
            descriptionArea.setFont(GbuyFont.MULI_LIGHT.deriveFont(14f));
            descriptionArea.setBorder(BorderFactory.createEmptyBorder(15, 0, 30, 30));
            descriptionArea.setBackground(GbuyColor.PANEL_COLOR);
            descriptionArea.setEditable(false);
            descriptionArea.setCaretPosition(0);


            this.toggleJoinButton = new RoundedToggleButton("");
            toggleJoinButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            toggleJoinButton.setDrawBorder(false);

            this.countLabel = new JLabel();
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

            gbc.gridx++;
            gbc.weightx = 1.0;    
            gbc.anchor = GridBagConstraints.LAST_LINE_START;
            scrollablePanel.add(editButton, gbc);

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


            JScrollPane descScrollPanel = new JScrollPane(scrollablePanel);
            descScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            descScrollPanel.setBorder(BorderFactory.createEmptyBorder());
            descScrollPanel.setPreferredSize(getPreferredSize());
            descScrollPanel.setOpaque(false);
            descScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
            
            buttonPanels = new JPanel(new BorderLayout(10, 10));
            buttonPanels.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            
            // String formattedTime = formatTimestamp(product.getDeadlineStamp());
            deadlineLabel = new JLabel();

            buttonPanels.setOpaque(false);

            buttonPanels.add(deadlineLabel, BorderLayout.NORTH);
            buttonPanels.add(countLabel, BorderLayout.WEST);

            // if(p.productStatus.equals("expired")){
            //     RoundedPanel expiredPanel = new RoundedPanel();
            //     expiredPanel.setBackground(GbuyColor.EXPIRED_COLOR);
            //     expiredPanel.setShady(false);
            //     JLabel label = new JLabel("expired");
            //     label.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
            //     label.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            //     expiredPanel.add(label);
            //     buttonPanels.add(expiredPanel, BorderLayout.EAST);
            // }
            // else if (fromWhere != FROM_MY_LISTING){
            //     if(p.userCount != p.userLimit) {                    
            //         buttonPanels.add(toggleJoinButton, BorderLayout.EAST);
            //         boolean joined = GbuyDatabase.getInstance().alreadyJoined(product.getId(), currentUser.getUserID());
    
            //         if(joined){
            //             toggleJoinButton.setText("Leave Groupbuy");
            //             toggleJoinButton.setDefaultColor(GbuyColor.MAIN_COLOR);
            //             toggleJoinButton.setPressedColor(GbuyColor.ONGOING_COLOR);
            //         } else {
            //             toggleJoinButton.setText("Join Groubuy");
            //             toggleJoinButton.setDefaultColor(GbuyColor.ONGOING_COLOR);
            //             toggleJoinButton.setPressedColor(GbuyColor.MAIN_COLOR);
            //         }
            //         toggleJoinButton.addActionListener(new ActionListener() {
            //             @Override
            //             public void actionPerformed(ActionEvent e) {
            //                 if(joined){
            //                     if(toggleJoinButton.isSelected()){
            //                         GbuyDatabase.getInstance().deleteGroupbuy(product.getId(), currentUser.getUserID());
            //                         JOptionPane.showMessageDialog(ListViewer.this, "You Left this groupbuy");
            //                         toggleJoinButton.setText("Join Groubuy");
                         
            //                     } else {
            //                         GbuyDatabase.getInstance().createGroupbuy(product.getId(), currentUser.getUserID());
            //                         JOptionPane.showMessageDialog(ListViewer.this, "You joined the groupbuy");
            //                         toggleJoinButton.setText("Leave Groupbuy");
                          
            //                     }
            //                 } else {
            //                     if(toggleJoinButton.isSelected()){
            //                         GbuyDatabase.getInstance().createGroupbuy(product.getId(), currentUser.getUserID());
            //                         JOptionPane.showMessageDialog(ListViewer.this, "You joined the groupbuy");
            //                         toggleJoinButton.setText("Leave Groupbuy");

                                    
            //                     } else {
            //                         GbuyDatabase.getInstance().deleteGroupbuy(product.getId(), currentUser.getUserID());
            //                         JOptionPane.showMessageDialog(ListViewer.this, "You Left this groupbuy");
            //                         toggleJoinButton.setText("Join Groubuy");
        
            //                     }
            //                 }
            //                 updateCountLabel(countLabel);
            //             }
            //         });
            //     } else {
            //             RoundedPanel completedPanel = new RoundedPanel();
            //             completedPanel.setBackground(GbuyColor.COMPLETED_COLOR);
            //             completedPanel.setShady(false);
            //             JLabel label = new JLabel("completed");
            //             label.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
            //             label.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            //             completedPanel.add(label);
            //             buttonPanels.add(completedPanel, BorderLayout.EAST);


            //         // buttonPanels.add(completedButton, BorderLayout.EAST);
            //     }

            // }
            
            setLayout(new BorderLayout(0, 20));
            add(descScrollPanel, BorderLayout.CENTER);
            add(buttonPanels, BorderLayout.SOUTH);

            // if(product.getProductStatus().equals("expired") && isUser){
            //     JOptionPane.showMessageDialog(null, "Your listing has expired, You may reschedule or delete your listing throught the edit button");
            // }
        }

        // private void updateCountLabel(JLabel label){
        //     SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        //     label.setText("Groupbuy count: " + String.valueOf(spc.userCount) + "/" + String.valueOf(spc.userLimit));    

        // }

        public void enableToggleButton(){
            buttonPanels.add(toggleJoinButton, BorderLayout.EAST);
        }

        public void showExpired(){
            RoundedPanel expiredPanel = new RoundedPanel();
            expiredPanel.setBackground(GbuyColor.EXPIRED_COLOR);
            expiredPanel.setShady(false);
            JLabel label = new JLabel("expired");
            label.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
            label.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            expiredPanel.add(label);
            buttonPanels.add(expiredPanel, BorderLayout.EAST);
        }

        public void showCompleted(){
            RoundedPanel completedPanel = new RoundedPanel();
            completedPanel.setBackground(GbuyColor.COMPLETED_COLOR);
            completedPanel.setShady(false);
            JLabel label = new JLabel("completed");
            label.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
            label.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
            completedPanel.add(label);
            buttonPanels.add(completedPanel, BorderLayout.EAST);
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