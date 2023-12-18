package groupbuyapp.NewClient.ClientCenter;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import groupbuyapp.NewClient.ClientCenter.ClientContent.ClientContent;
import groupbuyapp.NewClient.ClientCenter.ClientNavBar.ClientNavBar;

public class ClientCenter extends JPanel{
    private ClientNavBar clientNavBar;
    private ClientContent clientContent;

    public ClientNavBar getClientNavBar() {
        return clientNavBar;
    }

    public ClientContent getClientContent() {
        return clientContent;
    }

    public ClientCenter() {
        this.clientNavBar = new ClientNavBar();
        this.clientContent = new ClientContent();

        setLayout(new BorderLayout());

        add(clientNavBar, BorderLayout.NORTH);
        add(clientContent, BorderLayout.CENTER);
    }
    

}
