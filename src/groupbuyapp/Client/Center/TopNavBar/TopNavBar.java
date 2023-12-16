package groupbuyapp.Client.Center.TopNavBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundProfilePicturePanel;
import groupbuyapp.Misc.CustomComponents.RoundedCornerTextField;
import groupbuyapp.Misc.CustomComponents.RoundedPanel;
import groupbuyapp.Misc.Fonts.GbuyFont;

public class TopNavBar extends JPanel{
    private SearchBar searchBar;
    private RoundProfilePicturePanel profileContainer;
    
    public SearchBar getSearchBar() {
        return searchBar;
    }

    public TopNavBar(){
        setPreferredSize(new Dimension(getWidth(), 75));

        setLayout(new BorderLayout());
        
        setBackground(GbuyColor.MAIN_COLOR);
        setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 15));

        searchBar = new SearchBar();
        profileContainer = new RoundProfilePicturePanel(new ImageIcon("src/groupbuyapp/Client/LogIn/img/default profile.png"));
        profileContainer.setOpaque(false);

        add(profileContainer, BorderLayout.EAST);
        add(searchBar, BorderLayout.WEST);
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
}
