package groupbuyapp.Client.ClientCenter.ClientContent.ClientDisplayers;


import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel;
import groupbuyapp.SystemFiles.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;
import net.miginfocom.swing.MigLayout;

public class ClientHomeDisplayer extends JPanel{
    private MyListingsPanel myListingPanel;
    private MyGroupbuysPanel myGroupbuysPanel;
    private MiniBrowserPanel miniBrowserPanel;

    private ScrollablePanel scrollablePanel;
    private JScrollPane scrollPane;

    public SideScrollDisplayer getMyListingScroller(){
        return myListingPanel.getInnerMyListingPanel().getMyListingScroller();
    }

    public SideScrollDisplayer getMyGroupbuysScroller(){
        return myGroupbuysPanel.getInnerMyGroupbuysPanel().getMyGroupbuysScroller();
    }

    public ClientBrowser getMiniBrowser(){
        return miniBrowserPanel.getInnerMiniBrowserPanel().getMiniBrowser();
    }

    public ClientHomeDisplayer(){
        
        myListingPanel = new MyListingsPanel();
        myGroupbuysPanel = new MyGroupbuysPanel();
        miniBrowserPanel = new MiniBrowserPanel();
        
        JLabel homeLabel = new JLabel("HOME");
        homeLabel.setFont(GbuyFont.MULI_BOLD.deriveFont(30f));
        JPanel headerJPanel = new JPanel(new BorderLayout());
        headerJPanel.setBorder(BorderFactory.createEmptyBorder(5, 40, 0, 5));
        headerJPanel.setOpaque(false);
        headerJPanel.add(homeLabel);

        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        scrollablePanel = new ScrollablePanel(new MigLayout());
        scrollablePanel.setScrollableHeight(ScrollableSizeHint.STRETCH);
        scrollablePanel.setScrollableWidth(ScrollableSizeHint.FIT);
        scrollablePanel.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);

        scrollablePanel.add(headerJPanel, "wrap, push, grow");
        scrollablePanel.add(myListingPanel, "wrap, push, grow");
        scrollablePanel.add(myGroupbuysPanel, "wrap, push, grow");
        scrollablePanel.add(miniBrowserPanel, "wrap, push, grow");

        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollablePanel.setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        
        setLayout(new BorderLayout());



        setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);
        
    }

    public class MyListingsPanel extends JPanel{
        private InnerMyListingPanel innerMyListingPanel;

        public InnerMyListingPanel getInnerMyListingPanel() {
            return innerMyListingPanel;
        }

        public MyListingsPanel(){
            setLayout(new BorderLayout());
            innerMyListingPanel = new InnerMyListingPanel();
            add(innerMyListingPanel);
            setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 0));
            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        }

        public class InnerMyListingPanel extends JPanel{
            private SideScrollDisplayer myListingScroller;

            public SideScrollDisplayer getMyListingScroller() {
                return myListingScroller;
            }

            public InnerMyListingPanel(){
                setOpaque(false);
                setBackground(GbuyColor.PANEL_COLOR);
                this.myListingScroller = new SideScrollDisplayer(SideScrollDisplayer.HOME_BROWSER, "My Listings");
                setLayout(new BorderLayout());
                add(myListingScroller);


            }
        }
    }

    public class MyGroupbuysPanel extends JPanel{
        private InnerMyGroupbuysPanel innerMyGroupbuysPanel;


        public InnerMyGroupbuysPanel getInnerMyGroupbuysPanel() {
            return innerMyGroupbuysPanel;
        }

        public MyGroupbuysPanel(){
            
            setLayout(new BorderLayout());
            innerMyGroupbuysPanel = new InnerMyGroupbuysPanel();
            add(innerMyGroupbuysPanel);
            setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 0));
            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        }
        public class InnerMyGroupbuysPanel extends JPanel{
            private SideScrollDisplayer myGroupbuysScroller;

            public SideScrollDisplayer getMyGroupbuysScroller() {
                return myGroupbuysScroller;
            }

            public InnerMyGroupbuysPanel(){
                setOpaque(false);

                
                setBackground(GbuyColor.PANEL_COLOR);

                this.myGroupbuysScroller = new SideScrollDisplayer(SideScrollDisplayer.HOME_MY_GROUPBUYS, "My Groupbuys");

                setLayout(new BorderLayout());

                add(myGroupbuysScroller);

            }
        }
    }

    public class MiniBrowserPanel extends JPanel{
        private InnerMiniBrowserPanel innerMiniBrowserPanel;


        public InnerMiniBrowserPanel getInnerMiniBrowserPanel() {
            return innerMiniBrowserPanel;
        }

        public MiniBrowserPanel(){
            
            setLayout(new BorderLayout(10, 10));
            innerMiniBrowserPanel = new InnerMiniBrowserPanel();

            add(innerMiniBrowserPanel);
            JLabel browselabel = new JLabel("BROWSE");
            browselabel.setFont(GbuyFont.MULI_BOLD.deriveFont(26f));
            add(browselabel, BorderLayout.NORTH);

            setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 0));
            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        }

        public class InnerMiniBrowserPanel extends JPanel{
            private ClientBrowser miniBrowser;

            public ClientBrowser getMiniBrowser() {
                return miniBrowser;
            }

            public InnerMiniBrowserPanel(){
                this.miniBrowser = new ClientBrowser();
                setLayout(new BorderLayout());
                add(miniBrowser);
            }
        }
    }
}
