package groupbuyapp.Admin.AdminContent;


import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import groupbuyapp.Admin.AdminContent.ContentDisplayers.ContentDisplayer;
import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class AContent extends JPanel{
    private ContentDisplayer currentDisplayer;

    public ContentDisplayer getCurrentDisplayer() {
        return currentDisplayer;
    }

    public void setCurrentDisplayer(ContentDisplayer currentDisplayer) {
        this.currentDisplayer = currentDisplayer;
    }

    public AContent(){
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        this.currentDisplayer = new ContentDisplayer(ContentDisplayer.DEFAULT);
        setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 30));
        setLayout(new BorderLayout());
        add(currentDisplayer);
    }
}
