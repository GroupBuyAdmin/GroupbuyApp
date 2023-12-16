package groupbuyapp.Client.Center.Content.Home;


import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import groupbuyapp.Client.Center.Content.Content;
import groupbuyapp.Client.Center.Content.Browser.Browser;
import groupbuyapp.Client.Center.Content.Browser.CategoryPanel;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.SideBar.SideBar;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel;
import groupbuyapp.Misc.CustomComponents.ScrollablePanel.ScrollableSizeHint;
import groupbuyapp.Misc.Fonts.GbuyFont;
import groupbuyapp.Misc.Interface.Refreshable;
import net.miginfocom.swing.MigLayout;

public class Home extends JPanel implements Refreshable{
    MyListingsPanel myListingPanel;
    MyGroupbuysPanel MyGroupbuysPanel;
    MiniBrowserPanel miniBrowserPanel;

    ScrollablePanel scrollablePanel;
    JScrollPane scrollPane;

    Browser browser;
    User currentUser;
    Content content;
    SideBar sideBar;

    public Home(Browser browser, User currentUser, Content content, SideBar sideBar){
        
        this.browser = browser;
        this.currentUser = currentUser;
        this.content = content;
        this.sideBar = sideBar;
        
        myListingPanel = new MyListingsPanel(content, sideBar);
        MyGroupbuysPanel = new MyGroupbuysPanel(content, sideBar);
        miniBrowserPanel = new MiniBrowserPanel(currentUser, content, sideBar);
        
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
        scrollablePanel.add(MyGroupbuysPanel, "wrap, push, grow");
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

    class MyListingsPanel extends JPanel{
        Content content;
        SideBar sideBar;
        InnerMyListingPanel innerMyListingPanel;
        public MyListingsPanel(Content content, SideBar sideBar){
            
            this.content = content;
            this.sideBar = sideBar;
            setLayout(new BorderLayout());
            innerMyListingPanel = new InnerMyListingPanel(content, sideBar);
            add(innerMyListingPanel);
            setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 0));
            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        }

        class InnerMyListingPanel extends JPanel{
            Content content;
            SideBar sideBar;
            CategoryPanel myListingsCategoryPanel;

            public InnerMyListingPanel(Content content, SideBar sideBar){
                setOpaque(false);
                setBackground(GbuyColor.PANEL_COLOR);
                this.content = content;
                this.sideBar = sideBar;
                myListingsCategoryPanel = new CategoryPanel(CategoryPanel.HOME_MY_LISTING, "My Listings", browser, currentUser, content, sideBar);
                setLayout(new BorderLayout());
                add(myListingsCategoryPanel);


            }
        }
    }

    class MyGroupbuysPanel extends JPanel{
        InnerMyGroupbuysPanel innerMyGroupbuysPanel;
        Content content;
        SideBar sideBar;

        public MyGroupbuysPanel(Content content, SideBar sideBar){
            
            this.content = content;
            this.sideBar = sideBar;
            setLayout(new BorderLayout());
            innerMyGroupbuysPanel = new InnerMyGroupbuysPanel(content, sideBar);
            add(innerMyGroupbuysPanel);
            setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 0));
            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        }
        class InnerMyGroupbuysPanel extends JPanel{
            Content content;
            SideBar sideBar;
            CategoryPanel myGroupbuysCategoryPanel;

            public InnerMyGroupbuysPanel(Content content, SideBar sideBar){
                setOpaque(false);
                this.content = content;
                this.sideBar = sideBar;
                
                setBackground(GbuyColor.PANEL_COLOR);


                setLayout(new BorderLayout());

                myGroupbuysCategoryPanel = new CategoryPanel(CategoryPanel.HOME_MY_GROUPBUYS, "My Groupbuys", browser, currentUser, content, sideBar);

                add(myGroupbuysCategoryPanel);

            }
        }
    }

    class MiniBrowserPanel extends JPanel{
        InnerMiniBrowserPanel innerMiniBrowserPanel;
        User currentUser;
        Content content;
        SideBar sideBar;

        public MiniBrowserPanel(User currentUser, Content content, SideBar sideBar){
            
            this.currentUser = currentUser;
            this.content = content;
            this.sideBar = sideBar;
            setLayout(new BorderLayout(10, 10));
            innerMiniBrowserPanel = new InnerMiniBrowserPanel(currentUser, content, sideBar);

            add(innerMiniBrowserPanel);
            JLabel browselabel = new JLabel("BROWSE");
            browselabel.setFont(GbuyFont.MULI_BOLD.deriveFont(26f));
            add(browselabel, BorderLayout.NORTH);

            setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 0));
            setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
        }
        class InnerMiniBrowserPanel extends JPanel{
            User currentUser;
            Content content;
            SideBar sideBar;
            Browser homeBrowser;
            public InnerMiniBrowserPanel(User currentUser, Content content, SideBar sideBar){
                setOpaque(false);
                this.currentUser = currentUser;
                this.content = content;
                this.sideBar = sideBar;
                this.homeBrowser = new Browser(currentUser, content, sideBar);
                setBackground(GbuyColor.PANEL_COLOR);
                setLayout(new BorderLayout()); 
                add(homeBrowser.getScrollablePanel());
            }
        }
    }

    @Override
    public void refresh() {
        myListingPanel.innerMyListingPanel.myListingsCategoryPanel.refresh();
        MyGroupbuysPanel.innerMyGroupbuysPanel.myGroupbuysCategoryPanel.refresh();
        miniBrowserPanel.innerMiniBrowserPanel.homeBrowser.refresh();
    }


}
