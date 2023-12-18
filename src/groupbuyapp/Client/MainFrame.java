package groupbuyapp.Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import groupbuyapp.Client.Center.Center;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Client.SideBar.Buttons.Buttons;
import groupbuyapp.NewClient.LogIn.AccountSetup;
import groupbuyapp.NewClient.LogIn.User;

public class MainFrame extends JFrame{
    private User currentUser;
    
    private Center center;
    private SideBar sideBar;
    
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
        initializeSideBarControls();
        this.center = new Center(currentUser, sideBar);

        initTopNavBarControls();
        Container pane = getContentPane();

        pane.setLayout(new BorderLayout());
        pane.add(sideBar, BorderLayout.WEST);
        pane.add(center, BorderLayout.CENTER);

    }

    private void setFrame(){
        setSize(1660, 880);
        // pack();
        setMinimumSize(new Dimension(1700, 800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeSideBarControls(){
        sideBar.getButtons().getHomeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.getContent().showHome();
                sideBar.getButtons().setSelected(Buttons.HOME);
            }
        });

        sideBar.getButtons().getMyListingsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.getContent().showMyListings();
                sideBar.getButtons().setSelected(Buttons.MY_LISTINGS);
            }
        });

        sideBar.getButtons().getMyGroupbuysButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.getContent().showMyGroupBuys();
                sideBar.getButtons().setSelected(Buttons.MY_GROUPBUYS);
            }
        });

        sideBar.getButtons().getBrowseGroupbuysButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.getContent().showBrowseGroupbuys();
                sideBar.getButtons().setSelected(Buttons.BROWSE_GROUPBUYS);
            }
        });

        sideBar.getLogo().getLogoContainer().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                center.getContent().showHome();
                sideBar.getButtons().setSelected(Buttons.HOME);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    private void initTopNavBarControls(){
        var profileIcon = center.getTopNavBar().getProfileIcon();
        profileIcon.setToolTipText(currentUser.getUserName());

        var signOutButton = center.getTopNavBar().getSignOutIcon();
        signOutButton.addMouseListener(new SignOutListener());

    }

    class SignOutListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            int option = JOptionPane.showConfirmDialog(
                    null, 
                    "Do you want to sign out?", 
                    "Sign Out",
                    JOptionPane.YES_NO_OPTION); 

            if (option == JOptionPane.YES_OPTION) {
                System.out.println("Signing out");
                dispose();
                runAccountSetup();
            } else {
                System.out.println("User clicked No");
            }
        }
    }

    private void runAccountSetup(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AccountSetup();
            }
            
        });
    }

}
