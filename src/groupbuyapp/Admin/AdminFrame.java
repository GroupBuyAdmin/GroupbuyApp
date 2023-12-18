package groupbuyapp.Admin;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import groupbuyapp.Admin.AdminContent.AContent;
import groupbuyapp.Admin.AdminSideBar.ASidebar;
import groupbuyapp.Admin.AdminTopNav.AtopNavbar;


public class AdminFrame extends JFrame{
    private AtopNavbar aTopNavBar;
    private AContent aContent;
    private ASidebar aSidebar;
    private AdminController adminController;
    
    
    public AdminFrame(){
        this.aTopNavBar = new AtopNavbar();
        this.aContent = new AContent();
        this.aSidebar = new ASidebar();
        this.adminController = new AdminController(aTopNavBar, aContent, aSidebar, this);
        adminController.init();

        initlayout();
        initFrame();
    }

    private void initlayout(){
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(aTopNavBar, BorderLayout.NORTH);
        c.add(aContent, BorderLayout.CENTER);
        c.add(aSidebar, BorderLayout.WEST);
    }

    private void initFrame(){
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
