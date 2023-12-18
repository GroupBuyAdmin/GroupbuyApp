package groupbuyapp.Client.ClientCenter.ClientNavBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.SystemFiles.ColorPalette.GbuyColor;
import groupbuyapp.SystemFiles.CustomComponents.RoundProfilePicturePanel;
import groupbuyapp.SystemFiles.CustomComponents.RoundedCornerTextField;
import groupbuyapp.SystemFiles.CustomComponents.RoundedPanel;
import groupbuyapp.SystemFiles.Fonts.GbuyFont;

public class TopNavBar extends JPanel{
    private SearchBar searchBar;
    private Profile profile;
    
    
    public Profile getProfile() {
        return profile;
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }

    public JPanel getProfileIcon(){
        return profile.getProfileIcon();
    }

    public JLabel getSignOutIcon(){
        return profile.getSignOutButton();
    }

    

    public TopNavBar(){
        setPreferredSize(new Dimension(getWidth(), 75));

        setLayout(new BorderLayout());
        
        setBackground(GbuyColor.MAIN_COLOR);
        setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 10));

        searchBar = new SearchBar();
        profile = new Profile();

        add(searchBar, BorderLayout.WEST);
        add(profile, BorderLayout.EAST);
    }

    public class SearchBar extends RoundedPanel{
        RoundedCornerTextField searchField;
        JButton searchButton;

        public RoundedCornerTextField getSearchField() {
            return searchField;
        }

        public JButton getSearchButton() {
            return searchButton;
        }

        public String getSearchedItem(){
            String searchItem = searchField.getText();
            if(searchItem.equals("Search for products")){
                return null;
            } else return searchItem;
        }

        public SearchBar(){
            this.searchField = new RoundedCornerTextField(30);
            searchField.setText("Search for products");
            searchField.setOpaque(false);
            searchField.setFont(GbuyFont.MULI_LIGHT.deriveFont(14f));
            searchField.setForeground(Color.lightGray);
            searchField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    searchField.setText("");
                    searchField.setFont(GbuyFont.MULI_BOLD.deriveFont(14f));
                    searchField.setForeground(GbuyColor.MAIN_COLOR);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(searchField.getText().isEmpty()){
                        searchField.setText("Search for products");
                        searchField.setFont(GbuyFont.MULI_LIGHT.deriveFont(14f));
                        searchField.setForeground(Color.lightGray);
                    }
                }
                
            });

            this.searchButton = new JButton(new ImageIcon("src/groupbuyapp/Client/Center/TopNavBar/img/search.png"));
            searchButton.setContentAreaFilled(false);
            searchButton.setBorderPainted(false);
            searchButton.setFocusable(false);

            setLayout(new BorderLayout());

            add(searchField, BorderLayout.CENTER);
            add(searchButton, BorderLayout.EAST);

            setBackground(GbuyColor.PANEL_COLOR);
            setShady(false);

            setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
            setArcs(new Dimension(50, 50));
        }

    }

    public class Profile extends JPanel{
        private RoundProfilePicturePanel profileIcon;
        private JLabel signOutButton;

        public RoundProfilePicturePanel getProfileIcon() {
            return profileIcon;
        }

        public JLabel getSignOutButton() {
            return signOutButton;
        }

        private static String defaultProfile = "src/groupbuyapp/Client/Center/TopNavBar/img/default profile2.png";
        private static String signOutIcon = "src/groupbuyapp/Client/Center/TopNavBar/img/sign out.png";

        public Profile(){
            setOpaque(false);

            this.profileIcon = new RoundProfilePicturePanel(new ImageIcon(defaultProfile));
            this.signOutButton = new JLabel();
            signOutButton.setIcon(new ImageIcon(signOutIcon));


            setLayout(new FlowLayout(FlowLayout.TRAILING, 25, 0));

            add(profileIcon);
            add(signOutButton);

        }

    }
}
