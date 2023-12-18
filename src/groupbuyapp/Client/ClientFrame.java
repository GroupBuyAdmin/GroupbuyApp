package groupbuyapp.Client;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import groupbuyapp.Client.ClientCenter.ClientCenter;
import groupbuyapp.Client.ClientSidebar.ClientSidebar;
import groupbuyapp.Client.LogIn.User;

public class ClientFrame extends JFrame{
    private User currentUser;
    private ClientSidebar clientSidebar;
    private ClientCenter clientCenter;
    private ClientController clientController;

    public ClientFrame(User user){
        this.currentUser = user;
        this.clientCenter = new ClientCenter();
        this.clientSidebar = new ClientSidebar();
        this.clientController = new ClientController(clientSidebar, clientCenter, this, currentUser);
        clientController.init();

        Container container = getContentPane();

        container.setLayout(new BorderLayout());
        container.add(clientSidebar, BorderLayout.WEST);
        container.add(clientCenter, BorderLayout.CENTER);

        initFrame();
    }

    private void initFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
