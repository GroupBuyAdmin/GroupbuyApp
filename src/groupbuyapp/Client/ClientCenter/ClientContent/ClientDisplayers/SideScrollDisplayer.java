package groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.ClientCenter.ClientContent.ClientContainers.ProductPanel;
import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;
import groupbuyapp.SystemFiles.Interface.Refreshable;

public class SideScrollDisplayer extends JPanel implements Refreshable{
    private Header header;
    private SideScrollPanel sideScrollPanel;
    
    private int fromWhere;

    private List<ProductPanel> allProductsPanels;


    public List<ProductPanel> getAllProductsPanels() {
        return allProductsPanels;
    }

    public void setAllProductsPanels(List<ProductPanel> allProductsPanels) {
        this.allProductsPanels = allProductsPanels;
        refresh();
    }

    public JLabel getHeaderName(){
        return header.getHeaderName();
    }

    public JLabel getSeeAll(){
        return header.getSeeAll();
    }

    public JPanel getScrollablePanel(){
        return sideScrollPanel.getScrollablePanel();
    }

    public static final int HOME_MY_LISTING = 1;
    public static final int HOME_MY_GROUPBUYS = 2;
    public static final int HOME_BROWSER = 3;
    public static final int JUST_CATEGORY = 4;

    public SideScrollDisplayer(int fromWhere, String headerName){
        this.allProductsPanels = new ArrayList<>();
        this.fromWhere = fromWhere;

        this.header = new Header(headerName);
        this.sideScrollPanel = new SideScrollPanel();

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(sideScrollPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        setBackground(GbuyColor.PANEL_COLOR);
        addMouseListener(new InteractiveHighlighter(this));
    }

    public void refresh(){
        sideScrollPanel.getScrollablePanel().removeAll();
        
        for(ProductPanel p : allProductsPanels){
            sideScrollPanel.getScrollablePanel().add(p);
        }
        
        sideScrollPanel.getScrollablePanel().revalidate();
        sideScrollPanel.getScrollablePanel().repaint();
    }
    
    public class Header extends JPanel{
        private JLabel headerName;
        private JLabel seeAll;

        public JLabel getHeaderName() {
            return headerName;
        }

        public JLabel getSeeAll() {
            return seeAll;
        }

        public Header(String name){
 
            setOpaque(false);
            setLayout(new BorderLayout());

            headerName = new JLabel(name);
            if(fromWhere == JUST_CATEGORY){
                headerName.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(16f));
            } else {
                headerName.setFont(GbuyFont.MULI_BOLD.deriveFont(24f));
            }
            headerName.setForeground(GbuyColor.MAIN_COLOR);

            seeAll = new JLabel("See All");
            seeAll.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));

            // if(fromWhere == JUST_CATEGORY){
            //     seeAll.addMouseListener(new SeeAllListener(seeAll, category, content, sideBar, newBrowser));
            // } else {
            //     seeAll.addMouseListener(new HomeSeeAllListener(headerName, content, sideBar, fromWhere));
            // }

            headerName.setHorizontalAlignment(JLabel.LEADING);
            seeAll.setHorizontalAlignment(JLabel.TRAILING);

            add(headerName, BorderLayout.WEST);
            add(seeAll, BorderLayout.EAST);
        }
    }

    public class SideScrollPanel extends JPanel{
        private JPanel scrollablePanel;
        private JScrollPane scrollpane;

        public JPanel getScrollablePanel() {
            return scrollablePanel;
        }

        public JScrollPane getScrollpane() {
            return scrollpane;
        }

        public SideScrollPanel(){
            setOpaque(false);

            this.scrollablePanel = new JPanel();
            scrollablePanel.setBackground(GbuyColor.PANEL_COLOR);
            scrollablePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            this.scrollpane = new JScrollPane(scrollablePanel);
            scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollpane.getHorizontalScrollBar().setUnitIncrement(8);
            scrollpane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
            scrollpane.setBorder(BorderFactory.createEmptyBorder());
            setLayout(new BorderLayout());
            add(scrollpane);
        }
    }
    
    private class InteractiveHighlighter extends MouseAdapter{
        SideScrollDisplayer s;

        InteractiveHighlighter(SideScrollDisplayer s){
            this.s = s;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            s.getSeeAll().setForeground(GbuyColor.MAIN_COLOR);
            s.revalidate();
            s.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            s.getSeeAll().setForeground(GbuyColor.MAIN_TEXT_COLOR);
            s.revalidate();
            s.repaint();
        }
   }
}
