package groupbuyapp.Admin.AdminContent;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import groupbuyapp.Admin.AdminContent.ContentDisplayers.ContentDisplayer;
import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class AContent extends JPanel{
    private ContentDisplayer contentDisplayer;
    public ContentDisplayer getContentDisplayer() {
        return contentDisplayer;
    }
    public AContent(){
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        this.contentDisplayer = new ContentDisplayer();
        setLayout(new BorderLayout());
        add(contentDisplayer);
    }
}
