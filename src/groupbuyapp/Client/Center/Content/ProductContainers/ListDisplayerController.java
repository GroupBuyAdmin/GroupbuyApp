package groupbuyapp.Client.Center.Content.ProductContainers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import groupbuyapp.Client.Center.Content.ListingDisplayer.ListDisplayer;
import groupbuyapp.Client.LogIn.misc.User;

public class ListDisplayerController {
    private ListDisplayer listDisplayer;
    private User currentUser;

    public ListDisplayer getListDisplayer() {
        return listDisplayer;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ListDisplayerController(ListDisplayer listDisplayer, User currentUser){
        this.listDisplayer = listDisplayer;
        this.currentUser = currentUser;
        init();
    }

    public void init(){
        List<ProductPanel> containers = listDisplayer.getAllContainers();
        for(ProductPanel p : containers){
            p.addMouseListener(new CreateViewer(currentUser, p.getProduct()));

        }

    }

    class CreateViewer extends MouseAdapter{
        private User user;
        private Product product;

        public CreateViewer(User user, Product product){
            this.user = user;
            this.product = product;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            ListViewerFactory lViewerFactory = new ListViewerFactory();
            ListViewer madeListViewer = lViewerFactory.create(user, product);

            listDisplayer.getCardContainer().add(madeListViewer, ListDisplayer.PRODUCT_VIEW);
            listDisplayer.getcLayout().show(listDisplayer.getCardContainer(), ListDisplayer.PRODUCT_VIEW);

            var backButton = madeListViewer.getBackButton();
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    listDisplayer.getcLayout().show(listDisplayer.getCardContainer(), ListDisplayer.LIST_VIEW);
                }
                
            });
            
        }
    }
}
