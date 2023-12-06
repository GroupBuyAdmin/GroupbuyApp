package groupbuyapp.Client.Center;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import groupbuyapp.Client.Center.Content.Content;
import groupbuyapp.Client.Center.TopNavBar.TopNavBar;

public class Center extends JPanel{
    private Content content;
    private TopNavBar topNavBar;
    
    public Content getContent() {
        return content;
    }

    public TopNavBar getTopNavBar() {
        return topNavBar;
    }

    public Center(){
        this.content = new Content();
        this.topNavBar = new TopNavBar();

        setLayout(new BorderLayout());
        add(topNavBar, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }
}
