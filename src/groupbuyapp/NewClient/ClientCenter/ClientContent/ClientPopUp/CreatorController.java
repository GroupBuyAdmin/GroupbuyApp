package groupbuyapp.NewClient.ClientCenter.ClientContent.ClientPopUp;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import groupbuyapp.Misc.Database.GbuyDatabase;
import groupbuyapp.Misc.Database.SingleProductContainer;
import groupbuyapp.NewClient.ClientController;
import groupbuyapp.NewClient.ClientCenter.ClientContent.ClientDisplayers.ClientListingViewer;
import groupbuyapp.NewClient.ClientCenter.ClientContent.ClientPopUp.ClientListingCreator.DetailsPanel;
import groupbuyapp.NewClient.ClientCenter.ClientContent.ClientPopUp.ClientListingCreator.ImagePanel;
import groupbuyapp.NewClient.LogIn.User;

public class CreatorController {
    private ClientListingCreator clientListingCreator;
    private User currentUser;
    private ClientController clientController;
    private ClientListingViewer clientListingViewer;

    public ClientListingCreator getClientListingCreator() {
        return clientListingCreator;
    }

    public CreatorController(ClientListingCreator clientListingCreator, User currentUser, ClientController clientController){
        this(clientListingCreator, currentUser, clientController, null);
    }

    public CreatorController(ClientListingCreator clientListingCreator, User currentUser, ClientController clientController, ClientListingViewer clientListingViewer){
        this.clientListingCreator = clientListingCreator;
        this.currentUser = currentUser;
        this.clientController = clientController;
        this.clientListingViewer = clientListingViewer;
    }

    public void init(){
        var cancelButton = clientListingCreator.getCancelButton();
        var addPhotoButton = clientListingCreator.getAddPhotoButton();
        var uploadButton = clientListingCreator.getUploadButton();

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientListingCreator.close();
            }
            
        });

        addPhotoButton.addActionListener(new PhotoChooser());

        uploadButton.addActionListener(new Uploader());

    }

    class PhotoChooser implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            var fileChooser = clientListingCreator.getFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                clientListingCreator.getCenterPanel().getImagePanel().getIconButton().setHasSelected(true);
                File selectedFile = fileChooser.getSelectedFile();
                ImageIcon selectedImageIcon = new ImageIcon(selectedFile.getPath());
                Image resizedImage = resizeImage(selectedImageIcon);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                clientListingCreator.getImage().setIcon(resizedIcon);
                clientListingCreator.getCenterPanel().getImagePanel().getImageContainer().refresh();
                clientListingCreator.getAddPhotoButton().setText("Change Photo");
                clientListingCreator.getCenterPanel().getImagePanel().getIconButton().refresh();
            }
        }
        
    }

    private Image resizeImage(ImageIcon selectedImage) {
        int finalWidth;
        int finalHeight;

        int originalWidth = selectedImage.getIconWidth();
        int originalHeight = selectedImage.getIconHeight();

        if (originalWidth > originalHeight) {
            finalWidth = clientListingCreator.getImagePanelWidth();
            finalHeight = (originalHeight * finalWidth) / originalWidth;
        } else {
            finalHeight = clientListingCreator.getImagePanelHeight();
            finalWidth = (originalWidth * finalHeight) / originalHeight;
        }

        Image image = selectedImage.getImage().getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
        return image;
    }

    class Uploader implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(completedFields() && validFields()){
                uploadProductToDatabase();
                clientListingCreator.close();
            } else {
                JOptionPane.showMessageDialog(null, "Please complete all fields");
            }
        }
        
    }

    public boolean completedFields() {
        ImagePanel imagePanel = clientListingCreator.getCenterPanel().getImagePanel();
        DetailsPanel detailsPanel = clientListingCreator.getCenterPanel().getDetailsPanel();
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

    public boolean validFields() {
        DetailsPanel detailsPanel = clientListingCreator.getCenterPanel().getDetailsPanel();
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
            JOptionPane.showMessageDialog(null, message);
        }


        return flag;
    }

    private void uploadProductToDatabase() {
        SingleProductContainer spc = new SingleProductContainer();
        spc.productName = clientListingCreator.getCenterPanel().getDetailsPanel().getFields().getTextFields().getNameTextField().getText();
        spc.productDescription = clientListingCreator.getCenterPanel().getDetailsPanel().getFields().getTextFields().getDescTextArea().getText();
        spc.productLocation = clientListingCreator.getCenterPanel().getDetailsPanel().getFields().getTextFields().getLocationTextField().getText();
        JComboBox<String> comboBox = clientListingCreator.getCenterPanel().getDetailsPanel().getFields().getSubDescription().getComboBox();
        spc.productCategory = (String) comboBox.getSelectedItem();
        spc.productPrice = Double.parseDouble(clientListingCreator.getCenterPanel().getDetailsPanel().getFields().getSubDescription().getPriceTextField().getText());
        spc.userLimit = Integer.parseInt(clientListingCreator.getCenterPanel().getDetailsPanel().getFields().getSubDescription().userlimitField.getText());
        String date = clientListingCreator.getCenterPanel().getDetailsPanel().getFields().getSubDescription().dateTimePicker.getDatePicker().getText();
        String time = clientListingCreator.getCenterPanel().getDetailsPanel().getFields().getSubDescription().dateTimePicker.getTimePicker().getText();
        spc.deadlineString = formatDateString(date + " " + time);

        if (clientListingCreator.getCenterPanel().getImagePanel().getIconButton().HasSelected()) {
            spc.selectedFile = clientListingCreator.getCenterPanel().getImagePanel().getIconButton().getFileChooser().getSelectedFile();
        } else {
            spc.selectedFile = createTempFile(clientListingCreator.getCenterPanel().getImagePanel().getImageContainer().getOriginalImage());
        }

        if (!clientListingCreator.isEditProduct()) {
            spc.creatorID = currentUser.getUserID();
            spc.productStatus = "ongoing";
            GbuyDatabase.getInstance().insertProduct(spc);
            JOptionPane.showMessageDialog(null, spc.productName + " was added");
        } else {
            spc.productStatus = clientListingCreator.getProduct().getProductStatus();
            GbuyDatabase.getInstance().editProduct(spc, clientListingCreator.getProduct().getId());
            JOptionPane.showMessageDialog(null, "product " + clientListingCreator.getProduct().getId() + " was edited");
        }
        var myListings = clientController.getClientCenter().getClientContent().getMyListings();
        myListings.setProductPanels(clientController.createMyListingPanels());
        myListings.revalidate();
        myListings.repaint();

        if(clientListingViewer != null){
            ClientListingViewer c = clientController.createViewerForMyListing(GbuyDatabase.getInstance().getOneProduct(clientListingCreator.getProduct().getId()));
            var content = clientController.getClientCenter().getClientContent();
            content.getCardContainer().add(c, "new view");
            content.getCardLayout().show(content.getCardContainer(), "new view");
        }
    }

    public String formatDateString(String inputDateString) {
        try {
            // Parse the input date string
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM dd, yyyy h:mma");
            Date date = inputFormat.parse(inputDateString);

            // Format the date into the desired output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return outputFormat.format(date);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }

    private File createTempFile(byte[] fileData) {
        File tempFile = null;
        FileOutputStream fos = null;

        try {
            // Create a temporary file
            tempFile = File.createTempFile("temp", ".tmp");

            // Write the byte array to the temporary file
            fos = new FileOutputStream(tempFile);
            fos.write(fileData);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the FileOutputStream if it's open
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return tempFile;
    }
}
