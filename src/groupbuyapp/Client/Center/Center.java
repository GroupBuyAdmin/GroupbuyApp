package groupbuyapp.Client.Center;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import groupbuyapp.Client.Center.Content.Content;
import groupbuyapp.Client.Center.TopNavBar.TopNavBar;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.SideBar.SideBar;

public class Center extends JPanel{
    private Content content;
    private TopNavBar topNavBar;
    
    public Content getContent() {
        return content;
    }

    public TopNavBar getTopNavBar() {
        return topNavBar;
    }

    public Center(User currentUser, SideBar sideBar){
        this.content = new Content(currentUser, sideBar);
        this.topNavBar = new TopNavBar();

        setLayout(new BorderLayout());
        add(topNavBar, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }
}

