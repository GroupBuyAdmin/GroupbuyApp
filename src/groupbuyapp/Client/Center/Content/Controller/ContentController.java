package groupbuyapp.Client.Center.Content.Controller;

import groupbuyapp.Client.Center.Content.Content;

public class ContentController {
    private Content content;
    private int currentPanel;
    
    public ContentController(Content content, int currentPanel){
        this.content = content;
        this.currentPanel = currentPanel;
    }

    public void showHome(){
        if(Current_Panel_Is(IN_HOME)){
            content.getHome().refresh();
        }
        content.getLayout().show(content.getContentContainer(), Content.HOME);
    }

    public void showMyListing(){
        if(Current_Panel_Is(IN_MY_LISTINGS)){
            content.getMyListings().refresh();
        }
        content.getLayout().show(content.getContentContainer(), Content.MY_LISTINGS);
    }

    public void showMyGroupbuys(){
        if(Current_Panel_Is(IN_MY_GROUPBUYS)){
            content.getMyGroupbuys().refresh();
        }
        content.getMyGroupbuys().refresh();
        content.getLayout().show(content.getContentContainer(), Content.MY_GROUPBUYS);
    }

    public void showBrowseGroupbuys(){
        if(Current_Panel_Is(IN_BROWSE_GROUPBUYS)){
            content.getBrowser().refresh();
        }
        content.getBrowser().refresh();
        content.getLayout().show(content.getContentContainer(), Content.BROWSE_GROUPBUYS);
    }

    public static final int IN_HOME = 1;
    private static final int IN_MY_LISTINGS = 2;
    private static final int IN_MY_GROUPBUYS = 3;
    private static final int IN_BROWSE_GROUPBUYS = 4;

    private boolean Current_Panel_Is(int desiredPanel){         //when a sidebar button is clicked twice, the current panel will refresh   
        if(currentPanel == desiredPanel){
            return true;
        } else {
            currentPanel = desiredPanel;
            return false;
        }
    }
}
