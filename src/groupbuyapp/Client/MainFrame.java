package groupbuyapp.Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import groupbuyapp.Client.Center.Center;
import groupbuyapp.Client.Center.Content.MyListings.MyListings;
import groupbuyapp.Client.SideBar.SideBar;

public class MainFrame extends JFrame{
    private Center center;
    private SideBar sideBar;

    public MainFrame(){
        setFrame();
        this.sideBar = new SideBar();
        this.center = new Center();

        Container pane = getContentPane();

        pane.setLayout(new BorderLayout());
        pane.add(sideBar, BorderLayout.WEST);
        pane.add(center, BorderLayout.CENTER);

        initializeSideBarControls();

    }

    private void setFrame(){
        setSize(1660, 880);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeSideBarControls(){
        sideBar.getButtons().getHomeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.getContent().showHome();
                sideBar.getButtons().setSelected(1);
            }
        });

        sideBar.getButtons().getMyListingsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.getContent().showMyListings();
                var myListingContainer = center.getContent().getMyListings().getCardContainer();
                var myListingCardLayout = center.getContent().getMyListings().getcLayout();
                myListingCardLayout.show(myListingContainer, MyListings.MY_LISTING);
                sideBar.getButtons().setSelected(2);
            }
        });

        sideBar.getButtons().getMyGroupbuysButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.getContent().showMyGroupBuys();
                sideBar.getButtons().setSelected(3);
            }
        });

        sideBar.getButtons().getBrowseGroupbuysButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.getContent().showBrowseGroupbuys();
                sideBar.getButtons().setSelected(4);
            }
        });

        sideBar.getLogo().getLogoContainer().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                center.getContent().showHome();
                sideBar.getButtons().setSelected(1);
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
