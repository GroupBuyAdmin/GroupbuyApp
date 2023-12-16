package groupbuyapp.Client.Center.Content.ProductContainers;

import groupbuyapp.Client.LogIn.misc.User;
import groupbuyapp.Misc.Database.GbuyDatabase;
import groupbuyapp.Misc.Database.SingleProductContainer;

public class ListViewerFactory {
    public static final int USER = 1;
    public static final int VIEWER = 2;

    private ListViewer createdListViewer;

    public ListViewer create(User currentUser, Product product){
        createdListViewer = new ListViewer();
        if(currentUser.getUserID() == product.getCreatorID()){
            return user(currentUser, product);
        } else {
            return viewer(product);
        }
    }

    public ListViewer user(User currentUser, Product product){
        createdListViewer.getImageContainer().setIcon(product.getImageIcon());
        createdListViewer.getProductNameLabel().setText(product.getName());
        createdListViewer.getProductPriceLabel().setText(product.getPrice());
        createdListViewer.getCreatorLabel().setText(currentUser.getUserName());
        createdListViewer.getLocationLabel().setText(product.getLocation());
        createdListViewer.getDescriptionArea().setText(product.getDescription());
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        createdListViewer.getCountLabel().setText(spc.userCount + "/" + spc.userLimit);
        if(spc.userCount == spc.userLimit){
            createdListViewer.showCompleted();
        } else if(spc.productStatus.equals("expired") ){
            createdListViewer.showExpired();
        } 
        return createdListViewer;
    }

    public ListViewer viewer(Product product){
        createdListViewer.getImageContainer().setIcon(product.getImageIcon());
        createdListViewer.getProductNameLabel().setText(product.getName());
        createdListViewer.getProductPriceLabel().setText(product.getPrice());
        createdListViewer.getCreatorLabel().setText(GbuyDatabase.getInstance().getUserName(product.getCreatorID()));
        createdListViewer.getLocationLabel().setText(product.getLocation());
        createdListViewer.getDescriptionArea().setText(product.getDescription());
        SingleProductContainer spc = GbuyDatabase.getInstance().getProductUserCountAndLimit(product.getId());
        createdListViewer.getCountLabel().setText(spc.userCount + "/" + spc.userLimit);
        createdListViewer.remove(createdListViewer.getEditButton());
        if(spc.userCount == spc.userLimit){
            createdListViewer.showCompleted();
        } else if(spc.productStatus.equals("expired") ){
            createdListViewer.showExpired();
        } else {
            createdListViewer.enableToggleButton();
        }
        createdListViewer.revalidate();
        createdListViewer.repaint();
        return createdListViewer;
    }
}
