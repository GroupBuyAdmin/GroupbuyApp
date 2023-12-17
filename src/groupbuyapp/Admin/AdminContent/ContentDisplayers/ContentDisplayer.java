package groupbuyapp.Admin.AdminContent.ContentDisplayers;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.Misc.CustomComponents.ScrollablePanel;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel.ScrollableSizeHint;

public class ContentDisplayer extends JPanel{
    private HeaderPanel headerPanel;
    private ScrollPanel scrollPanel;

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public ScrollPanel getScrollPanel() {
        return scrollPanel;
    }

    public JLabel getHeaderName(){
        return headerPanel.getHeaderName();
    }

    public ScrollablePanel getScrollablePanel(){
        return scrollPanel.getScrollablePanel();
    }

    public ContentDisplayer(){
        this.headerPanel = new HeaderPanel();
        this.scrollPanel = new ScrollPanel();

        setLayout(new BorderLayout());

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPanel, BorderLayout.SOUTH);
    }


    class HeaderPanel extends JPanel{
        private JLabel headerName;

        public JLabel getHeaderName() {
            return headerName;
        }

        public HeaderPanel(){
            setOpaque(false);
            this.headerName = new JLabel("default");
            setLayout(new BorderLayout());
            add(headerName, BorderLayout.WEST);
        }
    }

    class ScrollPanel extends JPanel{
        private ScrollablePanel scrollablePanel;
        private List<ContentContainer> allcontentContainers;

        public ScrollablePanel getScrollablePanel() {
            return scrollablePanel;
        }

        public ScrollPanel(){
            this.scrollablePanel = new ScrollablePanel();
            scrollablePanel.setScrollableHeight(ScrollableSizeHint.STRETCH);
            scrollablePanel.setScrollableWidth(ScrollableSizeHint.FIT);

            setLayout(new BorderLayout());

            add(scrollablePanel);
        }

    }
}
