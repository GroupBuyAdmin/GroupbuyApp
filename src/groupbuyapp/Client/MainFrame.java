package groupbuyapp.Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import groupbuyapp.Client.Center.Center;
import groupbuyapp.Client.LogIn.misc.User;
import groupbuyapp.Client.SideBar.SideBar;

public class MainFrame extends JFrame{
    private User currentUser;
    private MainFrameController mainFrameController;
    private Center center;
    private SideBar sideBar;
    
    public MainFrameController getMainFrameController() {
        return mainFrameController;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public MainFrame(){
        this(null);
    }

    public MainFrame(User user){
        this.currentUser = user;
        setFrame();
        this.sideBar = new SideBar();
        this.center = new Center(currentUser, sideBar);
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(sideBar, BorderLayout.WEST);
        pane.add(center, BorderLayout.CENTER);
        this.mainFrameController = new MainFrameController(center, sideBar);
        mainFrameController.init();

    }

    private void setFrame(){
        setSize(1660, 880);
        // pack();
        setMinimumSize(new Dimension(1700, 800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
