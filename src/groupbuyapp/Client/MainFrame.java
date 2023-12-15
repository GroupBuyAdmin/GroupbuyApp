package groupbuyapp.Client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import groupbuyapp.Client.Center.Center;
import groupbuyapp.Client.Center.Content.MyListings.MyListings;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Client.SideBar.Buttons.Buttons;

public class MainFrame extends JFrame{
    private User currentUser;
    
    public User getCurrentUser() {
        return currentUser;
    }

    private Center center;
    private SideBar sideBar;

    public MainFrame(){
        this(null);
    }

    public MainFrame(User user){
        this.currentUser = user;
        setFrame();
        this.sideBar = new SideBar();
        initializeSideBarControls();
        this.center = new Center(currentUser, sideBar);

        Container pane = getContentPane();

        pane.setLayout(new BorderLayout());
        pane.add(sideBar, BorderLayout.WEST);
        pane.add(center, BorderLayout.CENTER);


    }

    private void setFrame(){
        setSize(1660, 880);
        // pack();
        // setMinimumSize(new Dimension(1700, 800));
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
                var myListingContainer = center.getContent().getMyListings().getCardContainer();
                var myListingCardLayout = center.getContent().getMyListings().getcLayout();
                myListingCardLayout.show(myListingContainer, MyListings.MY_LISTING);
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
}
