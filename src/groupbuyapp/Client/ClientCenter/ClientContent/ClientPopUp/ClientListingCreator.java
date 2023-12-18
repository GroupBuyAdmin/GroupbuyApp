package groupbuyapp.Client.ClientCenter.ClientContent.ClientPopUp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import com.github.lgooddatepicker.components.DateTimePicker;

import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.RoundedButton;
import groupbuyapp.SystemFiles.CustomComponents.RoundedCornerComboBox;
import groupbuyapp.SystemFiles.CustomComponents.RoundedCornerTextArea;
import groupbuyapp.SystemFiles.CustomComponents.RoundedCornerTextField;
import groupbuyapp.SystemFiles.CustomComponents.RoundedPanel;
import groupbuyapp.SystemFiles.Database.GbuyDatabase;
import groupbuyapp.SystemFiles.Database.Product;
import groupbuyapp.SystemFiles.Database.SingleProductContainer;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;
import net.miginfocom.swing.MigLayout;

/**
 * The {@code ListingCreator} class is responsible for creating and editing product listings. 
 * It provides a graphical user interface for users to input details about a product, 
 * such as its name, description, price, and category. Users can also upload an image for the product. 
 * The class handles validation of the input fields and interacts with a database to store the product information. 
 */
public class ClientListingCreator {
    private JFrame mainFrame;
    private JPanel masterPanel;
    private Product product;

    public Product getProduct() {
        return product;
    }

    private CenterPanel centerPanel;

    public CenterPanel getCenterPanel() {
        return centerPanel;
    }

    public static final boolean EDIT = true;
    public static final boolean CREATE = false;

    private boolean editProduct;

    public boolean isEditProduct() {
        return editProduct;
    }

    public JButton getCancelButton(){
        return centerPanel.getDetailsPanel().getUploadPanel().getCancelButton();
    }

    public JButton getUploadButton(){
        return centerPanel.getDetailsPanel().getUploadPanel().getUploadButton();
    }
    
    public JButton getAddPhotoButton(){
        return centerPanel.getImagePanel().getIconButton().getButton();
    }

    public JButton getDeleteButton(){
        return centerPanel.getDetailsPanel().getUploadPanel().getDeleteButton();
    }

    public JFileChooser getFileChooser(){
        return centerPanel.getImagePanel().getIconButton().getFileChooser();
    }

    public int getImagePanelWidth(){
        return centerPanel.getImagePanel().getWidth();
    }

    public int getImagePanelHeight(){
        return centerPanel.getImagePanel().getHeight();
    }
    

    public void close(){
        mainFrame.dispose();
    }

    public JLabel getImage(){
        return centerPanel.getImagePanel().getImageContainer().getImageLabel();
    }



    public ClientListingCreator(){
        this(null);
    }


    public ClientListingCreator(Product product){
        this.product = product;
        if(product != null){
            editProduct = true;
        } else{
            editProduct = false;
        }
        masterPanel = new JPanel();

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        headerPanel.setBackground(Color.white);
    
        JLabel headerLabel = new JLabel();
        if(!editProduct){
            headerLabel.setText("Create Listing");
        } else {
            headerLabel.setText("Edit Listing");
        }
        headerLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(20f));
        headerLabel.setForeground(GbuyColor.MAIN_TEXT_COLOR);
        headerPanel.add(headerLabel);

        centerPanel = new CenterPanel();
        centerPanel.setBackground(Color.white);

        masterPanel.setLayout(new BorderLayout());
        masterPanel.add(headerPanel, BorderLayout.NORTH);
        masterPanel.add(centerPanel, BorderLayout.CENTER);
        masterPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        masterPanel.setBackground(Color.white);

        initFrame();

        if(editProduct){
            mainFrame.setVisible(true);
            centerPanel.initializeWithData();
        }
    }

    private void initFrame(){
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(1500,1000);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setContentPane(masterPanel);
        mainFrame.setVisible(true);
    }


    /**
     * Checks if all the required fields in the CenterPanel are completed by the user.
     *
     * @param c The CenterPanel object that contains the required fields.
     * @return True if all the required fields are completed, false otherwise.
     */
    public boolean completedFields(CenterPanel c) {
        ImagePanel imagePanel = c.getImagePanel();
        DetailsPanel detailsPanel = c.getDetailsPanel();
        ImageIcon imageContainerIcon = (ImageIcon) imagePanel.getImageContainer().getImageLabel().getIcon();
        String nameField = detailsPanel.getFields().getTextFields().getNameTextField().getText();
        String descField = detailsPanel.getFields().getTextFields().getDescTextArea().getText();
        String locField = detailsPanel.getFields().getTextFields().getLocationTextField().getText();
        String priceField = detailsPanel.getFields().getSubDescription().getPriceTextField().getText();
        String userLimitField = detailsPanel.getFields().getSubDescription().userlimitField.getText();
        String dateField = detailsPanel.getFields().getSubDescription().dateTimePicker.getDatePicker().getText();
        String timeField = detailsPanel.getFields().getSubDescription().dateTimePicker.getTimePicker().getText();

        if (imageContainerIcon == null || nameField.isEmpty() || descField.isEmpty() || locField.isEmpty() || priceField.isEmpty() || userLimitField.isEmpty() || dateField.isEmpty() || timeField.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the price field in the CenterPanel is a valid number.
     * If it is not a valid number, displays an error message.
     *
     * @param c The CenterPanel object that contains the details panel.
     * @return Returns true if all fields are valid, otherwise returns false.
     */
    public boolean validFields(CenterPanel c) {
        DetailsPanel detailsPanel = c.getDetailsPanel();
        String message = "Invalid fields: ";
        boolean flag = true;

        try {
            Double.parseDouble(detailsPanel.getFields().getSubDescription().getPriceTextField().getText());
        } catch (NumberFormatException e) {
            message += " {Price}";
            flag = false;
        }

        try{
            Integer.parseInt(detailsPanel.getFields().getSubDescription().userlimitField.getText());
        } catch (NumberFormatException e){
            message += " {User Limit}";
            flag = false;
        }

        if (!flag) {
            JOptionPane.showMessageDialog(c, message);
        }


        return flag;
    }

    class CenterPanel extends JPanel{
        ImagePanel imagePanel;
        DetailsPanel detailsPanel;

        public ImagePanel getImagePanel() {return imagePanel;}
        public DetailsPanel getDetailsPanel() {return detailsPanel;}
        
        public CenterPanel(){
            this.imagePanel = new ImagePanel();
            imagePanel.setBackground(Color.white);
            this.imagePanel.setPreferredSize(new Dimension(150, 350));
            this.detailsPanel = new DetailsPanel();
            imagePanel.setBackground(Color.white);
            
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 0.5;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 0, 20);
            add(imagePanel, gbc);
            
            gbc.gridx++;
            gbc.weightx = 0.4;
            gbc.anchor = GridBagConstraints.EAST;
            add(detailsPanel, gbc);
        }

        /**
         * Resizes an image to fit within a specified width and height while maintaining its aspect ratio.
         * 
         * @param selectedImage The original image to be resized.
         * @return The resized image that fits within the specified width and height while maintaining its aspect ratio.
         */
        private Image resizeImage(ImageIcon selectedImage) {
            int finalWidth;
            int finalHeight;

            int originalWidth = selectedImage.getIconWidth();
            int originalHeight = selectedImage.getIconHeight();

            if (originalWidth > originalHeight) {
                finalWidth = imagePanel.getWidth();
                finalHeight = (originalHeight * finalWidth) / originalWidth;
            } else {
                finalHeight = imagePanel.getHeight();
                finalWidth = (originalWidth * finalHeight) / originalHeight;
            }

            Image image = selectedImage.getImage().getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
            return image;
        }

        /**
         * Populates the fields of the CenterPanel with data from a SingleProductContainer object.
         * 
         * <p>Flow:
         * <p>1. Retrieve the productId from the product object
         * <p>2. Use the productId to fetch the corresponding SingleProductContainer object from the database.
         * <p>3. Set the text of the name, description, and location fields in the detailsPanel to the values from the SingleProductContainer object.
         * <p>4. Set the selected item of the category combo box in the detailsPanel to the value from the SingleProductContainer object.
         * <p>5. Set the text of the price field in the detailsPanel to the value from the SingleProductContainer object.
         * <p>6. Set the original image of the imageContainer in the imagePanel to the byte image from the SingleProductContainer object.
         * <p>7. Resize the original image and set it as the icon of the image label in the imageContainer in the imagePanel.
         * <p>8. Clear the text of the image label in the imageContainer in the imagePanel.
         * <p>9. Refresh the imageContainer in the imagePanel.
         * <p>10. Set the text of the button in the iconButton in the imagePanel to "Change Photo".
         * <p>11. Refresh the iconButton in the imagePanel.
         * 
         */
        private void initializeWithData() {
            int productId = product.getId();
            SingleProductContainer spc = GbuyDatabase.getInstance().getSingleProduct(productId);
            detailsPanel.getFields().getTextFields().getNameTextField().setText(spc.productName);
            detailsPanel.getFields().getTextFields().getDescTextArea().setText(spc.productDescription);
            detailsPanel.getFields().getTextFields().getLocationTextField().setText(spc.productLocation);
            JComboBox<String> comboBox = detailsPanel.getFields().getSubDescription().getComboBox();
            comboBox.setSelectedItem(spc.productCategory);
            detailsPanel.getFields().getSubDescription().getPriceTextField().setText(String.valueOf(spc.productPrice));
            detailsPanel.getFields().getSubDescription().userlimitField.setText(String.valueOf(spc.userLimit));

            LocalDateTime dateTime = spc.deadlineStamp.toLocalDateTime();
            LocalDate localDate = dateTime.toLocalDate();
            LocalTime localTime = dateTime.toLocalTime();
            detailsPanel.getFields().getSubDescription().dateTimePicker.getDatePicker().setDate(localDate);
            detailsPanel.getFields().getSubDescription().dateTimePicker.getTimePicker().setTime(localTime);

            imagePanel.getImageContainer().setOriginalImage(spc.byteImage);
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(spc.byteImage).getImage());
            ImageIcon resizedImageIcon = new ImageIcon(resizeImage(imageIcon));
            imagePanel.getImageContainer().getImageLabel().setIcon(resizedImageIcon);
            imagePanel.getImageContainer().getImageLabel().setText("");
            imagePanel.imageContainer.refresh();
            imagePanel.iconButton.getButton().setText("Change Photo");
            imagePanel.getIconButton().refresh();
        }

    }

    class ImagePanel extends JPanel{
        ImageContainer imageContainer;
        IconButton iconButton;

        public IconButton getIconButton() {return iconButton;}
        public ImageContainer getImageContainer() {return imageContainer;}

        public ImagePanel(){
            setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            this.imageContainer = new ImageContainer();
            imageContainer.setBackground(Color.white);
            this.iconButton = new IconButton();
            iconButton.setBackground(Color.white);
            
            setLayout(new BorderLayout(0,20));
            add(imageContainer, BorderLayout.CENTER);
            add(iconButton, BorderLayout.SOUTH);
        }
        
        class ImageContainer extends JPanel{
            byte[] originalImage;
            JLabel imageLabel;
            
            public byte[] getOriginalImage() {return originalImage;}
            public void setOriginalImage(byte[] originalImage) {this.originalImage = originalImage;}

            public JLabel getImageLabel() {return imageLabel;}

            public ImageContainer(){
                imageLabel = new JLabel("No Photo");
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
                imageLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));
                setLayout(new BorderLayout());
   
                RoundedPanel r = new RoundedPanel();
                r.setLayout(new BorderLayout());
                r.setShady(false);
                r.add(imageLabel, BorderLayout.CENTER);
                r.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);

                add(r, BorderLayout.CENTER);
            }

            public void refresh(){
                revalidate();
                repaint();
            }
        }

        class IconButton extends JPanel{
            RoundedButton button;
            JFileChooser fileChooser;
            boolean hasSelected = false;

            public JFileChooser getFileChooser() {return fileChooser;}
            public JButton getButton() {return button;}
            public void setHasSelected(boolean x){ hasSelected = x; }
            public boolean HasSelected(){ return hasSelected; }

            public IconButton(){
                this.button = new RoundedButton("Add Photo");
                button.setBorderColor(GbuyColor.MAIN_COLOR);
                button.setButtonColor(Color.white);
                button.setForeground(GbuyColor.MAIN_COLOR);
                button.setButtonFont(GbuyFont.MULI_BOLD.deriveFont(14f));

                this.fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new ImageFileFilter());
                setLayout(new FlowLayout(FlowLayout.LEFT));
                add(button);
            }

            public void refresh(){
                revalidate();
                repaint();
            }

        }
    }

    class DetailsPanel extends JPanel{
        Fields fields;
        UploadPanel uploadPanel;

        public UploadPanel getUploadPanel() {return uploadPanel;}
        public Fields getFields() {return fields;}

        public DetailsPanel(){
            setLayout(new BorderLayout());
            this.fields = new Fields();
            fields.setBackground(Color.white);
            this.uploadPanel = new UploadPanel();
            uploadPanel.setBackground(Color.white);

            add(fields, BorderLayout.CENTER);
            add(uploadPanel, BorderLayout.SOUTH);
        }

        //inside Details Panel
        class Fields extends JPanel{
            TextFields textFields;
            SubDescription subDescription;
            
            public TextFields getTextFields() {return textFields;}
            public SubDescription getSubDescription() {return subDescription;}

            public Fields(){
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                this.textFields = new TextFields();
                textFields.setBackground(Color.white);
                textFields.setPreferredSize(new Dimension(25, 100));
                this.subDescription = new SubDescription();
                subDescription.setBackground(Color.white);

                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 0.5;
                gbc.weighty = 1.5;
                gbc.fill = GridBagConstraints.BOTH;
                
                add(textFields, gbc);

                gbc.gridy++;
                gbc.weighty = 0.2;
                add(subDescription, gbc);
            }

            //product name and product description
            class TextFields extends JPanel{
                RoundedCornerTextField nameTextField;
                RoundedCornerTextArea descTextArea;
                RoundedCornerTextField locationTextField;
                
                public RoundedCornerTextField getLocationTextField() {return locationTextField;}
                public JTextField getNameTextField() {return nameTextField;}
                public JTextArea getDescTextArea() {return descTextArea;}

                public TextFields(){
                    
                    JLabel nameFieldLabel = new JLabel("Product Name");
                    nameFieldLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
                    nameFieldLabel.setHorizontalAlignment(JLabel.LEFT);

                    this.nameTextField = new RoundedCornerTextField();
                    nameTextField.setText("Enter product name");
                    nameTextField.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
                    nameTextField.setForeground(GbuyColor.MAIN_COLOR);
                    nameTextField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                    nameTextField.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
                    nameTextField.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            if(!editProduct && nameTextField.getText().isEmpty())
                                nameTextField.setText("");
                        }
                        @Override
                        public void focusLost(FocusEvent e) {}
                        
                    });


                    //inside descFieldPanel
                    JLabel descFieldLabel = new JLabel("Description");
                    descFieldLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
                    descFieldLabel.setHorizontalAlignment(JLabel.LEFT);


                    this.descTextArea = new RoundedCornerTextArea();
                    descTextArea.setText("Enter description");
                    descTextArea.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
                    descTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    descTextArea.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
                    descTextArea.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            if(!editProduct && nameTextField.getText().isEmpty())
                                descTextArea.setText("");
                        }
                        @Override
                        public void focusLost(FocusEvent e) {}
                        
                    });

                    JScrollPane descScrollPanel = new JScrollPane(descTextArea);
                    descScrollPanel.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
                    descScrollPanel.setBorder(BorderFactory.createEmptyBorder());

                    JLabel locationFieldLabel = new JLabel("Location");
                    locationFieldLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
                    locationFieldLabel.setHorizontalAlignment(JLabel.LEFT);
                    this.locationTextField = new RoundedCornerTextField();
                    locationTextField.setText("Enter Location");
                    locationTextField.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
                    locationTextField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                    locationTextField.setForeground(GbuyColor.MAIN_COLOR);
                    locationTextField.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
                    locationTextField.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {   
                            if(!editProduct && nameTextField.getText().isEmpty())
                                locationTextField.setText("");
                        }
                        @Override
                        public void focusLost(FocusEvent e) {}
                        
                    });

                    //layout
                    setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.weightx = 0.5;

                    gbc.weighty = 0.2;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.insets = new Insets(0, 0, 5, 0);
                    add(nameFieldLabel, gbc);
                    
                    gbc.gridy++;
                    gbc.weighty = 0.5;
                    gbc.insets = new Insets(0, 0, 25, 0);
                    add(nameTextField, gbc);
                    
                    gbc.weighty = 0.2;
                    gbc.gridy++;
                    gbc.insets = new Insets(0, 0, 5, 0);
                    add(descFieldLabel, gbc);
                    
                    gbc.weighty = 10;
                    gbc.gridy++;
                    gbc.insets = new Insets(0, 0, 25, 0);
                    add(descScrollPanel, gbc);

                    gbc.gridy++;
                    gbc.weighty = 0.2;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.insets = new Insets(0, 0, 5, 0);
                    add(locationFieldLabel, gbc);

                    gbc.gridy++;
                    gbc.weighty = 0.5;
                    gbc.insets = new Insets(0, 0, 25, 0);
                    add(locationTextField, gbc);
                }

            }

            //product price, category, quantity
            class SubDescription extends JPanel{
                RoundedCornerTextField priceTextField;
                RoundedCornerComboBox comboBox;
                RoundedCornerTextField userlimitField;
                DateTimePicker dateTimePicker;

                public JComboBox<String> getComboBox() {return comboBox;}
                public JTextField getPriceTextField() {return priceTextField;}

                public SubDescription(){

                    //for categories drop down
                    JLabel categorylabel = new JLabel("Category");
                    categorylabel.setHorizontalAlignment(JLabel.LEFT);
                    categorylabel.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
  
                    String[] categories = {"Electronics", "Clothing", "Books", "Home and Kitchen", "Sports"};
                    this.comboBox = new RoundedCornerComboBox(categories);
                    ImageIcon img = new ImageIcon("src/groupbuyapp/Client/Center/Content/listingDisplayer/img/Arrow-Down.png");
                    Icon customDropdownIcon = new ImageIcon(img.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
                    comboBox.setCustomDropdownIcon(customDropdownIcon);
                    comboBox.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
                    comboBox.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));

                    RoundedPanel comboBoxPanel = new RoundedPanel();
                    comboBoxPanel.setShady(false);
                    comboBoxPanel.setArcs(new Dimension(10, 10));
                    comboBoxPanel.setLayout(new BorderLayout());
                    comboBoxPanel.add(comboBox, BorderLayout.CENTER);
                    comboBoxPanel.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);

                    //for price
                    JLabel priceLabel = new JLabel("Price $");
                    priceLabel.setHorizontalAlignment(JLabel.LEFT);
                    priceLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
                    
                    
                    this.priceTextField = new RoundedCornerTextField();
                    priceTextField.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
                    priceTextField.setForeground(GbuyColor.MAIN_COLOR);
                    priceTextField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                    priceTextField.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
                    priceTextField.setText("0.00");
                    priceTextField.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            if(!editProduct && priceTextField.getText().isEmpty())
                                priceTextField.setText("");
                        }
                        @Override
                        public void focusLost(FocusEvent e) {
                        }
                    });

                    JLabel userLimitLabel = new JLabel("Join Limit ");
                    this.userlimitField = new RoundedCornerTextField();
                    userlimitField.setText("0");
                    userlimitField.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
                    userlimitField.setForeground(GbuyColor.MAIN_COLOR);
                    userlimitField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                    userlimitField.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
                    userlimitField.addFocusListener(new FocusListener() {

                        @Override
                        public void focusGained(FocusEvent e) {
                            if(!editProduct && userlimitField.getText().isEmpty())
                                userlimitField.setText("");
                        }

                        @Override
                        public void focusLost(FocusEvent e) {

                        }
                        
                    });

                    JLabel dateTimePickerLabel = new JLabel("Deadline");
                    dateTimePicker = new DateTimePicker();

                    setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();

                    gbc.fill = GridBagConstraints.BOTH;
                    
                    //row 1
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    gbc.weightx = 0.5;
                    gbc.weighty = 0.5;
                    gbc.gridwidth = 1;
                    gbc.gridx = 0;
                    gbc.insets = new Insets(0, 0, 5, 0);
                    add(priceLabel, gbc);
                    
                    gbc.weightx = 0.5;
                    gbc.weighty = 0.5;
                    gbc.gridwidth = 1;
                    gbc.gridx++;
                    add(categorylabel, gbc);

                    //row 2
                    gbc.weightx = 0.5;
                    gbc.weighty = 0.5;
                    gbc.gridwidth = 1;
                    gbc.gridy++;
                    gbc.gridx = 0;
                    gbc.insets = new Insets(0, 0, 0, 15);
                    add(priceTextField, gbc);
                    
                    gbc.weightx = 0.5;
                    gbc.weighty = 0.5;
                    gbc.gridwidth = 1;
                    gbc.gridx++;
                    gbc.insets = new Insets(0, 0, 0, 0);
                    add(comboBoxPanel, gbc);

                    //row 3
                    gbc.weightx = 0.5;
                    gbc.weighty = 0.5;
                    gbc.gridwidth = 1;
                    gbc.gridy++;
                    gbc.gridx = 0;
                    gbc.insets = new Insets(0, 0, 5, 0);
                    add(userLimitLabel, gbc);

                    gbc.gridx++;
                    add(dateTimePickerLabel, gbc);

                    //row 4
                    gbc.weightx = 0.5;
                    gbc.weighty = 0.5;
                    gbc.gridwidth = 1;
                    gbc.gridy++;
                    gbc.gridx = 0;
                    gbc.insets = new Insets(0, 0, 0, 15);
                    add(userlimitField, gbc);

                    gbc.weightx = 0.5;
                    gbc.weighty = 0.5;
                    gbc.gridwidth = 1;
                    gbc.gridx++;
                    gbc.insets = new Insets(0, 0, 0, 0);
                    add(dateTimePicker, gbc);
   
                }
            }
        }

        class UploadPanel extends JPanel{
            RoundedButton uploadButton;
            RoundedButton cancelButton;
            RoundedButton deleteButton;

            public RoundedButton getDeleteButton() {return deleteButton;}
            public JButton getUploadButton() {return uploadButton;}
            public JButton getCancelButton() {return cancelButton;}

            public UploadPanel(){
                this.uploadButton = new RoundedButton("Upload");
                uploadButton.setButtonColor(Color.decode("#3EF050"));
                uploadButton.setForeground(Color.white);
                uploadButton.setDrawBorder(false);  
                uploadButton.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));

                this.cancelButton = new RoundedButton("Cancel");
                cancelButton.setButtonColor(Color.white);
                cancelButton.setForeground(GbuyColor.MAIN_COLOR);
                cancelButton.setDrawBorder(false);
                cancelButton.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));

                Image resizedImage = new ImageIcon("src/groupbuyapp/Client/Center/Content/ListingContainers/img/delete.png").getImage().getScaledInstance(13, 16, Image.SCALE_SMOOTH);
                this.deleteButton = new RoundedButton("delete", new ImageIcon(resizedImage));
                deleteButton.setIconTextGap(10);
                deleteButton.setDrawBorder(false);
                deleteButton.setButtonColor(GbuyColor.MAIN_COLOR);
                deleteButton.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
                deleteButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);

                if(!editProduct){                    
                    setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
                    add(uploadButton);                               
                    add(cancelButton);
                } else {
                    setLayout(new MigLayout("fillx"));
                    add(deleteButton, "gapleft 0");
                    JPanel rightButtons = new JPanel();
                    rightButtons.setOpaque(false);
                    rightButtons.add(uploadButton);
                    rightButtons.add(cancelButton);
                    add(rightButtons, "gapright 0, align right");
                }

            }

        }
    }

    //helper class for imagefile filter
    private static class ImageFileFilter extends FileFilter {
        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true; // Allow directories to be displayed
            }

            String extension = getExtension(file);
            return extension != null && (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif"));
        }

        @Override
        public String getDescription() {
            return "Image Files (jpg, jpeg, png, gif)";
        }

        private String getExtension(File file) {
            String fileName = file.getName();
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                return fileName.substring(dotIndex + 1).toLowerCase();
            }
            return null;
        }
    }


}
