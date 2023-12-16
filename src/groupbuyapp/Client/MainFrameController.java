package groupbuyapp.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import org.w3c.dom.events.MouseEvent;

import groupbuyapp.Client.Center.Center;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Client.SideBar.Buttons.Buttons;

public class MainFrameController {
    private Center center;
    private SideBar sidebar;

    public MainFrameController(Center center, SideBar sideBar){
        this.center = center;
        this.sidebar = sideBar;
    }

    public void init(){
        initSideBarControls();
        initCenterControls();
    }

    private void initSideBarControls(){
        var homeButton = sidebar.getButtons().getHomeButton();
        homeButton.addActionListener(new SelectSidebar(SelectSidebar.HOME));

        var myListingsButton = sidebar.getButtons().getMyListingsButton();
        myListingsButton.addActionListener(new SelectSidebar(SelectSidebar.MY_LISTING));

        var myGroupbuysButton = sidebar.getButtons().getMyGroupbuysButton();
        myGroupbuysButton.addActionListener(new SelectSidebar(SelectSidebar.MY_GROUPBUYS));

        var browseButton = sidebar.getButtons().getBrowseGroupbuysButton();
        browseButton.addActionListener(new SelectSidebar(SelectSidebar.BROWSE_GROUPBUYS));

        var logo = sidebar.getLogo().getLogoContainer();
        logo.addMouseListener(new ReturnHomeAction());
    }

    private void initCenterControls(){
        
    }

    class SelectSidebar implements ActionListener{
        public static final int HOME = 1;
        public static final int MY_LISTING = 2;
        public static final int MY_GROUPBUYS = 3;
        public static final int BROWSE_GROUPBUYS = 4;

        private int buttonType;

        public SelectSidebar(int buttonType){
            this.buttonType = buttonType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (buttonType) {
                case HOME:
                    center.getContent().getContentController().showHome();
                    sidebar.getButtons().setSelected(Buttons.HOME);
                    break;
            
                case MY_LISTING:
                    center.getContent().getContentController().showMyListing();
                    sidebar.getButtons().setSelected(Buttons.MY_LISTINGS);
                    break;
            
                case MY_GROUPBUYS:
                    center.getContent().getContentController().showMyGroupbuys();
                    sidebar.getButtons().setSelected(Buttons.MY_GROUPBUYS);
                    break;
            
                case BROWSE_GROUPBUYS:
                    center.getContent().getContentController().showBrowseGroupbuys();
                    sidebar.getButtons().setSelected(Buttons.BROWSE_GROUPBUYS);
                    break;
            
                default:
                    break;
            }
        }
        
    }

    class ReturnHomeAction extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            sidebar.getButtons().setSelected(Buttons.HOME);
        }
    }

}
